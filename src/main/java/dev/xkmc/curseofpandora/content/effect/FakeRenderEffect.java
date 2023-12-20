package dev.xkmc.curseofpandora.content.effect;

import dev.xkmc.l2library.base.effects.api.DelayedEntityRender;
import dev.xkmc.l2library.base.effects.api.IconOverlayEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;

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
		ResourceLocation id = ForgeRegistries.MOB_EFFECTS.getKey(this);
		assert id != null;
		String path = id.getPath();
		if (max > 0) {
			path += "_" + Math.min(max, i);
		}
		return DelayedEntityRender.icon(entity, new ResourceLocation(id.getNamespace(),
				"textures/effect_overlay/" + path + ".png"));
	}

}
