package dev.xkmc.curseofpandora.content.sets.angle;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AngelicWing extends ITokenProviderItem<AngelicWing.Data> {

	public AngelicWing(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(CoPLangData.IDS.ANGELIC_WING.get());
	}

	@SerialClass
	public static class Data extends BaseTickingToken {

		@Override
		protected void removeImpl(Player player) {

		}

		@Override
		protected void tickImpl(Player player) {
			double factor = 0.01;//TODO
			int max = 10;//TODO
			int req = 3;//TODO
			if (player.getAttributeValue(CoPMisc.REALITY.get()) < req) return;
			if (player.isFallFlying() && player.position().y >= player.level().getMaxBuildHeight()) {
				var vec = player.getDeltaMovement();
				if (vec.length() < max) {
					player.setDeltaMovement(vec.scale(1 + factor));
				}
			}
		}
	}

}
