package dev.xkmc.curseofpandora.event;

import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2complements.init.data.DamageTypeGen;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.AttackListener;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
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
		var event = cache.getLivingAttackEvent();
		assert event != null;
		if (event.getSource().is(DamageTypeGen.SOUL_FLAME)) {
			return;
		}
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
		var event = cache.getLivingHurtEvent();
		assert event != null;
		if (event.getSource().is(DamageTypeGen.SOUL_FLAME)) {
			return;
		}
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
		var event = cache.getLivingDamageEvent();
		assert event != null;
		if (event.getSource().is(DamageTypeGen.SOUL_FLAME)) {
			return;
		}
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
		var ins = cache.getAttackTarget().getAttribute(CoPMisc.ABSORB.get());
		if (ins != null) {
			float val = (float) ins.getValue();
			cache.addDealtModifier(DamageModifier.nonlinearPre(943, e -> Math.max(0, e - val)));
		}
	}

}
