package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.*;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPDamageTypeGen;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurseOfSpellItem extends ISlotAdderItem<CurseOfSpellItem.Ticker> {

	private static final SlotAdder ADDER = SlotAdder.of("curse_of_spell", "hands", 1);
	public static final TokenKey<Ticker> KEY = new TokenKey<>(CurseOfPandora.MODID, "curse_of_spell");
	private static final AttrAdder R = CursePandoraUtil.reality(KEY), S = CursePandoraUtil.spell(KEY);

	public static double getItemSpellPenalty(double base, ItemStack stack) {
		double level = 0;
		for (var i : stack.getAllEnchantments().values()) {
			if (i > 0) {
				level += Math.pow(2, i - 1);
			}
		}
		double val = (base + stack.getEnchantmentValue());
		return level / val / base * CoPConfig.COMMON.curse.curseOfSpellLoadFactor.get();
	}

	public static double getSpellPenalty(Player player) {
		double bonus = player.getAttributeValue(CoPAttrs.SPELL.get());
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
		super(properties, Ticker::new, ADDER, R, S);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(CoPLangData.Reality.SPELL_1.get().withStyle(ChatFormatting.RED));
		if (level != null && level.isClientSide()) {
			ClientSpellText.addTotal(list);
		}
	}

	@SerialClass
	public static class Ticker extends ListTickingToken implements IAttackListenerToken {

		public Ticker() {
			super(List.of(ADDER, R, S));
		}

		@Override
		protected void tickImpl(Player player) {
			super.tickImpl(player);
			if (getSpellPenalty(player) > 0 && player.tickCount % 20 == 0) {
				player.hurt(new DamageSource(CoPDamageTypeGen.forKey(player.level(), CoPDamageTypeGen.SPELL_CURSE), player), 1);
			}
		}

		@Override
		public void onPlayerDamaged(Player player, AttackCache cache) {
			double penalty = getSpellPenalty(player);
			if (penalty > 0) {
				double factor = CoPConfig.COMMON.curse.curseOfSpellDamageFactor.get();
				cache.addDealtModifier(DamageModifier.multTotal((float) (1 + penalty * factor)));
			}
		}

		@Override
		public void onPlayerDamageTarget(Player player, AttackCache cache) {
			double penalty = getSpellPenalty(player);
			if (penalty > 0) {
				double factor = CoPConfig.COMMON.curse.curseOfSpellWeakenFactor.get();
				cache.addDealtModifier(DamageModifier.multTotal(1 / (float) (1 + penalty * factor)));
			}
		}

	}

}
