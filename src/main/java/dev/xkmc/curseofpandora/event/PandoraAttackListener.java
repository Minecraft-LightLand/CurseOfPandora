package dev.xkmc.curseofpandora.event;

import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.entity.WindBladeEntity;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.registrate.CoPEffects;
import dev.xkmc.l2core.init.L2LibReg;
import dev.xkmc.l2damagetracker.contents.attack.AttackListener;
import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
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
					for (var e : L2LibReg.CONDITIONAL.type().getOrCreate(player).data.values()) {
						if (e instanceof IAttackListenerToken token) {
							token.onCreateSource(player, event);
						}
					}
				}
			}
	}

	@Override
	public void setupProfile(DamageData data, BiConsumer<LivingEntity, ItemStack> profile) {
		if (data.getSource().getDirectEntity() instanceof WindBladeEntity e) {
			if (e.getOwner() instanceof LivingEntity le && !e.getStack().isEmpty()) {
				profile.accept(le, e.getStack());
			}
		}
	}

	@Override
	public void onDamageFinalized(DamageData.DefenceMax data) {
		if (data.getSource().is(L2DamageTypes.NO_SCALE)) {
			return;
		}
		if (data.getTarget() instanceof Player player) {
			for (var e : L2LibReg.CONDITIONAL.type().getOrCreate(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerDamagedFinal(player, data);
				}
			}
		}
		if (data.getAttacker() instanceof Player player) {
			for (var e : L2LibReg.CONDITIONAL.type().getOrCreate(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerDamageTargetFinal(player, data);
				}
			}
		}
	}

	@Override
	public boolean onAttack(DamageData.Attack data) {
		if (data.getTarget() instanceof Player player) {
			for (var e : L2LibReg.CONDITIONAL.type().getOrCreate(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					if (token.onPlayerAttacked(player, data))
						return true;
				}
			}
		}
		if (data.getSource().is(L2DamageTypes.NO_SCALE)) {
			return false;
		}
		if (data.getAttacker() instanceof Player player) {
			for (var e : L2LibReg.CONDITIONAL.type().getOrCreate(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					if (token.onPlayerAttackTarget(player, data))
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onHurt(DamageData.Offence data) {
		if (data.getSource().is(L2DamageTypes.NO_SCALE)) {
			return;
		}
		if (data.getAttacker() instanceof Player player) {
			for (var e : L2LibReg.CONDITIONAL.type().getOrCreate(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerHurtTarget(player, data);
				}
			}
		}
		if (data.getTarget() instanceof Player player) {
			for (var e : L2LibReg.CONDITIONAL.type().getOrCreate(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerHurt(player, data);
				}
			}
		}
	}

	@Override
	public void onDamage(DamageData.Defence data) {
		if (!data.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			if (!data.getSource().is(DamageTypeTags.BYPASSES_EFFECTS)) {
				if (data.getAttacker() != null && data.getAttacker().hasEffect(CoPEffects.SHADOW)) {
					var id = CoPEffects.SHADOW.key().location();
					var factor = (float) (1 - CoPConfig.SERVER.shadow.damageReduction.get());
					data.addDealtModifier(DamageModifier.multTotal(factor, id));
				}
			}
		}
		if (data.getSource().is(L2DamageTypes.NO_SCALE)) {
			return;
		}
		if (data.getAttacker() instanceof Player player) {
			for (var e : L2LibReg.CONDITIONAL.type().getOrCreate(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerDamageTarget(player, data);
				}
			}
		}
		if (data.getTarget() instanceof Player player) {
			for (var e : L2LibReg.CONDITIONAL.type().getOrCreate(player).data.values()) {
				if (e instanceof IAttackListenerToken token) {
					token.onPlayerDamaged(player, data);
				}
			}
		}
	}

}
