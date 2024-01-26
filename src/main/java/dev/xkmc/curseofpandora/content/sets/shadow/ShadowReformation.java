package dev.xkmc.curseofpandora.content.sets.shadow;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPEffects;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

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
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
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
		if (player.getAttributeValue(CoPMisc.REALITY.get()) >= getIndexReq())
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
		public void onPlayerHurtTarget(Player player, AttackCache cache) {
			if (!cache.getAttackTarget().hasEffect(CoPEffects.SHADOW.get()))
				return;
			var event = cache.getLivingHurtEvent();
			assert event != null;
			if (event.getSource().is(L2DamageTypes.MAGIC)) {
				cache.addHurtModifier(DamageModifier.multTotal((float) (1 + getBonus())));
			}
		}

		@Override
		public void onPlayerDamaged(Player player, AttackCache cache) {
			if (cache.getAttacker() == null || !cache.getAttacker().hasEffect(CoPEffects.SHADOW.get()))
				return;
			var event = cache.getLivingDamageEvent();
			assert event != null;
			if (!event.getSource().is(L2DamageTypes.MAGIC) &&
					!event.getSource().is(DamageTypeTags.BYPASSES_EFFECTS) &&
					!event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
				cache.addDealtModifier(DamageModifier.multTotal((float) (1 - getReduction())));
			}
		}


	}

}
