package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2core.capability.conditionals.TokenKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Set;

public class CursePandoraUtil {

	public static AttrAdder spell(TokenKey<?> key) {
		return AttrAdder.of(key.id(), CoPAttrs.SPELL, AttributeModifier.Operation.ADD_VALUE, 1);
	}

	public static AttrAdder reality(TokenKey<?> key) {
		return AttrAdder.of(key.id(), CoPAttrs.REALITY, AttributeModifier.Operation.ADD_VALUE, 1);
	}

	public static void remove(AttributeInstance attr, AttributeModifier.Operation op, ResourceLocation negId,
							  Set<ResourceLocation> ignore, ValueConsumer negate, ValueConsumer val, boolean posOnly) {
		Set<AttributeModifier> list = attr.getModifiers();
		for (var e : list) {
			if (e.operation() != op) continue;
			if (e.id().equals(negId)) continue;
			val.accept(e.amount());
			if (ignore.contains(e.id())) continue;
			if (posOnly ^ e.amount() > 0) negate.accept(e.amount());
		}
		var old = attr.getModifier(negId);
		double mod = negate.reverse();
		if (old == null || old.amount() != mod) {
			attr.removeModifier(negId);
			attr.addPermanentModifier(new AttributeModifier(negId, mod, op));
		}
		val.accept(mod);
	}

	public interface ValueConsumer {

		void accept(double val);

		double get();

		double reverse();
	}

	public static class Add implements ValueConsumer {

		private double val;

		public Add() {
			val = 0;
		}

		@Override
		public void accept(double v) {
			val += v;
		}

		@Override
		public double get() {
			return val;
		}

		@Override
		public double reverse() {
			return -val;
		}

	}

	public static class Mult implements ValueConsumer {

		protected double val;

		public Mult() {
			val = 1;
		}

		@Override
		public void accept(double v) {
			val *= 1 + v;
		}

		@Override
		public double get() {
			return val;
		}

		@Override
		public double reverse() {
			return 1 / val - 1;
		}

	}

	public static class BonusMult extends Mult {

		private final double factor;

		public BonusMult(double factor) {
			this.factor = factor;
		}

		@Override
		public double reverse() {
			return factor / val - 1;
		}

	}

}
