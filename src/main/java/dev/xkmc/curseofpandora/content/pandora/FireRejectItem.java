package dev.xkmc.curseofpandora.content.pandora;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class FireRejectItem extends EffectRefreshItem {

	public FireRejectItem(Properties properties) {
		super(properties, () -> new MobEffectInstance(
				MobEffects.FIRE_RESISTANCE, 40, 1, true, true));
	}

	@Override
	public void curioTick(LivingEntity entity) {
		super.curioTick(entity);
		if (entity.isOnFire()) {
			entity.clearFire();
		}
	}

}
