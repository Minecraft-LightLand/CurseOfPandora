package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@SerialClass
public abstract class ListTickingToken extends BaseTickingToken {

	private final List<ISubToken> list;

	public ListTickingToken(List<ISubToken> list) {
		this.list = list;
	}

	@Override
	protected void removeImpl(Player player) {
		list.forEach(e -> e.removeImpl(player));
	}

	@Override
	protected void tickImpl(Player player) {
		list.forEach(e -> e.tickImpl(player));
	}

}
