package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import net.minecraft.world.entity.player.Player;

public interface IAttackListenerToken {

	default void onPlayerDamagedFinal(Player player, DamageData.DefenceMax data) {
	}

	default boolean onPlayerAttackTarget(Player player, DamageData.Attack data) {
		return false;
	}

	default void onPlayerHurtTarget(Player player, DamageData.Offence data) {
	}

	default void onPlayerDamageTarget(Player player, DamageData.Defence data) {
	}

	default boolean onPlayerAttacked(Player player, DamageData.Attack data) {
		return false;
	}

	default void onPlayerHurt(Player player, DamageData.Offence data) {
	}

	default void onPlayerDamaged(Player player, DamageData.Defence data) {
	}

	default void onPlayerDamageTargetFinal(Player player, DamageData.DefenceMax data) {
	}

	default void onCreateSource(Player player, CreateSourceEvent event) {
	}

}
