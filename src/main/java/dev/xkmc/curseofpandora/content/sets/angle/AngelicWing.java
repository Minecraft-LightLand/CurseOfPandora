package dev.xkmc.curseofpandora.content.sets.angle;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AngelicWing extends ITokenProviderItem<AngelicWing.Data> {

	private static int getIndexReq() {
		return CoPConfig.COMMON.angelicWingRealityIndex.get();
	}

	public AngelicWing(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(CoPLangData.IDS.ANGELIC_CHECK.get().withStyle(ChatFormatting.GRAY));
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(CoPLangData.IDS.ANGELIC_WING_IMMUNE.get())
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(CoPLangData.IDS.ANGELIC_WING.get())
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		@Override
		protected void removeImpl(Player player) {

		}

		private boolean check(Player player) {
			return AngelicPunishment.check(player, getIndexReq());
		}

		@Override
		public void onPlayerAttacked(Player player, AttackCache cache) {
			if (!check(player)) return;
			var event = cache.getLivingAttackEvent();
			assert event != null;
			if (event.getSource().is(DamageTypes.FLY_INTO_WALL)) {
				event.setCanceled(true);
			}
			if (event.getSource().is(DamageTypes.FALL)) {
				event.setCanceled(true);
			}
		}

		@Override
		protected void tickImpl(Player player) {
			if (!check(player)) return;
			double boost = CoPConfig.COMMON.angelicWingBoost.get();
			double max = CoPConfig.COMMON.angelicWingMaxSpeed.get();
			int req = getIndexReq();
			if (player.getAttributeValue(CoPMisc.REALITY.get()) < req) return;
			if (player.isFallFlying()) {
				var vec = player.getDeltaMovement();
				double len = vec.length();
				if (len < max && len > 0.2) {
					player.setDeltaMovement(vec.scale(1 + boost / len));
				}
			}
		}
	}

}
