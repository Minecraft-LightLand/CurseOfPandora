package dev.xkmc.curseofpandora.content.entity;

import dev.xkmc.curseofpandora.init.registrate.CoPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.UUID;
import java.util.function.Predicate;


@SuppressWarnings("deprecation")
public class EvilSpirit extends PathfinderMob implements TraceableEntity, OwnableEntity {
	public static final float FLAP_DEGREES_PER_TICK = 45.836624F;
	public static final int TICKS_PER_FLAP = Mth.ceil(3.9269907F);
	protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(EvilSpirit.class, EntityDataSerializers.BYTE);
	private static final int FLAG_IS_CHARGING = 1;
	private static final double RIDING_OFFSET = 0.4D;
	@Nullable
	UUID owner;
	@Nullable
	private BlockPos boundOrigin;
	private boolean hasLimitedLife;
	private int limitedLifeTicks;

	public EvilSpirit(EntityType<? extends EvilSpirit> type, Level level) {
		super(type, level);
		this.moveControl = new EvilSpirit.VexMoveControl(this);
	}

	public EvilSpirit(Player player) {
		this(CoPEntities.EVIL_SPIRIT.get(), player.level());
		setOwner(player);
		setPos(player.position());
	}

	@Nullable
	@Override
	public UUID getOwnerUUID() {
		return owner;
	}

	public void setOwner(Player player) {
		this.owner = player.getUUID();
	}

	@Nullable
	public Player getOwner() {
		return owner == null ? null : level().getPlayerByUUID(owner);
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		var player = getOwner();
		if (player != null) {
			if (player == entity) return true;
			if (player.isAlliedTo(entity)) return true;
		}
		return super.isAlliedTo(entity);
	}

	// Vex start

	protected float getStandingEyeHeight(Pose pose, EntityDimensions dim) {
		return dim.height - 0.28125F;
	}

	public boolean isFlapping() {
		return this.tickCount % TICKS_PER_FLAP == 0;
	}

	public void move(MoverType type, Vec3 dir) {
		super.move(type, dir);
		this.checkInsideBlocks();
	}

