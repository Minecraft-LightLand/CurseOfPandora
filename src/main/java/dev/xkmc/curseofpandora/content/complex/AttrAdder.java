package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.curseofpandora.init.CurseOfPandora;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;

import java.util.function.DoubleSupplier;

public record AttrAdder(ResourceLocation name, Holder<Attribute> attr,
						AttributeModifier.Operation op, DoubleSupplier value)
		implements ISubToken, IAttrAdder {

	public static AttrAdder of(String name, Holder<Attribute> id, AttributeModifier.Operation op, double value) {
		return new AttrAdder(CurseOfPandora.loc(name + "_bonus"), id, op, () -> value);
	}

	public static AttrAdder of(String name, Holder<Attribute> id, AttributeModifier.Operation op, DoubleSupplier value) {
		return new AttrAdder(CurseOfPandora.loc(name + "_bonus"), id, op, value);
	}

	public void tickImpl(Player player) {
		addAttr(player);
	}

	public void addAttr(LivingEntity player) {
		if (player.level().isClientSide()) return;
		double val = value.getAsDouble();
		var ins = player.getAttribute(attr);
		if (ins == null) return;
		var mod = ins.getModifier(name);
		if (mod == null || mod.operation() != op || mod.amount() != val) {
			ins.removeModifier(name);
			ins.addTransientModifier(new AttributeModifier(name, val, op));
		}
	}

	public void removeImpl(Player player) {
		if (player.level().isClientSide()) return;
		var ins = player.getAttribute(attr);
		if (ins == null) return;
		ins.removeModifier(name);
	}

	public MutableComponent getTooltip() {
		return attr.value().toComponent(new AttributeModifier(name, value.getAsDouble(), op), TooltipFlag.NORMAL);
	}

}
