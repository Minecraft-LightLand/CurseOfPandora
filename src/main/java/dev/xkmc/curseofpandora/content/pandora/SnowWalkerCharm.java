package dev.xkmc.curseofpandora.content.pandora;

import dev.xkmc.l2core.init.reg.ench.EnchVal;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class SnowWalkerCharm extends EnchDescItem implements ICurioItem {

	public SnowWalkerCharm(Properties properties, EnchVal sup) {
		super(properties, sup);
	}

	@Override
	public boolean canWalkOnPowderedSnow(SlotContext slotContext, ItemStack stack) {
		return true;
	}

}

