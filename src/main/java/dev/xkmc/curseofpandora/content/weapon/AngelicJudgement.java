package dev.xkmc.curseofpandora.content.weapon;

import dev.xkmc.curseofpandora.content.entity.WindBladeEntity;
import dev.xkmc.curseofpandora.content.entity.WindBladeWeapon;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPDamageTypeGen;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AngelicJudgement extends SwordItem implements EmptyClickListener, WindBladeWeapon {

	private static int getIndexReq() {
		return CoPConfig.COMMON.weapon.angelicJudgementRealityIndex.get();
	}

	public AngelicJudgement(Properties props) {
		super(WeaponTier.ANGELIC_JUDGEMENT, 10, -2.4f, props);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Weapon.ANGELIC_JUDGEMENT.get()
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
		int deg = player.isShiftKeyDown() ? 5 : 20;
		if (!level.isClientSide()) {
			for (int i = -2; i <= 2; i++) {
				float y = player.getYRot() + i * deg;
				float f = 90;
				WindBladeEntity e = new WindBladeEntity(level);
				e.setOwner(player);
				e.setPos(player.getX(), player.getEyeY() - 0.5f, player.getZ());
				e.shootFromRotation(player, player.getXRot(), y, 0, velocity, 0);
				e.setProperties(dmg, Math.round(dist / velocity), f, stack);
				level.addFreshEntity(e);
			}
		}
	}

	@Override
	public DamageSource getSource(WindBladeEntity entity, @Nullable Entity owner) {
		return new DamageSource(CoPDamageTypeGen.forKey(entity.level(), CoPDamageTypeGen.WIND_BLADE), entity, owner);
	}

	@Override
	public void onHit(WindBladeEntity entity) {
	}

	@Override
	public boolean glow() {
		return true;
	}

	@Override
	public ResourceLocation bladeTexture() {
		return new ResourceLocation(CurseOfPandora.MODID, "textures/entity/angelic_blade.png");
	}

	@Override
	public ParticleOptions getParticle() {
		return ParticleTypes.WAX_OFF;
	}

}
