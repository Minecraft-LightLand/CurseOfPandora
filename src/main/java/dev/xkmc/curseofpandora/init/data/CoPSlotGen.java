package dev.xkmc.curseofpandora.init.data;

import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.l2library.compat.curios.CurioEntityBuilder;
import dev.xkmc.l2library.compat.curios.CurioSlotBuilder;
import dev.xkmc.l2library.compat.curios.SlotCondition;
import dev.xkmc.l2library.serial.config.RecordDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class CoPSlotGen extends RecordDataProvider {

	public CoPSlotGen(DataGenerator generator) {
		super(generator, "Curios Generator");
	}

	public void add(BiConsumer<String, Record> map) {
		map.accept(CurseOfPandora.MODID + "/curios/slots/curio",
				new CurioSlotBuilder(20, "curios:slot/empty_curio_slot", 0,
						CurioSlotBuilder.Operation.SET));
		map.accept(CurseOfPandora.MODID + "/curios/entities/pandora_entity",
				new CurioEntityBuilder(new ArrayList<>(List.of(new ResourceLocation("player"))),
						new ArrayList<>(List.of("curio", "hands")), SlotCondition.of()));
	}
}
