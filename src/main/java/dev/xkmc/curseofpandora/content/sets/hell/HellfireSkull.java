package dev.xkmc.curseofpandora.content.sets.hell;

import dev.xkmc.curseofpandora.content.complex.BasePandoraToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.event.ItemEffectHandlers;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2core.base.effects.EffectBuilder;
import dev.xkmc.l2core.base.effects.EffectUtil;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class HellfireSkull extends ITokenProviderItem<HellfireSkull.Data> {

	public static int getIndexReq() {
		return CoPConfig.SERVER.hell.hellfireSkullRealityIndex.get();
	}

	public static int getMinDuration() {
		return CoPConfig.SERVER.hell.hellfireSkullMinimumDuration.get();
	}

	public HellfireSkull(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(ctx.level()) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Hell.SKULL.get(Math.round(getMinDuration() / 20d))
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BasePandoraToken implements IAttackListenerToken {

		@Override
		protected void removeImpl(Player player) {

		}

		@Override
		protected void tickImpl(Player player) {

		}

		@Override
		public void onPlayerHurtTarget(Player player, DamageData.Offence data) {
			var target = data.getTarget();
			var ins = target.getEffect(LCEffects.FLAME);
			if (ins != null && ins.getDuration() >= getMinDuration()) {
				int reality = (int) Math.round(player.getAttributeValue(CoPAttrs.REALITY));
				int amp = Math.min(ins.getAmplifier() + 1, reality - 1);
				if (amp > ins.getAmplifier()) {
					ItemEffectHandlers.HELLFIRE_SKULL.trigger(target);
					EffectUtil.addEffect(target, new EffectBuilder(new MobEffectInstance(ins)).setAmplifier(amp).ins, player);
				}
			}
		}

	}

}
