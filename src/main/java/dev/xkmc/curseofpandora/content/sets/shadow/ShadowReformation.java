package dev.xkmc.curseofpandora.content.sets.shadow;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.curseofpandora.init.registrate.CoPEffects;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.Tags;

import java.util.List;

public class ShadowReformation extends ITokenProviderItem<ShadowReformation.Data> {

	public static int getIndexReq() {
		return CoPConfig.COMMON.shadow.shadowReformationRealityIndex.get();
	}

	public static double getBonus() {
		return CoPConfig.COMMON.shadow.shadowReformationBonus.get();
	}

	public static double getReduction() {
		return CoPConfig.COMMON.shadow.shadowReformationReduction.get();
	}

	public ShadowReformation(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(ctx.level()) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq()).withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Shadow.REFORMATION_1.get(
				Math.round(getBonus() * 100)
		)).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Shadow.REFORMATION_2.get(
				Math.round(getReduction() * 100)
		)).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		@Override
		protected void removeImpl(Player player) {

		}

		@Override
		protected void tickImpl(Player player) {

		}

		@Override
		public void onPlayerHurtTarget(Player player, DamageData.Offence data) {
			if (!data.getTarget().hasEffect(CoPEffects.SHADOW))
				return;
			if (data.getSource().is(Tags.DamageTypes.IS_MAGIC)) {
				data.addHurtModifier(DamageModifier.multTotal((float) (1 + getBonus())));
			}
		}

		@Override
		public void onPlayerDamaged(Player player, DamageData.Defence data) {
			if (data.getAttacker() == null || !data.getAttacker().hasEffect(CoPEffects.SHADOW))
				return;
			if (!data.getSource().is(Tags.DamageTypes.IS_MAGIC) &&
					!data.getSource().is(DamageTypeTags.BYPASSES_EFFECTS) &&
					!data.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
				data.addDealtModifier(DamageModifier.multTotal((float) (1 - getReduction())));
			}
		}


	}

}
