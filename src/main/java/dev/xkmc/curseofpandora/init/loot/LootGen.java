package dev.xkmc.curseofpandora.init.loot;

import com.tterrag.registrate.providers.loot.RegistrateLootTableProvider;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.l2library.util.data.LootTableTemplate;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Locale;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class LootGen {

	private static LootTable.Builder buildEndCityExtraLoot() {//TODO
		return LootTable.lootTable().withPool(LootTableTemplate.getPool(1, 0)
						.add(LootTableTemplate.getItem(Items.ELYTRA, 1)))
				.withPool(LootTableTemplate.getPool(2, 1)
						.add(LootTableTemplate.getItem(Items.ENCHANTED_GOLDEN_APPLE, 2, 4))
						.add(LootTableTemplate.getItem(Items.NETHERITE_INGOT, 2, 4))
						.add(LootTableTemplate.getItem(Items.NETHER_STAR, 1)))
				.withPool(LootTableTemplate.getPool(5, 2)
						.add(LootTableTemplate.getItem(Items.GLOWSTONE_DUST, 16, 32))
						.add(LootTableTemplate.getItem(Items.REDSTONE, 16, 32))
						.add(LootTableTemplate.getItem(Items.LAPIS_LAZULI, 16, 32))
						.add(LootTableTemplate.getItem(Items.AMETHYST_SHARD, 16, 32))
						.add(LootTableTemplate.getItem(Items.QUARTZ, 16, 32))
						.add(LootTableTemplate.getItem(Items.EMERALD, 16, 32)));
	}

	private static LootTable.Builder buildPlaceholderLoot() {//TODO
		return LootTable.lootTable().withPool(LootTableTemplate.getPool(1, 0)
				.add(LootTableTemplate.getItem(Items.COAL, 1, 16)));
	}

	public enum LootDefinition {
		END_CITY_TREASURE(0.2, 0.1, BuiltInLootTables.END_CITY_TREASURE, LootGen::buildEndCityExtraLoot),
		BASTION_TREASURE(0.2, 0.1, BuiltInLootTables.BASTION_TREASURE, LootGen::buildPlaceholderLoot),
		DESERT_PYRAMID(0.2, 0.1, BuiltInLootTables.DESERT_PYRAMID, LootGen::buildPlaceholderLoot),
		ANCIENT_CITY(0.2, 0.1, BuiltInLootTables.ANCIENT_CITY, LootGen::buildPlaceholderLoot),
		SHIPWRECK_TREASURE(0.2, 0.1, BuiltInLootTables.SHIPWRECK_TREASURE, LootGen::buildPlaceholderLoot),
		UNDERWATER_RUIN_BIG(0.2, 0.1, BuiltInLootTables.UNDERWATER_RUIN_BIG, LootGen::buildPlaceholderLoot),
		VILLAGE_CARTOGRAPHER(0.2, 0.1, BuiltInLootTables.VILLAGE_CARTOGRAPHER, LootGen::buildPlaceholderLoot),
		IGLOO_CHEST(0.2, 0.1, BuiltInLootTables.IGLOO_CHEST, LootGen::buildPlaceholderLoot),
		STRONGHOLD_CORRIDOR(0.2, 0.1, BuiltInLootTables.STRONGHOLD_CORRIDOR, LootGen::buildPlaceholderLoot),
		WOODLAND_MANSION(0.2, 0.1, BuiltInLootTables.WOODLAND_MANSION, LootGen::buildPlaceholderLoot),
		NETHER_BRIDGE(0.2, 0.1, BuiltInLootTables.NETHER_BRIDGE, LootGen::buildPlaceholderLoot),
		PILLAGER_OUTPOST(0.2, 0.1, BuiltInLootTables.PILLAGER_OUTPOST, LootGen::buildPlaceholderLoot),
		RUINED_PORTAL(0.2, 0.1, BuiltInLootTables.RUINED_PORTAL, LootGen::buildPlaceholderLoot),
		ABANDONED_MINESHAFT(0.2, 0.1, BuiltInLootTables.ABANDONED_MINESHAFT, LootGen::buildPlaceholderLoot),
		JUNGLE_TEMPLE(0.2, 0.1, BuiltInLootTables.JUNGLE_TEMPLE, LootGen::buildPlaceholderLoot),
		SIMPLE_DUNGEON(0.2, 0.1, BuiltInLootTables.SIMPLE_DUNGEON, LootGen::buildPlaceholderLoot),
		;

		public final String id;
		public final double chance, bonus;
		public final ResourceLocation table;
		public final Supplier<LootTable.Builder> loot;


		LootDefinition(double chance, double bonus, ResourceLocation table, Supplier<LootTable.Builder> loot) {
			this.chance = chance;
			this.bonus = bonus;
			this.table = table;
			this.id = name().toLowerCase(Locale.ROOT);
			this.loot = loot;
		}

		public ResourceLocation getInner() {
			return new ResourceLocation(CurseOfPandora.MODID, id);
		}

	}

	private static void genInnerLoot(BiConsumer<ResourceLocation, LootTable.Builder> map) {
		for (LootDefinition def : LootDefinition.values()) {
			map.accept(def.getInner(), def.loot.get());
		}
	}

	public static void genLoot(RegistrateLootTableProvider pvd) {
		pvd.addLootAction(LootContextParamSets.EMPTY, LootGen::genInnerLoot);
	}

}