	public void tick() {
		this.noPhysics = true;
		super.tick();
		this.noPhysics = false;
		this.setNoGravity(true);
		if (this.hasLimitedLife && --this.limitedLifeTicks <= 0) {
			this.limitedLifeTicks = 20;
			this.hurt(this.damageSources().starve(), getMaxHealth());
		}

	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(4, new EvilSpirit.VexChargeAttackGoal());
		this.goalSelector.addGoal(8, new EvilSpirit.VexRandomMoveGoal());
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.ATTACK_DAMAGE, 4.0D);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_FLAGS_ID, (byte) 0);
	}

	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains("BoundX")) {
			this.boundOrigin = new BlockPos(tag.getInt("BoundX"), tag.getInt("BoundY"), tag.getInt("BoundZ"));
		}

		if (tag.contains("LifeTicks")) {
			this.setLimitedLife(tag.getInt("LifeTicks"));
		}

		if (tag.contains("Owner")) {
			owner = tag.getUUID("Owner");
		}

	}

	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		if (this.boundOrigin != null) {
			tag.putInt("BoundX", this.boundOrigin.getX());
			tag.putInt("BoundY", this.boundOrigin.getY());
			tag.putInt("BoundZ", this.boundOrigin.getZ());
		}

		if (this.hasLimitedLife) {
			tag.putInt("LifeTicks", this.limitedLifeTicks);
		}

		if (owner != null) {
			tag.putUUID("Owner", owner);
		}

	}

	@Nullable
	public BlockPos getBoundOrigin() {
		return this.boundOrigin;
	}

	public void setBoundOrigin(@Nullable BlockPos pos) {
		this.boundOrigin = pos;
	}

	private boolean getVexFlag(int flag) {
		int i = this.entityData.get(DATA_FLAGS_ID);
		return (i & flag) != 0;
	}

	private void setVexFlag(int flag, boolean val) {
		int i = this.entityData.get(DATA_FLAGS_ID);
		if (val) {
			i |= flag;
		} else {
			i &= ~flag;
		}

		this.entityData.set(DATA_FLAGS_ID, (byte) (i & 255));
	}

	public boolean isCharging() {
		return this.getVexFlag(1);
	}

	public void setIsCharging(boolean charge) {
		this.setVexFlag(1, charge);
	}

	public void setLimitedLife(int life) {
		this.hasLimitedLife = true;
		this.limitedLifeTicks = life;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.VEX_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.VEX_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.VEX_HURT;
	}

	public float getLightLevelDependentMagicValue() {
		return 1.0F;
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance ins, MobSpawnType type,
										@Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
		RandomSource randomsource = level.getRandom();
		this.populateDefaultEquipmentSlots(randomsource, ins);
		this.populateDefaultEquipmentEnchantments(randomsource, ins);
		return super.finalizeSpawn(level, ins, type, data, tag);
	}

	protected void populateDefaultEquipmentSlots(RandomSource source, DifficultyInstance ins) {
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
		this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
	}

	public double getMyRidingOffset() {
		return RIDING_OFFSET;
	}

	// Monster

	public void aiStep() {
		this.updateSwingTime();
		this.updateNoActionTime();
		super.aiStep();
		LivingEntity target = getTarget();
		if (target != null && target.isAlive()) return;
		Player owner = getOwner();
		if (owner == null) return;
		target = owner.getLastHurtByMob();
		if (target != null && target.isAlive()) return;
		setTarget(target);
	}

	protected void updateNoActionTime() {
		float f = this.getLightLevelDependentMagicValue();
		if (f > 0.5F) {
			this.noActionTime += 2;
		}

	}

	public boolean shouldDropExperience() {
		return false;
	}

	protected boolean shouldDropLoot() {
		return false;
	}

	public ItemStack getProjectile(ItemStack weapon) {
		if (weapon.getItem() instanceof ProjectileWeaponItem) {
			Predicate<ItemStack> predicate = ((ProjectileWeaponItem) weapon.getItem()).getSupportedHeldProjectiles();
			ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(this, predicate);
			return net.minecraftforge.common.ForgeHooks.getProjectile(this, weapon, itemstack.isEmpty() ? new ItemStack(Items.ARROW) : itemstack);
		} else {
			return net.minecraftforge.common.ForgeHooks.getProjectile(this, weapon, ItemStack.EMPTY);
		}
	}

	// Monster end

	class VexChargeAttackGoal extends Goal {
		public VexChargeAttackGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			LivingEntity livingentity = EvilSpirit.this.getTarget();
			return livingentity != null && livingentity.isAlive() && !EvilSpirit.this.getMoveControl().hasWanted();
		}

		public boolean canContinueToUse() {
			return EvilSpirit.this.getMoveControl().hasWanted() && EvilSpirit.this.isCharging() &&
					EvilSpirit.this.getTarget() != null && EvilSpirit.this.getTarget().isAlive();
		}

		public void start() {
			LivingEntity livingentity = EvilSpirit.this.getTarget();
			if (livingentity != null) {
				Vec3 vec3 = livingentity.getEyePosition();
				EvilSpirit.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
			}

			EvilSpirit.this.setIsCharging(true);
			EvilSpirit.this.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
		}

		public void stop() {
			EvilSpirit.this.setIsCharging(false);
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			LivingEntity livingentity = EvilSpirit.this.getTarget();
			if (livingentity != null) {
				if (EvilSpirit.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
					EvilSpirit.this.doHurtTarget(livingentity);
					EvilSpirit.this.setIsCharging(false);
				} else {
					double d0 = EvilSpirit.this.distanceToSqr(livingentity);
					if (d0 < 9.0D) {
						Vec3 vec3 = livingentity.getEyePosition();
						EvilSpirit.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
					}
				}

			}
		}
	}

	class VexMoveControl extends MoveControl {
		public VexMoveControl(EvilSpirit p_34062_) {
			super(p_34062_);
		}

		public void tick() {
			if (this.operation == MoveControl.Operation.MOVE_TO) {
				Vec3 vec3 = new Vec3(this.wantedX - EvilSpirit.this.getX(), this.wantedY - EvilSpirit.this.getY(), this.wantedZ - EvilSpirit.this.getZ());
				double d0 = vec3.length();
				if (d0 < EvilSpirit.this.getBoundingBox().getSize()) {
					this.operation = MoveControl.Operation.WAIT;
					EvilSpirit.this.setDeltaMovement(EvilSpirit.this.getDeltaMovement().scale(0.5D));
				} else {
					EvilSpirit.this.setDeltaMovement(EvilSpirit.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05D / d0)));
					if (EvilSpirit.this.getTarget() == null) {
						Vec3 vec31 = EvilSpirit.this.getDeltaMovement();
						EvilSpirit.this.setYRot(-((float) Mth.atan2(vec31.x, vec31.z)) * (180F / (float) Math.PI));
						EvilSpirit.this.yBodyRot = EvilSpirit.this.getYRot();
					} else {
						double d2 = EvilSpirit.this.getTarget().getX() - EvilSpirit.this.getX();
						double d1 = EvilSpirit.this.getTarget().getZ() - EvilSpirit.this.getZ();
						EvilSpirit.this.setYRot(-((float) Mth.atan2(d2, d1)) * (180F / (float) Math.PI));
						EvilSpirit.this.yBodyRot = EvilSpirit.this.getYRot();
					}
				}

			}
		}
	}

	class VexRandomMoveGoal extends Goal {
		public VexRandomMoveGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			return !EvilSpirit.this.getMoveControl().hasWanted() && EvilSpirit.this.random.nextInt(reducedTickDelay(7)) == 0;
		}

		public boolean canContinueToUse() {
			return false;
		}

		public void tick() {
			BlockPos blockpos = EvilSpirit.this.getBoundOrigin();
			if (blockpos == null) {
				blockpos = EvilSpirit.this.blockPosition();
			}

			for (int i = 0; i < 3; ++i) {
				BlockPos blockpos1 = blockpos.offset(EvilSpirit.this.random.nextInt(15) - 7, EvilSpirit.this.random.nextInt(11) - 5, EvilSpirit.this.random.nextInt(15) - 7);
				if (EvilSpirit.this.level().isEmptyBlock(blockpos1)) {
					EvilSpirit.this.moveControl.setWantedPosition((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY() + 0.5D, (double) blockpos1.getZ() + 0.5D, 0.25D);
					if (EvilSpirit.this.getTarget() == null) {
						EvilSpirit.this.getLookControl().setLookAt((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY() + 0.5D, (double) blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
					}
					break;
				}
			}

		}
	}
}