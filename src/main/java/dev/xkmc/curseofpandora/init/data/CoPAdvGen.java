package dev.xkmc.curseofpandora.init.data;

import com.tterrag.registrate.providers.RegistrateAdvancementProvider;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2core.serial.advancements.AdvancementGenerator;
import dev.xkmc.l2core.serial.advancements.CriterionBuilder;
import dev.xkmc.l2core.serial.advancements.RewardBuilder;
import dev.xkmc.pandora.init.registrate.PandoraItems;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;

import java.util.ArrayList;
import java.util.List;

public class CoPAdvGen {

	public static RewardBuilder INITIAL = new RewardBuilder(CurseOfPandora.REGISTRATE, 0,
			ResourceKey.create(Registries.LOOT_TABLE, CurseOfPandora.loc("pandora_initial")),
			() -> LootTable.lootTable().withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(PandoraItems.PANDORA_NECKLACE.get())
							.apply(SetComponentsFunction.setComponent(
									PandoraItems.DC_ITEMS.get(), allCurses()
							))))
	);

	public static void init() {

	}

	public static void onAdvGen(RegistrateAdvancementProvider pvd) {
		var gen = new AdvancementGenerator(pvd, CurseOfPandora.MODID);
		var builder = gen.new TabBuilder("pandora");

		builder.hidden("pandora_box", CriterionBuilder.one(PlayerTrigger.TriggerInstance.tick()))
				.add(INITIAL).build();
	}

	public static ItemContainerContents allCurses() {
		List<ItemStack> list = new ArrayList<>();
		list.add(CoPItems.CURSE_OF_INERTIA.asStack());
		list.add(CoPItems.CURSE_OF_PROXIMITY.asStack());
		list.add(CoPItems.CURSE_OF_FLESH.asStack());
		list.add(CoPItems.CURSE_OF_METABOLISM.asStack());
		list.add(CoPItems.CURSE_OF_TENSION.asStack());
		list.add(CoPItems.CURSE_OF_PRUDENCE.asStack());
		list.add(CoPItems.CURSE_OF_SPELL.asStack());
		return ItemContainerContents.fromItems(list);
	}

}
