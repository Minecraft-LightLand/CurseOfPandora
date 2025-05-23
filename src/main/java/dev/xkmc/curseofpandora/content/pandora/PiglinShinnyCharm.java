package dev.xkmc.curseofpandora.content.pandora;

import dev.xkmc.l2core.init.reg.ench.EnchVal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.function.Supplier;

public class PiglinShinnyCharm extends EnchDescItem implements ICurioItem {

	public PiglinShinnyCharm(Properties properties, EnchVal sup) {
		super(properties, sup);
	}

	@Override
	public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack) {
		return true;
	}

}
