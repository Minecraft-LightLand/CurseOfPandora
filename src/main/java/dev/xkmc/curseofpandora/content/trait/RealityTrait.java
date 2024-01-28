package dev.xkmc.curseofpandora.content.trait;

import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2hostility.content.traits.base.MobTrait;
import net.minecraft.ChatFormatting;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class RealityTrait extends MobTrait {

	public RealityTrait(ChatFormatting format) {
		super(format);
	}

	@Override
	public void onAttackedByOthers(int level, LivingEntity entity, LivingAttackEvent event) {
		if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY))
			return;
		if (event.getSource().getEntity() instanceof LivingEntity attacker) {
			var ins = attacker.getAttribute(CoPAttrs.REALITY.get());
			if (ins != null) {
				int val = (int) Math.round(ins.getValue());
				if (val >= level) {
					return;
				}
			}
		}
		event.setCanceled(true);
	}

}
