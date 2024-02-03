package dev.xkmc.curseofpandora.content.entity;

import dev.xkmc.curseofpandora.content.sets.abyss.AbyssalCrown;
import dev.xkmc.curseofpandora.content.sets.abyss.AbyssalWill;
import dev.xkmc.curseofpandora.init.data.CoPDamageTypeGen;
import dev.xkmc.curseofpandora.init.registrate.CoPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.UUID;

public class AbyssalFangs extends Entity implements TraceableEntity {

	public static void circle(LivingEntity owner) {
		double d0 = owner.getY() - 5;
		double d1 = owner.getY() + 5;
		float f = (owner.getYRot() + 90) * ((float) Math.PI / 180F);
		for (int i = 0; i < 5; ++i) {
			float f1 = f + (float) i * (float) Math.PI * 0.4F;
			createSpellEntity(owner, owner.getX() + Mth.cos(f1) * 1.5D, owner.getZ() + Mth.sin(f1) * 1.5D, d0, d1, f1, 0);
		}
		for (int k = 0; k < 8; ++k) {
			float f2 = f + (float) k * (float) Math.PI * 2.0F / 8.0F + 1.2566371F;
			createSpellEntity(owner, owner.getX() + Mth.cos(f2) * 2.5D, owner.getZ() + Mth.sin(f2) * 2.5D, d0, d1, f2, 3);
		}

	}

	public static void straight(LivingEntity owner) {
		double d0 = owner.getY() - 5;
		double d1 = owner.getY() + 5;
		float f = (owner.getYRot() + 90) * ((float) Math.PI / 180F);
		for (int i = 0; i < 16; ++i) {
			double d2 = 1.25D * (double) (i + 1);
			createSpellEntity(owner, owner.getX() + Mth.cos(f) * d2, owner.getZ() + Mth.sin(f) * d2, d0, d1, f, i);
		}

	}

	private static void createSpellEntity(LivingEntity owner, double x, double z, double y0, double y1, float rot, int delay) {
		Level level = owner.level();
		BlockPos origin = BlockPos.containing(x, y1, z);
		boolean flag = false;
		double height = 0.0D;
		do {
			BlockPos below = origin.below();
			BlockState down = level.getBlockState(below);
			if (down.isFaceSturdy(level, below, Direction.UP)) {
				if (!level.isEmptyBlock(origin)) {
					BlockState up = level.getBlockState(origin);
					VoxelShape voxelshape = up.getCollisionShape(level, origin);
					if (!voxelshape.isEmpty()) {
						height = voxelshape.max(Direction.Axis.Y);
					}
				}
				flag = true;
				break;
			}
			origin = origin.below();
		} while (origin.getY() >= Mth.floor(y0) - 1);
		if (flag) {
			level.addFreshEntity(new AbyssalFangs(level, x, (double) origin.getY() + height, z, rot, delay, owner));
		}
	}


	public static final int ATTACK_DURATION = 20;
	public static final int LIFE_OFFSET = 2;
	public static final int ATTACK_TRIGGER_TICKS = 14;
	private int warmupDelayTicks;
	private boolean sentSpikeEvent;
	private int lifeTicks = ATTACK_DURATION + LIFE_OFFSET;
	private boolean clientSideAttackStarted;
	@Nullable
	private LivingEntity owner;
	@Nullable
	private UUID ownerUUID;

	public AbyssalFangs(EntityType<? extends AbyssalFangs> type, Level level) {
		super(type, level);
	}

	public AbyssalFangs(Level level, double x, double y, double z, float yrot, int delay, LivingEntity owner) {
		this(CoPEntities.ABYSSAL_FANGS.get(), level);
		this.warmupDelayTicks = delay;
		this.setOwner(owner);
		this.setYRot(yrot * (180F / (float) Math.PI));
		this.setPos(x, y, z);
	}

	protected void defineSynchedData() {
	}

