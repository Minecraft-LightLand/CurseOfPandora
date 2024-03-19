package dev.xkmc.curseofpandora.content.pandora;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.l2complements.content.item.curios.CurioItem;
import dev.xkmc.l2damagetracker.contents.curios.AttrTooltip;
import dev.xkmc.pandora.init.data.PandoraLangData;
import dev.xkmc.pandora.init.data.PandoraTagGen;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class AttributeItem extends CurioItem implements ICurioItem {

	public static AttributeEntry add(Supplier<Attribute> attr, String name, DoubleSupplier val) {
		return new AttributeEntry(attr, name, val, AttributeModifier.Operation.ADDITION);
	}

	public static AttributeEntry multBase(Supplier<Attribute> attr, String name, DoubleSupplier val) {
		return new AttributeEntry(attr, name, val, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	public static AttributeEntry multTotal(Supplier<Attribute> attr, String name, DoubleSupplier val) {
		return new AttributeEntry(attr, name, val, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

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
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
		return getAttributeModifiers(uuid);
	}

	private Multimap<Attribute, AttributeModifier> getAttributeModifiers(UUID uuid) {
		Multimap<Attribute, AttributeModifier> ans = LinkedHashMultimap.create();
		for (var e : entries) {
			e.modify(uuid, ans);
		}
		return ans;
	}

	@Override
	public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
		return AttrTooltip.modifyTooltip(tooltips, getAttributeModifiers(Util.NIL_UUID), false);
	}

	public record AttributeEntry(Supplier<Attribute> attr, String name, DoubleSupplier val,
								 AttributeModifier.Operation op) {

		public void modify(UUID uuid, Multimap<Attribute, AttributeModifier> ans) {
			ans.put(attr.get(), new AttributeModifier(uuid, name, val.getAsDouble(), op));
		}

	}

}
