package dev.xkmc.curseofpandora.content.pandora;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2complements.content.item.curios.CurioItem;
import dev.xkmc.l2complements.content.item.curios.ICapItem;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.pandora.init.data.PandoraLangData;
import dev.xkmc.pandora.init.data.PandoraTagGen;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class AttributeItem extends CurioItem implements ICapItem<AttributeItem.Data> {

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
	public Data create(ItemStack stack) {
		return new Data(this, stack);
	}

	public record AttributeEntry(Supplier<Attribute> attr, String name, DoubleSupplier val,
								 AttributeModifier.Operation op) {

		public void modify(UUID uuid, Multimap<Attribute, AttributeModifier> ans) {
			ans.put(attr.get(), new AttributeModifier(uuid, name, val.getAsDouble(), op));
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
			Map<String, Integer> map = new HashMap<>();
			for (int i = 0; i < tooltips.size(); i++) {
				var txt = tooltips.get(i);
				if (txt.getContents() instanceof TranslatableContents tr) {
					var args = tr.getArgs();
					if (args.length == 2  && args[1] instanceof MutableComponent comp) {
						if (comp.getContents() instanceof TranslatableContents sub) {
							map.put(sub.getKey(), i);
						}
					}
				}
			}
			for (int i = 0; i < item.entries.length; i++) {
				var ent = item.entries[i];
				double val = ent.val.getAsDouble();
				MutableComponent rep = null;
				if (AttrAdder.isMult(ent.attr.get())) {
					rep = AttrAdder.getDesc(ent.attr.get(), val, ent.op());
				}
				if (ent.attr.get() == L2DamageTracker.REDUCTION.get()) {
					rep = AttrAdder.getDesc(ent.attr.get(), val, ent.op());
				}
				if (rep != null) {
					Integer index = map.get(ent.attr.get().getDescriptionId());
					if (index != null) {
						tooltips.set(index, rep.withStyle(ChatFormatting.BLUE));
					}
				}
			}
			return tooltips;
		}

	}

}
