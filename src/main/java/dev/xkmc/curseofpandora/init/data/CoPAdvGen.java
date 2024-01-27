package dev.xkmc.curseofpandora.init.data;

import com.tterrag.registrate.providers.RegistrateAdvancementProvider;
import dev.xkmc.curseofpandora.content.reality.CursePandoraUtil;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.l2library.serial.advancements.AdvancementGenerator;
import dev.xkmc.l2library.serial.advancements.CriterionBuilder;
import dev.xkmc.l2library.serial.advancements.RewardBuilder;
import dev.xkmc.l2library.util.data.LootTableTemplate;
import dev.xkmc.pandora.init.registrate.PandoraItems;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;

public class CoPAdvGen {

	public static void onAdvGen(RegistrateAdvancementProvider pvd) {
		var gen = new AdvancementGenerator(pvd, CurseOfPandora.MODID);
		var builder = gen.new TabBuilder("pandora");
		var item = PandoraItems.PANDORA_NECKLACE.get();
		var tag = CursePandoraUtil.allCurses(item).getOrCreateTag();
		builder.hidden("pandora_box", CriterionBuilder.one(PlayerTrigger.TriggerInstance.tick()))
				.add(new RewardBuilder(CurseOfPandora.REGISTRATE, 0,
						new ResourceLocation(CurseOfPandora.MODID, "pandora_initial"),
						() -> LootTable.lootTable().withPool(LootPool.lootPool()
								.add(LootTableTemplate.getItem(item, 1)
										.apply(SetNbtFunction.setTag(tag))))
				)).build();
	}

}
