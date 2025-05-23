package dev.xkmc.curseofpandora.init.registrate;

import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class CoPAttrs {

	public static final SimpleEntry<Attribute> SPELL = reg("spell_tolerance", 1, 10000, "Spell Tolerance");
	public static final SimpleEntry<Attribute> REALITY = reg("reality_index", 0, 10000, "Reality Index");

	public static void register() {

	}

	private static SimpleEntry<Attribute> reg(String id, double def, double max, String name) {
		CurseOfPandora.REGISTRATE.addRawLang("attribute.name." + id, name);
		return new SimpleEntry<>(CurseOfPandora.REGISTRATE.simple(id, Registries.ATTRIBUTE,
				() -> new RangedAttribute("attribute.name." + id, def, 0, max)
						.setSyncable(true)));
	}
}
