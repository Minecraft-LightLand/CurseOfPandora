package dev.xkmc.curseofpandora.content.pandora;

import dev.xkmc.l2complements.content.item.curios.ICapItem;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.function.Supplier;

public class EnderMaskCharm extends EnchDescItem implements ICapItem<EnderMaskCharm.Data> {

	public EnderMaskCharm(Properties properties, Supplier<Enchantment> sup) {
		super(properties, sup);
	}

	@Override
	public Data create(ItemStack stack) {
		return new Data(stack);
	}

	public record Data(ItemStack stack) implements ICurio {

		@Override
		public ItemStack getStack() {
			return stack;
		}

		@Override
		public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan) {
			return true;
		}
	}

}

