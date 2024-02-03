package dev.xkmc.curseofpandora.content.weapon;

import dev.xkmc.curseofpandora.content.entity.WindBladeEntity;
import dev.xkmc.curseofpandora.content.entity.WindBladeWeapon;
import dev.xkmc.curseofpandora.content.sets.shadow.VoidOverflow;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPDamageTypeGen;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

public class DoomStar extends SwordItem implements EmptyClickListener, WindBladeWeapon {

	private static int getIndexReq() {
		return CoPConfig.COMMON.weapon.doomStarRealityIndex.get();
	}

	public DoomStar(Properties props) {
		super(WeaponTier.DOOM_STAR, 10, -2.4f, props);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Weapon.DOOM_STAR.get()
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
		float velocity = 3;
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
		var type = owner instanceof Player player && VoidOverflow.check(player) ?
				CoPDamageTypeGen.VOID_CURSE : CoPDamageTypeGen.SHADOW_CURSE;
		return new DamageSource(CoPDamageTypeGen.forKey(entity.level(), type), entity, owner);
	}

}
