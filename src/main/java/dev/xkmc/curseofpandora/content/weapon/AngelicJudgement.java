package dev.xkmc.curseofpandora.content.weapon;

import dev.xkmc.curseofpandora.content.entity.WindBladeEntity;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AngelicJudgement extends SwordItem implements EmptyClickListener {

	public AngelicJudgement(Properties props) {
		super(WeaponTier.ANGELIC_JUDGEMENT, 10, -2.4f, props);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(CoPLangData.Weapon.ANGELIC_JUDGEMENT.get().withStyle(ChatFormatting.GRAY));
	}

	@Override
	public void clickEmpty(ItemStack stack, Player player) {
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
			e.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, velocity, 1);
			e.setProperties(dmg, Math.round(dist / velocity), f, stack);
			level.addFreshEntity(e);
		}
	}

}
