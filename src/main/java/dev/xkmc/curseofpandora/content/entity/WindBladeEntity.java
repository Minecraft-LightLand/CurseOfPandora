package dev.xkmc.curseofpandora.content.entity;

import dev.xkmc.curseofpandora.init.data.CoPDamageTypeGen;
import dev.xkmc.curseofpandora.init.registrate.CoPEntities;
import dev.xkmc.l2core.util.MathHelper;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;

@SerialClass
public class WindBladeEntity extends ThrowableProjectile implements IEntityWithComplexSpawn {

	@SerialField
	public float damage = 3;
	@SerialField
	public int last = 200;
	@SerialField
	public float zrot = 0f;

	@SerialField
	private ItemStack issuer = ItemStack.EMPTY;

	public WindBladeEntity(EntityType<? extends WindBladeEntity> type, Level w) {
		super(type, w);
	}

	public WindBladeEntity(Level w) {
		this(CoPEntities.WIND_BLADE.get(), w);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {

	}

	public void setProperties(float damage, int last, float zrot, ItemStack issuer) {
		this.damage = damage;
		this.last = last;
		this.zrot = zrot;
		this.issuer = issuer;

		Vec3 vector3d = this.getDeltaMovement();
		float f = Mth.sqrt((float) MathHelper.horSq(vector3d));
		this.setXRot((float) (Mth.atan2(vector3d.y, f) * (double) (180F / (float) Math.PI)));
		this.setYRot((float) (Mth.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI)));
		this.xRotO = this.getXRot();
		this.yRotO = this.getYRot();

	}

	@Override
	public void tick() {
		Vec3 velocity = getDeltaMovement();
		super.tick();
		this.setDeltaMovement(velocity);
		last--;
		if (last <= 0) {
			discard();
		}
		ParticleOptions particle = issuer.getItem() instanceof WindBladeWeapon weapon ?
				weapon.getParticle() : ParticleTypes.CRIT;
		double vx = velocity.x;
		double vy = velocity.y;
		double vz = velocity.z;
		for (int i = 0; i < 4; ++i) {
			level().addParticle(particle,
					this.getX() + vx * (double) i / 4.0D,
					this.getY() + vy * (double) i / 4.0D,
					this.getZ() + vz * (double) i / 4.0D,
					0, 0, 0);
		}
	}

	protected void onHit(HitResult result) {
		super.onHit(result);
	}

	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		if (!level().isClientSide) {
			Entity entity = result.getEntity();
			Entity owner = this.getOwner();
			DamageSource source;
			if (issuer.getItem() instanceof WindBladeWeapon weapon) {
				source = weapon.getSource(this, owner);
			} else {
				source = new DamageSource(CoPDamageTypeGen.forKey(level(), CoPDamageTypeGen.WIND_BLADE), entity, owner);
			}
			float dmg = damage;
			if (getOwner() instanceof Player player) {
				double cr = player.getAttributeValue(L2DamageTracker.CRIT_RATE);
				double cd = player.getAttributeValue(L2DamageTracker.CRIT_DMG);
				double strength = player.getAttributeValue(L2DamageTracker.BOW_STRENGTH);
				if (player.getRandom().nextDouble() < cr) {
					strength *= 1.0 + cd;
				}
				dmg *= (float) strength;
			}
			entity.hurt(source, dmg);
			if (owner instanceof LivingEntity && level() instanceof ServerLevel sl) {
				EnchantmentHelper.doPostAttackEffects(sl, entity, source);
			}
			if (issuer.getItem() instanceof WindBladeWeapon weapon) {
				weapon.onHit(this);
			} else {
				discard();
			}
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult hit) {
		super.onHitBlock(hit);
		if (!level().isClientSide) {
			if (issuer.getItem() instanceof WindBladeWeapon weapon) {
				weapon.onHit(this);
			}
			discard();
		}
	}

	@Override
	public boolean isNoGravity() {
		return true;
	}

	public float getZRot() {
		return zrot;
	}

	@Override
	public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
		buffer.writeFloat(zrot);
		ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, issuer);
	}

	@Override
	public void readSpawnData(RegistryFriendlyByteBuf additionalData) {
		zrot = additionalData.readFloat();
		issuer = ItemStack.OPTIONAL_STREAM_CODEC.decode(additionalData);
	}

	public ItemStack getStack() {
		return issuer;
	}

	public void shootFromRotation(Entity user, float xr, float yr, float angle, float v, float rand) {
		float f = -Mth.sin(yr * 0.017453292F) * Mth.cos(xr * 0.017453292F);
		float f1 = -Mth.sin((xr + angle) * 0.017453292F);
		float f2 = Mth.cos(yr * 0.017453292F) * Mth.cos(xr * 0.017453292F);
		this.shoot(f, f1, f2, v, rand);
		Vec3 vec3 = user.getDeltaMovement();
		if (vec3.length() < v * 0.75) return;
		this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, user.onGround() ? 0.0 : vec3.y, vec3.z));
	}

}
