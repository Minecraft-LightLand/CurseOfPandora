package dev.xkmc.curseofpandora.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.ForgeRegistries;

public class CoPAttrs {

	public static final RegistryEntry<Attribute> SPELL = reg("spell_tolerance", 1, 10000, "Spell Tolerance");
	public static final RegistryEntry<Attribute> REALITY = reg("reality_index", 0, 10000, "Reality Index");

	public static void register() {

	}

	private static RegistryEntry<Attribute> reg(String id, double def, double max, String name) {
		CurseOfPandora.REGISTRATE.addRawLang("attribute.name." + id, name);
		return CurseOfPandora.REGISTRATE.simple(id, ForgeRegistries.ATTRIBUTES.getRegistryKey(),
				() -> new RangedAttribute("attribute.name." + id, def, 0, max)
						.setSyncable(true));
	}
}
