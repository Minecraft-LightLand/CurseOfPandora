package dev.xkmc.curseofpandora.init.data;

import com.tterrag.registrate.providers.RegistrateDataMapProvider;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2tabs.init.L2Tabs;
import dev.xkmc.l2tabs.init.data.AttrDispEntry;

public class CoPDataMapGen {

	public static void genDataMap(RegistrateDataMapProvider pvd) {
		pvd.builder(L2Tabs.ATTRIBUTE_ENTRY.reg())
				.add(CoPAttrs.SPELL.val().getId(), new AttrDispEntry(false, 20000, 0), false)
				.add(CoPAttrs.REALITY.val().getId(), new AttrDispEntry(false, 21000, 0), false);
	}
}