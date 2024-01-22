package dev.xkmc.curseofpandora.content.sets.hell;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.event.ItemEffectHandlers;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2library.base.effects.EffectBuilder;
import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HellfireSkull extends ITokenProviderItem<HellfireSkull.Data> {

	public static int getIndexReq() {
		return CoPConfig.COMMON.hellfireSkullRealityIndex.get();
	}

	public static int getMinDuration() {
		return CoPConfig.COMMON.hellfireSkullMinimumDuration.get();
	}

	public HellfireSkull(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
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
			var target = cache.getAttackTarget();
			var ins = target.getEffect(LCEffects.FLAME.get());
			if (ins != null && ins.getDuration() >= getMinDuration()) {
				int reality = (int) Math.round(player.getAttributeValue(CoPMisc.REALITY.get()));
				int amp = Math.min(ins.getAmplifier() + 1, reality);
				if (amp > ins.getAmplifier()) {
					ItemEffectHandlers.HELLFIRE_SKULL.trigger(target);
					EffectUtil.addEffect(target, new EffectBuilder(ins).setAmplifier(amp).ins,
							EffectUtil.AddReason.FORCE, player);
				}
			}
		}

	}

}
