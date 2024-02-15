package dev.xkmc.curseofpandora.content.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface WindBladeWeapon {

	DamageSource getSource(WindBladeEntity entity, @Nullable Entity owner);

	default void onHit(WindBladeEntity entity) {
		entity.discard();
	}

	default boolean glow() {
		return false;
	}

	ResourceLocation bladeTexture();

}
