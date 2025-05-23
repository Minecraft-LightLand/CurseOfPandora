package dev.xkmc.curseofpandora.init.data;

import com.tterrag.registrate.providers.RegistrateAdvancementProvider;
import dev.xkmc.curseofpandora.content.reality.CursePandoraUtil;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2core.serial.advancements.AdvancementGenerator;
import dev.xkmc.l2core.serial.advancements.CriterionBuilder;
import dev.xkmc.l2core.serial.advancements.RewardBuilder;
import dev.xkmc.l2core.serial.loot.LootHelper;
import dev.xkmc.l2core.serial.loot.LootTableTemplate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;

import java.util.ArrayList;
import java.util.List;

public class CoPAdvGen {

	public static void onAdvGen(RegistrateAdvancementProvider pvd) {
		var gen = new AdvancementGenerator(pvd, CurseOfPandora.MODID);
		var builder = gen.new TabBuilder("pandora");
		var item = PandoraItems.PANDORA_NECKLACE.get();
		var tag = CursePandoraUtil.allCurses(item).getOrCreateTag();
		builder.hidden("pandora_box", CriterionBuilder.one(PlayerTrigger.TriggerInstance.tick()))
				.add(new RewardBuilder(CurseOfPandora.REGISTRATE, 0,
						CurseOfPandora.loc("pandora_initial"),
						() -> LootTable.lootTable().withPool(LootPool.lootPool()
								.add(LootItem.lootTableItem(item)
										.apply(SetComponentsFunction.setComponent(tag))))
				)).build();
	}

	public static ItemStack allCurses(PandoraHolder holder) {
		ItemStack stack = holder.getDefaultInstance();
		List<ItemStack> list = new ArrayList<>();
		list.add(CoPItems.CURSE_OF_INERTIA.asStack());
		list.add(CoPItems.CURSE_OF_PROXIMITY.asStack());
		list.add(CoPItems.CURSE_OF_FLESH.asStack());
		list.add(CoPItems.CURSE_OF_METABOLISM.asStack());
		list.add(CoPItems.CURSE_OF_TENSION.asStack());
		list.add(CoPItems.CURSE_OF_PRUDENCE.asStack());
		list.add(CoPItems.CURSE_OF_SPELL.asStack());
		int n = holder.getSlots(stack);
		for (int i = 7; i < n; i++) {
			list.add(ItemStack.EMPTY);
		}
		IPandoraHolder.setItems(stack, list);
		return stack;
	}


}