	public void setOwner(@Nullable LivingEntity owner) {
		this.owner = owner;
		this.ownerUUID = owner == null ? null : owner.getUUID();
	}

	@Nullable
	public LivingEntity getOwner() {
		if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel) {
			Entity entity = ((ServerLevel) this.level()).getEntity(this.ownerUUID);
			if (entity instanceof LivingEntity) {
				this.owner = (LivingEntity) entity;
			}
		}

		return this.owner;
	}

	protected void readAdditionalSaveData(CompoundTag tag) {
		this.warmupDelayTicks = tag.getInt("Warmup");
		if (tag.hasUUID("Owner")) {
			this.ownerUUID = tag.getUUID("Owner");
		}

	}

	protected void addAdditionalSaveData(CompoundTag tag) {
		tag.putInt("Warmup", this.warmupDelayTicks);
		if (this.ownerUUID != null) {
			tag.putUUID("Owner", this.ownerUUID);
		}

	}

	public void tick() {
		super.tick();
		if (this.level().isClientSide) {
			if (this.clientSideAttackStarted) {
				--this.lifeTicks;
				if (this.lifeTicks == ATTACK_TRIGGER_TICKS) {
					for (int i = 0; i < 12; ++i) {
						double d0 = this.getX() + (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getBbWidth() * 0.5D;
						double d1 = this.getY() + 0.05D + this.random.nextDouble();
						double d2 = this.getZ() + (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getBbWidth() * 0.5D;
						double d3 = (this.random.nextDouble() * 2.0D - 1.0D) * 0.3D;
						double d4 = 0.3D + this.random.nextDouble() * 0.3D;
						double d5 = (this.random.nextDouble() * 2.0D - 1.0D) * 0.3D;
						this.level().addParticle(ParticleTypes.CRIT, d0, d1 + 1.0D, d2, d3, d4, d5);
					}
				}
			}
		} else if (--this.warmupDelayTicks < 0) {
			if (this.warmupDelayTicks == -8) {
				for (LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2D, 0.0D, 0.2D))) {
					this.dealDamageTo(livingentity);
				}
			}

			if (!this.sentSpikeEvent) {
				this.level().broadcastEntityEvent(this, (byte) 4);
				this.sentSpikeEvent = true;
			}

			if (--this.lifeTicks < 0) {
				this.discard();
			}
		}

	}

	private void dealDamageTo(LivingEntity target) {
		LivingEntity owner = this.getOwner();
		if (target.isAlive() && !target.isInvulnerable() && target != owner) {
			if (owner instanceof Player player) {
				if (owner.isAlliedTo(target)) {
					return;
				}
				int step = AbyssalWill.getStep(player);
				var type = CoPDamageTypeGen.ABYSSAL_FANG;
				if (step * AbyssalCrown.getChance() > player.getRandom().nextDouble()) {
					type = CoPDamageTypeGen.ECHO_ABYSSAL_FANG;
				}
				target.hurt(new DamageSource(CoPDamageTypeGen.forKey(level(), type), this, owner),
						(float) player.getAttributeValue(Attributes.ATTACK_DAMAGE));
			} else {
				target.hurt(this.damageSources().magic(), 6.0F);
			}

		}
	}

	public void handleEntityEvent(byte event) {
		super.handleEntityEvent(event);
		if (event == 4) {
			clientSideAttackStarted = true;
			if (!isSilent()) {
				level().playLocalSound(getX(), getY(), getZ(), SoundEvents.EVOKER_FANGS_ATTACK,
						getSoundSource(), 1.0F, random.nextFloat() * 0.2F + 0.85F, false);
			}
		}

	}

	public float getAnimationProgress(float pTick) {
		if (!this.clientSideAttackStarted) {
			return 0.0F;
		} else {
			int i = this.lifeTicks - 2;
			return i <= 0 ? 1.0F : 1.0F - ((float) i - pTick) / 20.0F;
		}
	}


}
