package dev.xkmc.curseofpandora.content.pandora;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
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
import java.util.function.Supplier;

public class AttributeItem extends CurioItem implements ICapItem<AttributeItem.Data> {

	public static AttributeEntry add(Supplier<Attribute> attr, String name, double val) {
		return new AttributeEntry(attr, name, val, AttributeModifier.Operation.ADDITION);
	}

	public static AttributeEntry multBase(Supplier<Attribute> attr, String name, double val) {
		return new AttributeEntry(attr, name, val, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	public static AttributeEntry multTotal(Supplier<Attribute> attr, String name, double val) {
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
	public Data create(ItemStack stack) {
		return new Data(this, stack);
	}

	public record AttributeEntry(Supplier<Attribute> attr, String name, double val,
								 AttributeModifier.Operation op) {

		public void modify(UUID uuid, Multimap<Attribute, AttributeModifier> ans) {
			ans.put(attr.get(), new AttributeModifier(uuid, name, val, op));
		}

	}

	public record Data(AttributeItem item, ItemStack stack) implements ICurio {

		@Override
		public ItemStack getStack() {
			return stack;
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
			Multimap<Attribute, AttributeModifier> ans = LinkedHashMultimap.create();
			for (var e : item.entries) {
				e.modify(uuid, ans);
			}
			return ans;
		}

		@Override
		public List<Component> getAttributesTooltip(List<Component> tooltips) {
			for (int i = 0; i < item.entries.length; i++) {
				var ent = item.entries[i];
				if (AttrAdder.isMult(ent.attr.get())) {
					tooltips.set(i + 2, AttrAdder.getDesc(ent.attr.get(), ent.val(), ent.op())
							.withStyle(ChatFormatting.BLUE));
				}
				if (ent.attr.get() == CoPMisc.REDUCTION.get()) {
					tooltips.set(i + 2, AttrAdder.getDesc(ent.attr.get(), ent.val(), ent.op())
							.withStyle(ChatFormatting.BLUE));
				}
			}
			return tooltips;
		}

	}

}
