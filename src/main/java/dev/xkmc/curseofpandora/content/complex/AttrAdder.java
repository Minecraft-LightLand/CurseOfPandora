package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;
import java.util.function.Supplier;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

public record AttrAdder(String name, Supplier<Attribute> attr, UUID uuid,
						AttributeModifier.Operation op, double value)
		implements ISubToken, IAttrAdder {

	public static AttrAdder of(String name, Supplier<Attribute> id, AttributeModifier.Operation op, double value) {
		return new AttrAdder(name + "_bonus", id, MathHelper.getUUIDFromString(name), op, value);
	}

	public void tickImpl(Player player) {
		if (player.level().isClientSide()) return;
		var ins = player.getAttribute(attr.get());
		if (ins == null) return;
		var mod = ins.getModifier(uuid);
		if (mod == null || mod.getOperation() != op || mod.getAmount() != value) {
			ins.removeModifier(uuid);
			ins.addTransientModifier(new AttributeModifier(
					uuid, name, value, op));
		}
	}

	public void removeImpl(Player player) {
		if (player.level().isClientSide()) return;
		var ins = player.getAttribute(attr.get());
		if (ins == null) return;
		ins.removeModifier(uuid);
	}

	public MutableComponent getTooltip() {
		return Component.translatable(
						"attribute.modifier.plus." + op.toValue(),
						ATTRIBUTE_MODIFIER_FORMAT.format(value),
						Component.translatable(attr.get().getDescriptionId()));
	}

}
