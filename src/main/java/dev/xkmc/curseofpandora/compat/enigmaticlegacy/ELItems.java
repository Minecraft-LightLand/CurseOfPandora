package dev.xkmc.curseofpandora.compat.enigmaticlegacy;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;

public class ELItems {

	public static final ItemEntry<AggrevateRelief> AGGREVATE;
	public static final ItemEntry<InsomniaRelief> INSOMNIA;

	static {
		AGGREVATE = CoPItems.item("illusory_affection", AggrevateRelief::new).register();
		INSOMNIA = CoPItems.item("fading_dream", InsomniaRelief::new).register();
	}

	public static void register() {

	}

}
