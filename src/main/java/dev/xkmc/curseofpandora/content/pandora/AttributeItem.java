package dev.xkmc.curseofpandora.content.pandora;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.pandora.init.data.PandoraLangData;
import dev.xkmc.pandora.init.data.PandoraTagGen;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.function.DoubleSupplier;

public class AttributeItem extends Item implements ICurioItem {

	public static AttributeEntry add(Holder<Attribute> attr, String name, DoubleSupplier val) {
		return new AttributeEntry(attr, name, val, AttributeModifier.Operation.ADD_VALUE);
	}

	public static AttributeEntry multBase(Holder<Attribute> attr, String name, DoubleSupplier val) {
		return new AttributeEntry(attr, name, val, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
	}

	public static AttributeEntry multTotal(Holder<Attribute> attr, String name, DoubleSupplier val) {
		return new AttributeEntry(attr, name, val, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}

	private final AttributeEntry[] entries;

	public AttributeItem(Properties properties, AttributeEntry... entries) {
		super(properties);
		this.entries = entries;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		if (stack.is(PandoraTagGen.ALLOW_DUPLICATE))
			list.add(PandoraLangData.TOOLTIP_DUPLICATE.get().withStyle(ChatFormatting.GRAY));
	}

	@Override
	public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation uuid, ItemStack stack) {
		return getAttributeModifiers(uuid);
	}

	private Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(ResourceLocation uuid) {
		Multimap<Holder<Attribute>, AttributeModifier> ans = LinkedHashMultimap.create();
		for (var e : entries) {
			e.modify(uuid, ans);
		}
		return ans;
	}

	public record AttributeEntry(Holder<Attribute> attr, String name, DoubleSupplier val,
								 AttributeModifier.Operation op) {

		public void modify(ResourceLocation uuid, Multimap<Holder<Attribute>, AttributeModifier> ans) {
			ans.put(attr, new AttributeModifier(uuid.withSuffix("_" + name), val.getAsDouble(), op));
		}

	}

}
