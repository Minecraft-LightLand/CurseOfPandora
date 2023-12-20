package dev.xkmc.curseofpandora.content.pandora;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.l2complements.content.item.curios.CurioItem;
import dev.xkmc.l2complements.content.item.curios.ICapItem;
import dev.xkmc.pandora.init.data.PandoraLangData;
import dev.xkmc.pandora.init.data.PandoraTagGen;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;
import java.util.UUID;

public class AttributeItem extends CurioItem implements ICapItem<AttributeItem.Data> {

	private final AttributeEntry[] entries;

	public AttributeItem(Properties properties, AttributeEntry... entries) {
		super(properties);
		this.entries = entries;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		if (stack.is(PandoraTagGen.ALLOW_DUPLICATE))
			list.add(PandoraLangData.TOOLTIP_DUPLICATE.get().withStyle(ChatFormatting.GRAY));
	}


	@Override
	public Data create(ItemStack stack) {
		return new Data(this, stack);
	}

	public interface AttributeEntry {

		void modify(UUID uuid, Multimap<Attribute, AttributeModifier> ans);

	}

	public record Data(AttributeItem item, ItemStack stack) implements ICurio {

		@Override
		public ItemStack getStack() {
			return stack;
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
			Multimap<Attribute, AttributeModifier> ans = HashMultimap.create();
			for (var e : item.entries) {
				e.modify(uuid, ans);
			}
			return ans;
		}
	}

}
