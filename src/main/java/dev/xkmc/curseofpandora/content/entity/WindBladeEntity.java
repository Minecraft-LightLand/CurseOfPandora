package dev.xkmc.curseofpandora.content.entity;

import dev.xkmc.curseofpandora.init.data.CoPDamageTypeGen;
import dev.xkmc.curseofpandora.init.registrate.CoPEntities;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class WindBladeEntity extends ThrowableProjectile implements IEntityAdditionalSpawnData {

	@SerialClass.SerialField
	public float damage = 3;
	@SerialClass.SerialField
	public int last = 200;
	@SerialClass.SerialField
	public float zrot = 0f;

	private ItemStack issuer = ItemStack.EMPTY;

	public WindBladeEntity(EntityType<? extends WindBladeEntity> type, Level w) {
		super(type, w);
	}

	public WindBladeEntity(Level w) {
		this(CoPEntities.WIND_BLADE.get(), w);
	}

	@Override
	protected void defineSynchedData() {
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

		double vx = velocity.x;
		double vy = velocity.y;
		double vz = velocity.z;
		for (int i = 0; i < 4; ++i) {
			level().addParticle(ParticleTypes.CRIT,
					this.getX() + vx * (double) i / 4.0D,
					this.getY() + vy * (double) i / 4.0D,
					this.getZ() + vz * (double) i / 4.0D,
					-vx, -vy + 0.2, -vz);
		}
	}

	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!level().isClientSide) {
			discard();
		}
	}

	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		if (!level().isClientSide) {
			Entity entity = result.getEntity();
			Entity owner = this.getOwner();
			DamageSource source = new DamageSource(CoPDamageTypeGen.forKey(level(), CoPDamageTypeGen.WIND_BLADE), entity, owner);
			entity.hurt(source, damage);
			if (owner instanceof LivingEntity) {
				doEnchantDamageEffects((LivingEntity) owner, entity);
			}
			discard();
		}
	}

	@Override
	protected float getGravity() {
		return 0;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public float getZRot() {
		return zrot;
	}

	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		buffer.writeFloat(zrot);
	}

	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
		zrot = additionalData.readFloat();
	}
}
