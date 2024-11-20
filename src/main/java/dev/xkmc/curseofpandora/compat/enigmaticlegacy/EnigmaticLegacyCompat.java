package dev.xkmc.curseofpandora.compat.enigmaticlegacy;

import dev.xkmc.l2library.capability.conditionals.ConditionalData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class EnigmaticLegacyCompat {

	public static boolean suppressAggro(ServerPlayer player) {
		return ConditionalData.HOLDER.get(player).getData(ELItems.AGGREVATE.get().getKey()) != null;
	}

	public static boolean suppressInsomnia(Player player) {
		return ConditionalData.HOLDER.get(player).getData(ELItems.INSOMNIA.get().getKey()) != null;
	}

}
