package dev.xkmc.curseofpandora.content.hostility;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
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

public class SpellSingularity extends ITokenProviderItem<SpellSingularity.Data> {

	public SpellSingularity(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		var player = Proxy.getPlayer();
		boolean pass = level == null || player == null || check(player);
		var spell = Component.translatable(CoPAttrs.SPELL.get().getDescriptionId()).withStyle(ChatFormatting.BLUE);
		var magic = Component.translatable(L2DamageTracker.MAGIC_FACTOR.get().getDescriptionId()).withStyle(ChatFormatting.BLUE);
		var reality = Component.translatable(CoPAttrs.REALITY.get().getDescriptionId()).withStyle(ChatFormatting.BLUE);
		var numSpell = CoPConfig.COMMON.compat.spellSingularitySpellBonusPerReality.get();
		var numMagic = CoPConfig.COMMON.compat.spellSingularityMagicDamageBonusPerReality.get();
		var valSpell = Component.literal(Math.round(numSpell * 100) + "%").withStyle(ChatFormatting.AQUA);
		var valMagic = Component.literal(Math.round(numMagic * 100) + "%").withStyle(ChatFormatting.AQUA);
		list.add(CoPLangData.Compat.SPELL_SINGULARITY_0.get(reality).withStyle(ChatFormatting.GRAY));
		list.add(CoPLangData.Compat.SPELL_SINGULARITY_1.get(valSpell, spell, valMagic, magic, reality)
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.DARK_GRAY));
	}

	private static boolean check(Player player) {
		boolean pass = true;
		int reality = (int) player.getAttributeValue(CoPAttrs.REALITY);
		for (var e : EquipmentSlot.values()) {
			ItemStack stack = player.getItemBySlot(e);
			int count = stack.getAllEnchantments().size();
			pass &= count <= reality;
		}
		return pass;
	}

	@SerialClass
	public static class Data extends BaseTickingToken {

		private AttrAdder getSpell(int val) {
			double rate = CoPConfig.COMMON.compat.spellSingularitySpellBonusPerReality.get();
			return AttrAdder.of("spell_singularity", CoPAttrs.SPELL,
					AttributeModifier.Operation.ADD_VALUE, () -> val * rate);
		}

		private AttrAdder getMagic(int val) {
			double rate = CoPConfig.COMMON.compat.spellSingularityMagicDamageBonusPerReality.get();
			return AttrAdder.of("spell_singularity", L2DamageTracker.MAGIC_FACTOR,
					AttributeModifier.Operation.ADD_VALUE, () -> val * rate);
		}

		@Override
		protected void removeImpl(Player player) {
			getSpell(0).removeImpl(player);
			getMagic(0).removeImpl(player);
		}

		@Override
		protected void tickImpl(Player player) {
			boolean pass = check(player);
			if (pass) {
				int reality = (int) player.getAttributeValue(CoPAttrs.REALITY);
				getSpell(reality).tickImpl(player);
				getMagic(reality).tickImpl(player);
			} else {
				getSpell(0).removeImpl(player);
				getMagic(0).removeImpl(player);
			}
		}

	}

}
