package dev.xkmc.curseofpandora.compat;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.curseofpandora.content.trait.RealityTrait;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.l2hostility.content.config.TraitConfig;
import dev.xkmc.l2hostility.init.entries.LHRegistrate;
import dev.xkmc.l2hostility.init.registrate.LHBlocks;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.ChatFormatting;

public class CoPTraits {

	public static final RegistryEntry<RealityTrait> TRAIT_REALITY;

	static {
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
