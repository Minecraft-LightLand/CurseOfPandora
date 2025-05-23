package dev.xkmc.curseofpandora.content.hostility;

import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2hostility.content.traits.base.MobTrait;
import dev.xkmc.l2hostility.init.registrate.LHMiscs;
import net.minecraft.ChatFormatting;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class RealityTrait extends MobTrait {

	public RealityTrait(ChatFormatting format) {
		super(format);
	}

	@Override
	public boolean onAttackedByOthers(int level, LivingEntity entity, DamageData.Attack event) {
		if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY))
			return false;
		if (event.getSource().getEntity() instanceof LivingEntity attacker) {
			var ins = attacker.getAttribute(CoPAttrs.REALITY);
			if (ins != null) {
				int val = (int) Math.round(ins.getValue());
				if (val >= level) {
					return false;
				}
			}
			if (attacker instanceof Mob mob) {
				var opt = LHMiscs.MOB.type().getExisting(mob);
				return opt.isEmpty() || opt.get().getTraitLevel(this) < level;
			}
		}
		return true;
	}

	@Override
	public void onDamaged(int level, LivingEntity entity, DamageData.Defence data) {
		if (data.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY))
			return;
		if (data.getSource().getEntity() instanceof LivingEntity attacker) {
			var ins = attacker.getAttribute(CoPAttrs.REALITY);
			if (ins != null) {
				int val = (int) Math.round(ins.getValue());
				if (val >= level) {
					return;
				}
			}
		}
		data.addDealtModifier(DamageModifier.nonlinearFinal(12346, e -> 0, getRegistryName()));
	}

	@Override
	public boolean isBanned() {
		return !CoPConfig.SERVER.compat.allowRealityTrait.get();
	}

}
