package dev.xkmc.curseofpandora.compat;

import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2hostility.init.entries.LHRegistrate;

public class CoPLoader {

	public static L2Registrate getLHRegistrate(String modid) {
		return new LHRegistrate(modid);
	}

}
