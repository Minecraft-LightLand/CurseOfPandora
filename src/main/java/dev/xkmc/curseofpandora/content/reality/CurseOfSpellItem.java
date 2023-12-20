package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ISlotAdderItem;
import dev.xkmc.curseofpandora.content.complex.ListTickingToken;
import dev.xkmc.curseofpandora.content.complex.SlotAdder;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.l2complements.init.CurseOfPandora;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurseOfSpellItem extends ISlotAdderItem<CurseOfSpellItem.Ticker> {

	private static final SlotAdder ADDER = SlotAdder.of("curse_of_spell", "hostility_curse", 1);
	public static final TokenKey<Ticker> KEY = new TokenKey<>(dev.xkmc.l2complements.init.CurseOfPandora.MODID, "curse_of_spell");

	public static double getItemSpellPenalty(double base, ItemStack stack) {
		double level = 0;
		for (var i : stack.getAllEnchantments().values()) {
			level += Math.pow(2, i);
		}
		double val = base + stack.getEnchantmentValue();
		return level / val / base;
	}

	public static double getSpellPenalty(Player player) {
		double bonus = player.getAttributeValue(CurseOfPandora.SPELL.get());
		bonus = Math.max(1, bonus);
		double penalty = 0;
		for (var e : EquipmentSlot.values()) {
			ItemStack stack = player.getItemBySlot(e);
			double val = getItemSpellPenalty(bonus, stack);
			penalty += Math.max(0, val - 1);
		}
		return penalty;
	}

	public CurseOfSpellItem(Properties properties) {
		super(properties, KEY, Ticker::new, ADDER, CursePandoraUtil.reality(KEY), CursePandoraUtil.spell(KEY));
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(CoPLangData.IDS.CURSE_OF_SPELL_1.get().withStyle(ChatFormatting.RED));
		if (level != null && level.isClientSide()) {
			ClientSpellText.addTotal(list);
		}
	}

	@SerialClass
	public static class Ticker extends ListTickingToken implements IAttackListenerToken {

		public Ticker() {
			super(List.of(ADDER, CursePandoraUtil.reality(KEY), CursePandoraUtil.spell(KEY)));
		}

		@Override
		public void onPlayerDamaged(Player player, AttackCache cache) {
			double penalty = getSpellPenalty(player);
			if (penalty > 0) {
				cache.addDealtModifier(DamageModifier.multTotal((float) (1 + penalty)));
			}
		}

		@Override
		public void onPlayerDamageTarget(Player player, AttackCache cache) {
			double penalty = getSpellPenalty(player);
			if (penalty > 0) {
				cache.addDealtModifier(DamageModifier.multTotal(1 / (float) (1 + penalty)));
			}
		}

	}

}
