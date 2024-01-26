package dev.xkmc.curseofpandora.content.effect;

import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.l2library.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2library.base.effects.api.ForceEffect;
import dev.xkmc.l2library.base.effects.api.IconOverlayEffect;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ShadowEffect extends InherentEffect implements ForceEffect, IconOverlayEffect {
	public ShadowEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public DelayedEntityRender getIcon(LivingEntity entity, int lv) {
		return DelayedEntityRender.icon(entity, new ResourceLocation(CurseOfPandora.MODID, "textures/effect_overlay/shadow.png"));
	}

}
