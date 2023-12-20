package dev.xkmc.curseofpandora.init.data;

import com.tterrag.registrate.providers.RegistrateAdvancementProvider;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2library.serial.advancements.AdvancementGenerator;
import dev.xkmc.l2library.serial.advancements.CriterionBuilder;
import dev.xkmc.l2library.serial.advancements.RewardBuilder;
import dev.xkmc.l2library.util.data.LootTableTemplate;
import dev.xkmc.pandora.init.registrate.PandoraItems;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

public class CoPAdvGen {

	public static void onAdvGen(RegistrateAdvancementProvider pvd) {
		var gen = new AdvancementGenerator(pvd, CurseOfPandora.MODID);
		var builder = gen.new TabBuilder("pandora");
		builder.hidden("pandora_box", CriterionBuilder.one(PlayerTrigger.TriggerInstance.tick()))
				.add(new RewardBuilder(CurseOfPandora.REGISTRATE, 0,
						new ResourceLocation(CurseOfPandora.MODID, "pandora_initial"),
						() -> LootTable.lootTable()
								.withPool(LootPool.lootPool().add(LootTableTemplate.getItem(PandoraItems.PANDORA_NECKLACE.asItem(), 1)))
								.withPool(LootPool.lootPool().add(LootTableTemplate.getItem(CoPItems.CURSE_OF_INERTIA.asItem(), 1)))
								.withPool(LootPool.lootPool().add(LootTableTemplate.getItem(CoPItems.CURSE_OF_PROXIMITY.asItem(), 1)))
								.withPool(LootPool.lootPool().add(LootTableTemplate.getItem(CoPItems.CURSE_OF_FLESH.asItem(), 1)))
								.withPool(LootPool.lootPool().add(LootTableTemplate.getItem(CoPItems.CURSE_OF_METABOLISM.asItem(), 1)))
								.withPool(LootPool.lootPool().add(LootTableTemplate.getItem(CoPItems.CURSE_OF_TENSION.asItem(), 1)))
								.withPool(LootPool.lootPool().add(LootTableTemplate.getItem(CoPItems.CURSE_OF_PRUDENCE.asItem(), 1)))
								.withPool(LootPool.lootPool().add(LootTableTemplate.getItem(CoPItems.CURSE_OF_SPELL.asItem(), 1)))
				)).build();
	}

}
