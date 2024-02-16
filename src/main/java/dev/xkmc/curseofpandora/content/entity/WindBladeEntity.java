package dev.xkmc.curseofpandora.content.entity;

import dev.xkmc.curseofpandora.init.data.CoPDamageTypeGen;
import dev.xkmc.curseofpandora.init.registrate.CoPEntities;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
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

	@SerialClass.SerialField
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
				double cr = L2DamageTracker.CRIT_RATE.get().getWrappedValue(player);
				double cd = L2DamageTracker.CRIT_DMG.get().getWrappedValue(player);
				double strength = L2DamageTracker.BOW_STRENGTH.get().getWrappedValue(player);
				if (player.getRandom().nextDouble() < cr) {
					strength *= 1.0 + cd;
				}
				dmg *= (float) strength;
			}
			entity.hurt(source, dmg);
			if (owner instanceof LivingEntity) {
				doEnchantDamageEffects((LivingEntity) owner, entity);
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
		buffer.writeItemStack(issuer, true);
	}

	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
		zrot = additionalData.readFloat();
		issuer = additionalData.readItem();
	}

	public ItemStack getStack() {
		return issuer;
	}

}
