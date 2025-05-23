package dev.xkmc.curseofpandora.content.effect;

import dev.xkmc.l2core.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2core.base.effects.api.IconOverlayEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FakeRenderEffect extends MobEffect implements IconOverlayEffect {

	private final int max;

	public FakeRenderEffect(int max) {
		super(MobEffectCategory.NEUTRAL, 0xffffff);
		this.max = max;
	}

	public FakeRenderEffect() {
		this(0);
	}

	@Override
	public DelayedEntityRender getIcon(LivingEntity entity, int i) {
		ResourceLocation id = BuiltInRegistries.MOB_EFFECT.getKey(this);
		assert id != null;
		String path = id.getPath();
		if (max > 0) {
			path += "_" + Math.min(max, i);
		}
		return DelayedEntityRender.icon(entity, id.withPath("textures/effect_overlay/" + path + ".png"));
	}

}
