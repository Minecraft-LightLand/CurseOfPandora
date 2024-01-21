package dev.xkmc.curseofpandora.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.curseofpandora.content.trait.RealityTrait;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.l2hostility.content.config.TraitConfig;
import dev.xkmc.l2hostility.init.registrate.LHBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.ForgeRegistries;

public class CoPMisc {

	public static final RegistryEntry<RealityTrait> TRAIT_REALITY;
	public static final RegistryEntry<Attribute> SPELL = reg("spell_tolerance", 1, 10000, "Spell Tolerance");
	public static final RegistryEntry<Attribute> REALITY = reg("reality_index", 0, 10000, "Reality Index");

	public static final RegistryEntry<Attribute> ABSORB = reg("damage_absorption", 0, 10000, "Damage Absorption");

	static {
		CurseOfPandora.REGISTRATE.defaultCreativeTab(LHBlocks.TAB.getKey());
		TRAIT_REALITY = CurseOfPandora.REGISTRATE.regTrait("reality", () -> new RealityTrait(ChatFormatting.RED),
						rl -> new TraitConfig(rl, 1000, 1, 7, 50))
				.lang("Reality").desc("Immune to damage from entities will less reality index than rank of this trait.")
				.register();
	}

	public static void register() {

	}

	private static RegistryEntry<Attribute> reg(String id, double def, double max, String name) {
		CurseOfPandora.REGISTRATE.addRawLang("attribute.name." + id, name);
		return CurseOfPandora.REGISTRATE.simple(id, ForgeRegistries.ATTRIBUTES.getRegistryKey(),
				() -> new RangedAttribute("attribute.name." + id, def, 0, max)
						.setSyncable(true));
	}
}
