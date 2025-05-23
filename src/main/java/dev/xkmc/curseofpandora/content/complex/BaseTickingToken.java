package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.l2core.capability.conditionals.ConditionalToken;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.world.entity.player.Player;

@SerialClass
public abstract class BaseTickingToken extends ConditionalToken {

	@SerialField
	public int life;

	public boolean tick(Player player) {
		if (life > 0)
			life--;
		if (life <= 0) {
			removeImpl(player);
		} else {
			tickImpl(player);
		}
		return life <= 0;
	}

	public void update() {
		life = 2;
	}

	protected abstract void removeImpl(Player player);

	protected abstract void tickImpl(Player player);


}