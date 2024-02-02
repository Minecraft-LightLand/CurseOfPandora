package dev.xkmc.curseofpandora.content.weapon;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface EmptyClickListener {

	void clickEmpty(ItemStack stack, Player entity);

}
