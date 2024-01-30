package dev.xkmc.curseofpandora.compat;

import dev.xkmc.l2hostility.init.entries.LHRegistrate;
import dev.xkmc.l2library.base.L2Registrate;

public class CoPLoader {

	public static L2Registrate getLHRegistrate(String modid) {
		return new LHRegistrate(modid);
	}

}
