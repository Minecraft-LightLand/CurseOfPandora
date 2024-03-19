package dev.xkmc.curseofpandora.content.pandora;

import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.function.Supplier;

public class EnderMaskCharm extends EnchDescItem implements ICurioItem {

	public EnderMaskCharm(Properties properties, Supplier<Enchantment> sup) {
		super(properties, sup);
	}

	@Override
	public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan, ItemStack stack) {
		return true;
	}

}

