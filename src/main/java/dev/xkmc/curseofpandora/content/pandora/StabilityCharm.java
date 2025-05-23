package dev.xkmc.curseofpandora.content.pandora;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class StabilityCharm extends DescCurioItem implements ICurioItem {

	public StabilityCharm(Properties properties) {
		super(properties);
	}

	@Override
	public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
		Multimap<Holder<Attribute>, AttributeModifier> ans = HashMultimap.create();
		ans.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(id, 1, AttributeModifier.Operation.ADD_VALUE));
		return ans;
	}

}
