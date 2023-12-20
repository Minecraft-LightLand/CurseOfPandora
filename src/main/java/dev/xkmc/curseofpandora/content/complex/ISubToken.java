package dev.xkmc.curseofpandora.content.complex;

import net.minecraft.world.entity.player.Player;

public interface ISubToken {

	void removeImpl(Player player);

	void tickImpl(Player player);

}
