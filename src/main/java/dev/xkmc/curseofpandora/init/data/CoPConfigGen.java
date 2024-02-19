package dev.xkmc.curseofpandora.init.data;

import dev.xkmc.curseofpandora.compat.CoPTraits;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2hostility.init.L2Hostility;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import dev.xkmc.l2tabs.init.L2Tabs;
import dev.xkmc.l2tabs.init.data.AttributeDisplayConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class CoPConfigGen extends ConfigDataProvider {

	public CoPConfigGen(DataGenerator generator) {
		super(generator, "CurseOfPandora Config");
	}

	@Override
	public void add(Collector collector) {
		if (ModList.get().isLoaded(L2Hostility.MODID)) {
			CoPTraits.onConfigGen(collector);
		}
		collector.add(L2Tabs.ATTRIBUTE_ENTRY, new ResourceLocation(CurseOfPandora.MODID, "pandora"),
				new AttributeDisplayConfig()
						.add(CoPAttrs.SPELL.get(), 20000)
						.add(CoPAttrs.REALITY.get(), 21000));
	}

}