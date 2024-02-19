package dev.xkmc.curseofpandora.event;

import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.entity.WindBladeEntity;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.registrate.CoPEffects;
import dev.xkmc.l2complements.init.data.DamageTypeGen;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.AttackListener;
import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import dev.xkmc.l2library.capability.conditionals.ConditionalData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiConsumer;

public class PandoraAttackListener implements AttackListener {

	@Override
	public void onCreateSource(CreateSourceEvent event) {
		if (event.getResult() != null)
			if (event.getResult().toRoot() == L2DamageTypes.PLAYER_ATTACK) {
				if (event.getAttacker() instanceof Player player) {
					for (var e : ConditionalData.HOLDER.get(player).data.values()) {
						if (e instanceof IAttackListenerToken token) {
							token.onCreateSource(player, event);
						}
					}
				}
			}
	}

	@Override
	public void setupProfile(AttackCache cache, BiConsumer<LivingEntity, ItemStack> profile) {
		var event = cache.getLivingAttackEvent();
		assert event != null;
		if (event.getSource().getDirectEntity() instanceof WindBladeEntity e) {
			if (e.getOwner() instanceof LivingEntity le && !e.getStack().isEmpty()) {
				profile.accept(le, e.getStack());
			}
		}
	}

	@Override
	public void onDamageFinalized(AttackCache cache, ItemStack weapon) {
		var event = cache.getLivingDamageEvent();
		assert event != null;
		if (event.getSource().is(DamageTypeGen.SOUL_FLAME)) {
			return;
		}
		if (cache.getAttackTarget() instanceof Player player) {
			for (var e : ConditionalData.HOLDER.get(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerDamagedFinal(player, cache);
				}
			}
		}
		if (cache.getAttacker() instanceof Player player) {
			for (var e : ConditionalData.HOLDER.get(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerDamageTargetFinal(player, cache);
				}
			}
		}
	}

	@Override
	public void onAttack(AttackCache cache, ItemStack weapon) {
		var event = cache.getLivingAttackEvent();
		assert event != null;
		if (cache.getAttackTarget() instanceof Player player) {
			for (var e : ConditionalData.HOLDER.get(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerAttacked(player, cache);
					if (event.isCanceled())
						return;
				}
			}
		}
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
		if (!event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			if (!event.getSource().is(DamageTypeTags.BYPASSES_EFFECTS)) {
				if (cache.getAttacker() != null && cache.getAttacker().hasEffect(CoPEffects.SHADOW.get())) {
					cache.addDealtModifier(DamageModifier.multTotal((float) (1 - CoPConfig.COMMON.shadow.damageReduction.get())));
				}
			}
		}
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
	}

}
