package dev.xkmc.curseofpandora.content.weapon;

import dev.xkmc.curseofpandora.content.entity.WindBladeEntity;
import dev.xkmc.curseofpandora.content.entity.WindBladeWeapon;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPDamageTypeGen;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2library.init.explosion.BaseExplosion;
import dev.xkmc.l2library.init.explosion.BaseExplosionContext;
import dev.xkmc.l2library.init.explosion.ExplosionHandler;
import dev.xkmc.l2library.init.explosion.VanillaExplosionContext;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CursedKarma extends SwordItem implements EmptyClickListener, WindBladeWeapon {

	private static int getIndexReq() {
		return CoPConfig.COMMON.weapon.cursedKarmaRealityIndex.get();
	}

	public static int getRadius() {
		return CoPConfig.COMMON.weapon.cursedKarmaExplosionRadius.get();
	}

	public static int getDuration() {
		return CoPConfig.COMMON.weapon.cursedKarmaEffectDuration.get();
	}

	public CursedKarma(Properties props) {
		super(WeaponTier.CURSED_KARMA, 10, -2.4f, props);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Weapon.CURSED_KARMA.get()
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void clickEmpty(ItemStack stack, Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY.get()) < getIndexReq()) {
			return;
		}
		float strength = player.getAttackStrengthScale(0.5f);
		if (strength < 0.9f)
			return;
		player.resetAttackStrengthTicker();
		Level level = player.level();
		float velocity = 1;
		float dist = 64;
		float dmg = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
		float f = (float) (player.getRandom().nextDouble() * 360f);
		if (!level.isClientSide()) {
			WindBladeEntity e = new WindBladeEntity(level);
			e.setOwner(player);
			e.setPos(player.getX(), player.getEyeY() - 0.5f, player.getZ());
			e.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, velocity, 0);
			e.setProperties(dmg, Math.round(dist / velocity), f, stack);
			level.addFreshEntity(e);
		}
	}

	@Override
	public DamageSource getSource(WindBladeEntity entity, @Nullable Entity owner) {
		return new DamageSource(CoPDamageTypeGen.forKey(entity.level(), CoPDamageTypeGen.SOUL_CURSE), entity, owner);
	}

	@Override
	public void onHit(WindBladeEntity entity) {
		Vec3 pos = entity.position();
		BaseExplosionContext base = new BaseExplosionContext(entity.level(), pos.x, pos.y, pos.z, getRadius());
		VanillaExplosionContext mc = new VanillaExplosionContext(entity, null,
				(ExplosionDamageCalculator) null, false, Explosion.BlockInteraction.KEEP);
		ExplosionHandler.explode(new BaseExplosion(base, mc, e -> isTarget(e, entity.getOwner())));
		entity.discard();
	}

	private boolean isTarget(Entity entity, Entity owner) {
		if (owner != null) {
			if (entity == owner) return false;
			if (entity.isAlliedTo(owner)) return false;
			if (owner.isAlliedTo(entity)) return false;
		}
		int duration = getDuration();
		if (entity instanceof LivingEntity le) {
			if (owner instanceof Player player) {
				EffectUtil.addEffect(le, new MobEffectInstance(LCEffects.FLAME.get(), duration),
						EffectUtil.AddReason.FORCE, player);
				EffectUtil.addEffect(le, new MobEffectInstance(LCEffects.CURSE.get(), duration),
						EffectUtil.AddReason.FORCE, player);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean glow() {
		return true;
	}

	@Override
	public ResourceLocation bladeTexture() {
		return new ResourceLocation(CurseOfPandora.MODID, "textures/entity/flame_blade.png");
	}

	@Override
	public ParticleOptions getParticle() {
		return DustColorTransitionOptions.SCULK_TO_REDSTONE;
	}

}
