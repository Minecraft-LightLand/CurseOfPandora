package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.ISubToken;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.Set;

public abstract class AttributeLimiter implements ISubToken {

	protected final String baseName;
	protected final ResourceLocation add, base, total;
	protected final Holder<Attribute> attribute;

	protected AttributeLimiter(Holder<Attribute> attribute, String baseName) {
		add = CurseOfPandora.loc(baseName + "_add");
		base = CurseOfPandora.loc(baseName + "_mult_base");
		total = CurseOfPandora.loc(baseName + "_mult_total");
		this.baseName = baseName;
		this.attribute = attribute;
	}

	protected abstract CursePandoraUtil.ValueConsumer curseMult(double finVal, CursePandoraUtil.Mult valMult);

	protected final void doAttributeLimit(Player player, Set<ResourceLocation> set, boolean posOnly) {
		if (player.level().isClientSide) return;
		var attr = player.getAttribute(attribute);
		if (attr == null) return;
		CursePandoraUtil.Add valAdd = new CursePandoraUtil.Add();
		CursePandoraUtil.remove(attr, AttributeModifier.Operation.ADD_VALUE,
				add, set, new CursePandoraUtil.Add(), valAdd, posOnly);
		CursePandoraUtil.Add valBase = new CursePandoraUtil.Add();
		CursePandoraUtil.remove(attr, AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
				base, Set.of(), new CursePandoraUtil.Add(), valBase, posOnly);
		double finVal = (attr.getBaseValue() + valAdd.get()) * (1 + valBase.get());
		CursePandoraUtil.Mult valMult = new CursePandoraUtil.Mult();
		CursePandoraUtil.remove(attr, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
				total, Set.of(), curseMult(finVal, valMult), valMult, posOnly);
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
