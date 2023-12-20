package dev.xkmc.curseofpandora.content.complex;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;
import java.util.List;

public record BaseCurseCurio(Item item, ItemStack stack) implements ICurio {

	@Override
	public List<Component> getAttributesTooltip(List<Component> tooltips) {
		var ans = new ArrayList<>(ICurio.super.getAttributesTooltip(tooltips));
		if (item instanceof ISlotAdderItem<?> sa) {
			for (var e : sa.getSlotAdder()) {
				ans.add(e.getTooltip());
			}
		}
		return ans;
	}

	@Override
	public void curioTick(SlotContext slotContext) {
		if (item instanceof ITokenProviderItem<?> pvd) {
			if (slotContext.entity() instanceof Player player && player.isAlive()) {
				pvd.tick(player);
			}
		}
	}

	@Override
	public ItemStack getStack() {
		return stack();
	}

}
