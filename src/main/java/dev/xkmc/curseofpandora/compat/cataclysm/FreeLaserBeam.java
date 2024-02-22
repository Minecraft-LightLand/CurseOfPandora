package dev.xkmc.curseofpandora.compat.cataclysm;

import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Death_Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FreeLaserBeam extends Death_Laser_Beam_Entity {

	public FreeLaserBeam(EntityType<? extends FreeLaserBeam> type, Level world) {
		super(type, world);
	}

	public FreeLaserBeam(EntityType<? extends FreeLaserBeam> type, Level world, LivingEntity caster,
						 Vec3 pos, Vec3 dir, float yaw, float pitch, int duration) {
		super(type, world, caster, pos.x, pos.y, pos.z, yaw, pitch, duration);
		setXRot((float) (pitch * 180 / Math.PI));
		setYRot((float) (yaw * 180 / Math.PI));
	}

	public void tick() {
		this.prevCollidePosX = this.collidePosX;
		this.prevCollidePosY = this.collidePosY;
		this.prevCollidePosZ = this.collidePosZ;
		this.prevYaw = this.renderYaw;
		this.prevPitch = this.renderPitch;
		this.xo = this.getX();
		this.yo = this.getY();
		this.zo = this.getZ();
		if (this.tickCount == 1 && this.level().isClientSide) {
			this.caster = (LivingEntity) this.level().getEntity(this.getCasterID());
		}

		updateOrientation();


		if (!this.on && this.appear.getTimer() == 0) {
			this.discard();
		}

		if (this.on && this.tickCount > 20) {
			this.appear.increaseTimer();
		} else {
			this.appear.decreaseTimer();
		}

		if (this.caster != null && !this.caster.isAlive()) {
			this.discard();
		}

		if (this.tickCount > 20) {
			this.calculateEndPos();
			List<LivingEntity> hit = this.raytraceLaser(this.level(), position(),
					new Vec3(this.endPosX, this.endPosY, this.endPosZ),
					true).entities;
			if (this.blockSide != null) {
				this.spawnExplosionParticles(3);
			}
			if (!this.level().isClientSide) {
				for (LivingEntity target : hit) {
					if (this.caster != null && !this.caster.isAlliedTo(target) && target != this.caster) {
						if (target.invulnerableTime > 0) continue;
						float dmg = (float) (CMConfig.DeathLaserdamage +
								Math.min(CMConfig.DeathLaserdamage,
										target.getMaxHealth() * CMConfig.DeathLaserHpdamage));
						boolean flag = target.hurt(CMDamageTypes.causeDeathLaserDamage(this, this.caster), dmg);
						if (this.getFire() && flag) {
							target.setSecondsOnFire(5);
						}
					}
				}
			}
		}

		if (this.tickCount - 20 > this.getDuration()) {
			this.on = false;
		}

	}

	private void updateOrientation() {
		if (caster != null && level().isClientSide) {
			this.renderYaw = getYaw();
			this.renderPitch = getPitch();
		}
	}

	private void calculateEndPos() {
		if (this.level().isClientSide()) {
			this.endPosX = this.getX() + 30.0 * Math.cos(this.renderYaw) * Math.cos(this.renderPitch);
			this.endPosZ = this.getZ() + 30.0 * Math.sin(this.renderYaw) * Math.cos(this.renderPitch);
			this.endPosY = this.getY() + 30.0 * Math.sin(this.renderPitch);
		} else {
			this.endPosX = this.getX() + 30.0 * Math.cos(this.getYaw()) * Math.cos(this.getPitch());
			this.endPosZ = this.getZ() + 30.0 * Math.sin(this.getYaw()) * Math.cos(this.getPitch());
			this.endPosY = this.getY() + 30.0 * Math.sin(this.getPitch());
		}

	}

	private void spawnExplosionParticles(int amount) {
		for (int i = 0; i < amount; ++i) {
			float velocity = 1.0F;
			float yaw = (float) ((this.random.nextFloat() * 2.0F) * Math.PI);
			float motionY = this.random.nextFloat() * 0.08F;
			float motionX = Mth.cos(yaw);
			float motionZ = Mth.sin(yaw);
			this.level().addParticle(new LightningParticle.OrbData(velocity, 0.2F, 0.0F),
					this.collidePosX, this.collidePosY + 0.1, this.collidePosZ,
					motionX, motionY, motionZ);
		}

	}


	public LaserHitResult raytraceLaser(
			Level world, Vec3 from, Vec3 to,
			boolean ignoreBlockWithoutBoundingBox
	) {
		LaserHitResult result = new LaserHitResult();
		result.setBlockHit(world.clip(new ClipContext(from, to, net.minecraft.world.level.ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)));
		if (result.blockHit != null) {
			Vec3 hitVec = result.blockHit.getLocation();
			this.collidePosX = hitVec.x;
			this.collidePosY = hitVec.y;
			this.collidePosZ = hitVec.z;
			this.blockSide = result.blockHit.getDirection();
		} else {
			this.collidePosX = this.endPosX;
			this.collidePosY = this.endPosY;
			this.collidePosZ = this.endPosZ;
			this.blockSide = null;
		}

		var bound = new AABB(position(), new Vec3(collidePosX, collidePosY, collidePosZ)).inflate(1);
		List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, bound);
		for (LivingEntity entity : entities) {
			if (entity != this.caster) {
				float pad = entity.getPickRadius() + 0.5F;
				AABB aabb = entity.getBoundingBox().inflate(pad, pad, pad);
				Optional<Vec3> hit = aabb.clip(from, to);
				if (aabb.contains(from)) {
					result.addEntityHit(entity);
				} else if (hit.isPresent()) {
					result.addEntityHit(entity);
				}
			}
		}

		return result;
	}

	public static class LaserHitResult {

		private BlockHitResult blockHit;
		private final List<LivingEntity> entities = new ArrayList<>();

		public LaserHitResult() {
		}

		public BlockHitResult getBlockHit() {
			return this.blockHit;
		}

		public void setBlockHit(HitResult rayTraceResult) {
			if (rayTraceResult.getType() == HitResult.Type.BLOCK) {
				this.blockHit = (BlockHitResult) rayTraceResult;
			}
		}

		public void addEntityHit(LivingEntity entity) {
			this.entities.add(entity);
		}
	}

}
