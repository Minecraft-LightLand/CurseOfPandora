package dev.xkmc.curseofpandora.compat;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.curseofpandora.content.hostility.RealityTrait;
import dev.xkmc.curseofpandora.content.hostility.SealOfSword;
import dev.xkmc.curseofpandora.content.hostility.SpellSingularity;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2hostility.content.config.TraitConfig;
import dev.xkmc.l2hostility.init.entries.LHRegistrate;
import dev.xkmc.l2hostility.init.registrate.LHBlocks;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.ChatFormatting;

public class CoPTraits {

	public static final ItemEntry<SealOfSword> SEAL_OF_SWORDS;
	public static final ItemEntry<SpellSingularity> SPELL_SINGULARITY;
	public static final RegistryEntry<RealityTrait> TRAIT_REALITY;

	static {
		SEAL_OF_SWORDS = CoPItems.item("seal_of_swords", SealOfSword::new).register();
		SPELL_SINGULARITY = CoPItems.item("spell_singularity", SpellSingularity::new).register();

		CurseOfPandora.REGISTRATE.defaultCreativeTab(LHBlocks.TAB.getKey());
		TRAIT_REALITY = ((LHRegistrate) CurseOfPandora.REGISTRATE).regTrait("reality", () -> new RealityTrait(ChatFormatting.RED),
						rl -> new TraitConfig(rl, 1000, 1, 7, 50))
				.lang("Reality").desc("Immune to damage from entities will less reality index than rank of this trait.")
				.register();
	}

	public static void register() {

	}

	public static void onConfigGen(ConfigDataProvider.Collector collector) {
		((LHRegistrate) CurseOfPandora.REGISTRATE).CONFIGS.forEach(e -> e.accept(collector));
	}
}
