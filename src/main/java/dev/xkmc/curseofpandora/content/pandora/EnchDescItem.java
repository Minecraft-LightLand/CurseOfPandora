package dev.xkmc.curseofpandora.content.pandora;

import dev.xkmc.l2core.init.reg.ench.EnchVal;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class EnchDescItem extends Item implements ICurioItem {

	private final EnchVal sup;

	public EnchDescItem(Properties properties, EnchVal sup) {
		super(properties);
		this.sup = sup;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		var rl = sup.id().location();
		list.add(Component.translatable("enchantment." + rl.getNamespace() + "." + rl.getPath() + ".desc")
				.withStyle(ChatFormatting.GRAY));
	}

}
