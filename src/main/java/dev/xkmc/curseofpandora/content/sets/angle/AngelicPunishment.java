package dev.xkmc.curseofpandora.content.sets.angle;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.world.entity.player.Player;

public class AngelicPunishment extends ITokenProviderItem<AngelicPunishment.Data> {

	public AngelicPunishment(Properties properties, TokenKey<Data> key) {
		super(properties, Data::new);
	}

	@SerialClass
	public static class Data extends BaseTickingToken {

		@Override
		protected void removeImpl(Player player) {

		}

		@Override
		protected void tickImpl(Player player) {

		}
	}

}
