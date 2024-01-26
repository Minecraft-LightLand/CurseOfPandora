package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import net.minecraft.world.entity.player.Player;

public interface IAttackListenerToken {
	default void onPlayerDamagedFinal(Player player, AttackCache cache) {

	}

	default void onPlayerAttackTarget(Player player, AttackCache cache) {

	}

	default void onPlayerHurtTarget(Player player, AttackCache cache) {

	}

	default void onPlayerDamageTarget(Player player, AttackCache cache) {

	}

	default void onPlayerAttacked(Player player, AttackCache cache) {

	}

	default void onPlayerHurt(Player player, AttackCache cache) {

	}

	default void onPlayerDamaged(Player player, AttackCache cache) {

	}

	default void onPlayerDamageTargetFinal(Player player, AttackCache cache){

	}

}
