package dev.xkmc.curseofpandora.content.effect;

import dev.xkmc.l2complements.content.effect.skill.SkillEffect;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EmptyEffect extends InherentEffect implements SkillEffect {
	public EmptyEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

}
