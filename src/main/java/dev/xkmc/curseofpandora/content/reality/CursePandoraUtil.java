package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.pandora.content.base.IPandoraHolder;
import dev.xkmc.pandora.content.base.PandoraHolder;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CursePandoraUtil {

	public static ItemStack allCurses(PandoraHolder holder) {
		ItemStack stack = holder.getDefaultInstance();
		List<ItemStack> list = new ArrayList<>();
		list.add(CoPItems.CURSE_OF_INERTIA.asStack());
		list.add(CoPItems.CURSE_OF_PROXIMITY.asStack());
		list.add(CoPItems.CURSE_OF_FLESH.asStack());
		list.add(CoPItems.CURSE_OF_METABOLISM.asStack());
		list.add(CoPItems.CURSE_OF_TENSION.asStack());
		list.add(CoPItems.CURSE_OF_PRUDENCE.asStack());
		list.add(CoPItems.CURSE_OF_SPELL.asStack());
		int n = holder.getSlots(stack);
		for (int i = 7; i < n; i++) {
			list.add(ItemStack.EMPTY);
		}
		IPandoraHolder.setItems(stack, list);
		return stack;
	}

	public static AttrAdder spell(TokenKey<?> key) {
		return AttrAdder.of(key.id(), CoPAttrs.SPELL, AttributeModifier.Operation.ADDITION, 1);
	}

	public static AttrAdder reality(TokenKey<?> key) {
		return AttrAdder.of(key.id(), CoPAttrs.REALITY, AttributeModifier.Operation.ADDITION, 1);
	}

	public static void remove(AttributeInstance attr, AttributeModifier.Operation op, UUID negId, String negName,
							  Set<UUID> ignore, ValueConsumer negate, ValueConsumer val, boolean posOnly) {
		Set<AttributeModifier> list = attr.getModifiers(op);
		for (var e : list) {
			if (e.getId().equals(negId)) continue;
			val.accept(e.getAmount());
			if (ignore.contains(e.getId())) continue;
			if (posOnly ^ e.getAmount() > 0) negate.accept(e.getAmount());
		}
		var old = attr.getModifier(negId);
		double mod = negate.reverse();
		if (old == null || old.getAmount() != mod) {
			attr.removeModifier(negId);
			attr.addTransientModifier(new AttributeModifier(negId, negName, mod, op));
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
