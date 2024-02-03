package dev.xkmc.curseofpandora.content.weapon;

import dev.xkmc.curseofpandora.content.entity.AbyssalFangs;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbyssalEdge extends SwordItem implements EmptyClickListener {

	private static int getIndexReq() {
		return CoPConfig.COMMON.weapon.abyssalEdgeRealityIndex.get();
	}

	public AbyssalEdge(Properties props) {
		super(WeaponTier.ABYSSAL_EDGE, 10, -2.4f, props);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Weapon.ABYSSAL_EDGE.get()
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	private void attack(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY.get()) < getIndexReq()) {
			return;
		}
		float strength = player.getAttackStrengthScale(0.5f);
		if (strength < 0.9f)
			return;
		if (player.isShiftKeyDown()) AbyssalFangs.circle(player);
		else AbyssalFangs.straight(player);
	}

	@Override
	public void clickEmpty(ItemStack stack, Player player) {
		attack(player);
		player.resetAttackStrengthTicker();
	}

	@Override
	public void clickBlock(ItemStack stack, Player player) {
		attack(player);
	}

	@Override
	public void clickEntity(ItemStack stack, Player player) {
		attack(player);
	}

}
