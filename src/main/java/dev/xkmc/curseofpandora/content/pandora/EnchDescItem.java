package dev.xkmc.curseofpandora.content.pandora;

import dev.xkmc.l2complements.content.item.curios.CurioItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class EnchDescItem extends CurioItem {

	private final Supplier<Enchantment> sup;

	public EnchDescItem(Properties properties, Supplier<Enchantment> sup) {
		super(properties);
		this.sup = sup;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(Component.translatable(sup.get().getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
	}

}
