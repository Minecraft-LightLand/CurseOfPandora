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
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShadowConvergence extends ITokenProviderItem<ShadowConvergence.Data> {

	public static int getIndexReq() {
		return CoPConfig.COMMON.shadow.shadowConvergenceRealityIndex.get();
	}

	public static double getFactor() {
		return CoPConfig.COMMON.shadow.shadowConvergenceHealFactor.get();
	}

	public ShadowConvergence(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Shadow.CONVERGENCE.get(Math.round(getFactor() * 100))
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPMisc.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		private double heal;

		@Override
		protected void removeImpl(Player player) {

		}

		@Override
		protected void tickImpl(Player player) {
			if (heal > 0 && Double.isFinite(heal) && player.isAlive()) {
				player.heal((float) heal);
			}
			heal = 0;
		}

		@Override
		public void onPlayerDamageTargetFinal(Player player, AttackCache cache) {
			var target = cache.getAttackTarget();
			if (target.hasEffect(CoPEffects.SHADOW.get())) {
				heal += cache.getDamageDealt() * getFactor();
			}
		}

	}

}
