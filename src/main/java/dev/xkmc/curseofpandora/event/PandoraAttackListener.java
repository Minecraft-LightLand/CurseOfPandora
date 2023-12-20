package dev.xkmc.curseofpandora.event;

import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.AttackListener;
import dev.xkmc.l2library.capability.conditionals.ConditionalData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PandoraAttackListener implements AttackListener {

	@Override
	public void onDamageFinalized(AttackCache cache, ItemStack weapon) {
		if (cache.getAttackTarget() instanceof Player player) {
			for (var e : ConditionalData.HOLDER.get(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerDamagedFinal(player, cache);
				}
			}
		}
	}

	@Override
	public void onAttack(AttackCache cache, ItemStack weapon) {
		if (cache.getAttacker() instanceof Player player) {
			for (var e : ConditionalData.HOLDER.get(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerAttackTarget(player, cache);
				}
			}
		}
		if (cache.getAttackTarget() instanceof Player player) {
			for (var e : ConditionalData.HOLDER.get(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerAttacked(player, cache);
				}
			}
		}
	}

	@Override
	public void onHurt(AttackCache cache, ItemStack weapon) {
		if (cache.getAttacker() instanceof Player player) {
			for (var e : ConditionalData.HOLDER.get(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerHurtTarget(player, cache);
				}
			}
		}
		if (cache.getAttackTarget() instanceof Player player) {
			for (var e : ConditionalData.HOLDER.get(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerHurt(player, cache);
				}
			}
		}
	}

	@Override
	public void onDamage(AttackCache cache, ItemStack weapon) {
		if (cache.getAttacker() instanceof Player player) {
			for (var e : ConditionalData.HOLDER.get(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerDamageTarget(player, cache);
				}
			}
		}
		if (cache.getAttackTarget() instanceof Player player) {
			for (var e : ConditionalData.HOLDER.get(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerDamaged(player, cache);
				}
			}
		}
	}

}
