package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.ISubToken;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.Set;
import java.util.UUID;

public abstract class AttributeLimiter implements ISubToken {

	protected final String baseName;
	protected final UUID add, base, total;
	protected final Attribute attribute;

	protected AttributeLimiter(Attribute attribute, String baseName) {
		add = MathHelper.getUUIDFromString(baseName + "_add");
		base = MathHelper.getUUIDFromString(baseName + "_mult_base");
		total = MathHelper.getUUIDFromString(baseName + "_mult_total");
		this.baseName = baseName;
		this.attribute = attribute;
	}

	protected abstract CursePandoraUtil.ValueConsumer curseMult(double finVal, CursePandoraUtil.Mult valMult);

	protected final void doAttributeLimit(Player player, Set<UUID> set, boolean posOnly) {
		if (player.level().isClientSide) return;
		var attr = player.getAttribute(attribute);
		if (attr == null) return;
		CursePandoraUtil.Add valAdd = new CursePandoraUtil.Add();
		CursePandoraUtil.remove(attr, AttributeModifier.Operation.ADDITION,
				add, baseName + "_negate_add",
				set, new CursePandoraUtil.Add(), valAdd, posOnly);
		CursePandoraUtil.Add valBase = new CursePandoraUtil.Add();
		CursePandoraUtil.remove(attr, AttributeModifier.Operation.MULTIPLY_BASE,
				base, baseName + "_negate_base",
				Set.of(), new CursePandoraUtil.Add(), valBase, posOnly);
		double finVal = (attr.getBaseValue() + valAdd.get()) * (1 + valBase.get());
		CursePandoraUtil.Mult valMult = new CursePandoraUtil.Mult();
		CursePandoraUtil.remove(attr, AttributeModifier.Operation.MULTIPLY_TOTAL,
				total, baseName + "_negate_total",
				Set.of(), curseMult(finVal, valMult), valMult, posOnly);
	}

	public void removeImpl(Player player) {
		if (player.level().isClientSide) return;
		var attr = player.getAttribute(attribute);
		if (attr == null) return;
		attr.removeModifier(add);
		attr.removeModifier(base);
		attr.removeModifier(total);
	}

}
