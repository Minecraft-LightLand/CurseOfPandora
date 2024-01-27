package dev.xkmc.curseofpandora.content.sets.elemental;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurseRedirection extends ITokenProviderItem<CurseRedirection.Data> {

	private static double getStat() {
		return CoPConfig.COMMON.elemental.curseRedirectionBonus.get();
	}

	private static int getIndexReq() {
		return CoPConfig.COMMON.elemental.curseRedirectionRealityIndex.get();
	}

	private static AttrAdder magic(Player player) {
		int count = 0;
		for (var e : EquipmentSlot.values()) {
			if (e.isArmor()) {
				ItemStack stack = player.getItemBySlot(e);
				for (var ent : stack.getAllEnchantments().keySet()) {
					if (ent.isCurse()) count++;
				}
			}
		}
		double bonus = count * getStat();
		return AttrAdder.of("curse_redirection", L2DamageTracker.MAGIC_FACTOR::get,
				AttributeModifier.Operation.ADDITION, () -> bonus);
	}

	private static AttrAdder spell(Player player) {
		int count = 0;
		for (var e : EquipmentSlot.values()) {
			if (e.isArmor()) {
				ItemStack stack = player.getItemBySlot(e);
				for (var ent : stack.getAllEnchantments().keySet()) {
					if (ent.isCurse()) {
						count++;
						break;
					}
				}
			}
		}
		double bonus = count;
		return AttrAdder.of("curse_redirection", CoPMisc.SPELL,
				AttributeModifier.Operation.ADDITION, () -> bonus);
	}

	public CurseRedirection(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Elemental.CURSE_1.get())
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Elemental.CURSE_2.get(
				Math.round(getStat() * 100)
		)).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPMisc.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken {

		@Override
		protected void removeImpl(Player player) {
			magic(player).removeImpl(player);
			spell(player).removeImpl(player);
		}

		@Override
		protected void tickImpl(Player player) {
			if (player.level().isClientSide()) return;
			magic(player).tickImpl(player);
			spell(player).tickImpl(player);
		}

	}

}
