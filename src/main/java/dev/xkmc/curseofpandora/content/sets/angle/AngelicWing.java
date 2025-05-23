package dev.xkmc.curseofpandora.content.sets.angle;

import dev.xkmc.curseofpandora.content.complex.BasePandoraToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class AngelicWing extends ITokenProviderItem<AngelicWing.Data> {

	private static int getIndexReq() {
		return CoPConfig.SERVER.angelic.angelicWingRealityIndex.get();
	}

	public AngelicWing(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		list.add(CoPLangData.Angelic.CHECK.get().withStyle(ChatFormatting.GRAY));
		boolean pass = ClientSpellText.getReality(ctx.level()) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Angelic.WING_IMMUNE.get())
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Angelic.WING.get())
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

		private boolean check(Player player) {
			return AngelicPunishment.check(player, getIndexReq());
		}

		@Override
		public boolean onPlayerAttacked(Player player, DamageData.Attack data) {
			if (!check(player)) return false;
			if (data.getSource().is(DamageTypes.FLY_INTO_WALL)) {
				return true;
			}
			return data.getSource().is(DamageTypes.FALL);
		}

		@Override
		protected void tickImpl(Player player) {
			if (!check(player)) return;
			double boost = CoPConfig.SERVER.angelic.angelicWingBoost.get();
			double max = CoPConfig.SERVER.angelic.angelicWingMaxSpeed.get();
			int req = getIndexReq();
			if (player.getAttributeValue(CoPAttrs.REALITY) < req) return;
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
