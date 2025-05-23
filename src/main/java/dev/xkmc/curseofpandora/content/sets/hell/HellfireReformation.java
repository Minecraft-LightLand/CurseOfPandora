package dev.xkmc.curseofpandora.content.sets.hell;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2complements.content.item.curios.EffectValidItem;
import dev.xkmc.l2complements.init.data.LCDamageTypes;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2core.base.effects.EffectUtil;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class HellfireReformation extends ITokenProviderItem<HellfireReformation.Data> implements EffectValidItem {

	public static int getIndexReq() {
		return CoPConfig.SERVER.hell.hellfireReformationRealityIndex.get();
	}

	public HellfireReformation(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(ctx.level()) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Hell.REFORMATION_1.get()
				.withStyle(pass ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_GRAY));
		list.add(CoPLangData.Hell.REFORMATION_2.get()
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public boolean isEffectValid(MobEffectInstance ins, ItemStack itemStack, LivingEntity livingEntity) {
		return ins.getEffect() == LCEffects.FLAME.get();
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
		public boolean onPlayerAttacked(Player player, DamageData.Attack data) {
			return data.getSource().is(LCDamageTypes.SOUL_FLAME);
		}

		@Override
		public void onPlayerHurtTarget(Player player, DamageData.Offence data) {
			var target = data.getTarget();
			var ins = player.getEffect(LCEffects.FLAME);
			if (ins != null) {
				EffectUtil.addEffect(target, new MobEffectInstance(ins), player);
			}
		}

	}

}
