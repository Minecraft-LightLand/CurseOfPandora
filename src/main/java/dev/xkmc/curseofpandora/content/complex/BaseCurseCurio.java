package dev.xkmc.curseofpandora.content.complex;

import net.minecraft.ChatFormatting;
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
	public ItemStack getStack() {
		return stack();
	}

}
