package dev.xkmc.curseofpandora.content.pandora;

import dev.xkmc.l2core.init.reg.ench.EnchVal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class EnderMaskCharm extends EnchDescItem implements ICurioItem {

	public EnderMaskCharm(Properties properties, EnchVal sup) {
		super(properties, sup);
	}

	@Override
	public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan, ItemStack stack) {
		return true;
	}

}

