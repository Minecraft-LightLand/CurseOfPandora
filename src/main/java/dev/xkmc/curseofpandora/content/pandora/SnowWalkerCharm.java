package dev.xkmc.curseofpandora.content.pandora;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.function.Supplier;

public class SnowWalkerCharm extends EnchDescItem implements ICurioItem {

	public SnowWalkerCharm(Properties properties, Supplier<Enchantment> sup) {
		super(properties, sup);
	}

	@Override
	public boolean canWalkOnPowderedSnow(SlotContext slotContext, ItemStack stack) {
		return true;
	}

}

