package dev.xkmc.curseofpandora.content.pandora;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.l2complements.content.item.curios.DescCurioItem;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

public class StabilityCharm extends DescCurioItem implements ICurioItem {

	public StabilityCharm(Properties properties) {
		super(properties);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
		Multimap<Attribute, AttributeModifier> ans = HashMultimap.create();
		ans.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "curseofpandora:orb_of_stability", 1,
				AttributeModifier.Operation.ADDITION));
		return ans;
	}

}
