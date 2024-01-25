package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

public record AttrAdder(String name, Supplier<Attribute> attr, UUID uuid,
						AttributeModifier.Operation op, DoubleSupplier value)
		implements ISubToken, IAttrAdder {

	public static AttrAdder of(String name, Supplier<Attribute> id, AttributeModifier.Operation op, double value) {
		return new AttrAdder(name + "_bonus", id, MathHelper.getUUIDFromString(name), op, () -> value);
	}

	public static AttrAdder of(String name, Supplier<Attribute> id, AttributeModifier.Operation op, DoubleSupplier value) {
		return new AttrAdder(name + "_bonus", id, MathHelper.getUUIDFromString(name), op, value);
	}

	public void tickImpl(Player player) {
		if (player.level().isClientSide()) return;
		double val = value.getAsDouble();
		var ins = player.getAttribute(attr.get());
		if (ins == null) return;
		var mod = ins.getModifier(uuid);
		if (mod == null || mod.getOperation() != op || mod.getAmount() != val) {
			ins.removeModifier(uuid);
			ins.addTransientModifier(new AttributeModifier(uuid, name, val, op));
		}
	}

	public void removeImpl(Player player) {
		if (player.level().isClientSide()) return;
		var ins = player.getAttribute(attr.get());
		if (ins == null) return;
		ins.removeModifier(uuid);
	}

	public MutableComponent getTooltip() {
		return getDesc(attr.get(), value.getAsDouble(), op());
	}

	public static MutableComponent getDesc(Attribute attr, double val, AttributeModifier.Operation op) {
		var text = Component.translatable(attr.getDescriptionId());
		MutableComponent base;
		if (isMult(attr)) {
			if (op == AttributeModifier.Operation.ADDITION) {
				base = Component.literal(val < 0 ? "-" : "+");
				base.append(ATTRIBUTE_MODIFIER_FORMAT.format(Math.abs(val * 100)));
				base.append("%");

			} else {
				base = Component.literal("x");
				base.append(ATTRIBUTE_MODIFIER_FORMAT.format(val + 1));
			}
		} else {
			base = Component.literal(val < 0 ? "-" : "+");
			if (op == AttributeModifier.Operation.ADDITION) {
				base.append(ATTRIBUTE_MODIFIER_FORMAT.format(Math.abs(val)));
			} else {
				base.append(ATTRIBUTE_MODIFIER_FORMAT.format(Math.abs(val * 100)));
				base.append("%");
			}
		}
		base.append(" ");
		base.append(text);
		return base;
	}

	public static boolean isMult(Attribute attr) {
		var rl = ForgeRegistries.ATTRIBUTES.getKey(attr);
		assert rl != null;
		return rl.getNamespace().equals(L2DamageTracker.MODID);
	}

}
