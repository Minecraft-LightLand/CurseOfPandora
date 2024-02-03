package dev.xkmc.curseofpandora.event;

import dev.xkmc.curseofpandora.content.weapon.EmptyClickListener;
import dev.xkmc.l2complements.events.ItemUseEventHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemClickListener implements ItemUseEventHandler.ItemClickHandler {

	@Override
	public boolean predicate(ItemStack stack, Class<? extends PlayerEvent> cls, PlayerEvent event) {
		return stack.getItem() instanceof EmptyClickListener;
	}

	@Override
	public void onPlayerLeftClickEmpty(ItemStack stack, PlayerInteractEvent.LeftClickEmpty event) {
		if (stack.getItem() instanceof EmptyClickListener empty) {
			empty.clickEmpty(stack, event.getEntity());
		}
	}

	@Override
	public void onPlayerLeftClickBlock(ItemStack stack, PlayerInteractEvent.LeftClickBlock event) {
		if (stack.getItem() instanceof EmptyClickListener empty) {
			empty.clickBlock(stack, event.getEntity());
		}
	}

	@Override
	public void onPlayerLeftClickEntity(ItemStack stack, AttackEntityEvent event) {
		if (stack.getItem() instanceof EmptyClickListener empty) {
			empty.clickEntity(stack, event.getEntity());
		}
	}
}
