package dev.xkmc.curseofpandora.init.loot;

import com.tterrag.registrate.providers.loot.RegistrateLootTableProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static dev.xkmc.curseofpandora.init.registrate.CoPItems.*;

public class LootGen {

	private record LootEntry(int weight, Item... items) {

	}

	private static class PoolBuilder {

		private final LootPool.Builder pool = LootPool.lootPool()
				.setRolls(ConstantValue.exactly(1))
				.setBonusRolls(ConstantValue.exactly(0));

		private PoolBuilder addItems(LootEntry entry) {
			for (var e : entry.items) {
				pool.add(getItem(e, entry.weight));
			}
			return this;
		}

		private PoolBuilder addItem(int weight, Item item) {
			pool.add(getItem(item, weight));
			return this;
		}

		private PoolBuilder fromTag(int weight, ItemEntry<?>[] tag) {
			return addItems(new LootEntry(weight, Arrays.stream(tag).map(RegistryEntry::get).toArray(Item[]::new)));
		}

		private LootTable.Builder build() {
			return LootTable.lootTable().withPool(pool);
		}

	}

	private static LootPoolSingletonContainer.Builder<?> getItem(Item item, int weight) {
		return LootItem.lootTableItem(item).setWeight(weight);
	}

	private static ItemEntry<?>[] attr() {
		return new ItemEntry<?>[]{
				CHARM_HEALTH, CHARM_ARMOR, CHARM_SPEED,
				CHARM_DAMAGE, CHARM_HEAVY, CHARM_ACCURACY, CHARM_CRIT, CHARM_BOW, CHARM_PROTECTION,
				CHARM_MAGIC, CHARM_EXPLOSION, CHARM_LUCK
		};
	}

	private static ItemEntry<?>[] angelic() {
		return new ItemEntry<?>[]{ANGELIC_WING, ANGELIC_BLESS, ANGELIC_DESCENT, ANGELIC_PROTECTION, ANGELIC_PUNISHMENT};
	}

	private static ItemEntry<?>[] hell() {
		return new ItemEntry<?>[]{HELLFIRE_SKULL, HELLFIRE_REFORMATION, EYE_OF_CURSED_SOULS, CURSED_SOUL_CRYSTAL, CROWN_OF_DEMON};
	}

	private static ItemEntry<?>[] shadow() {
		return new ItemEntry<?>[]{SHADOW_CORE, SHADOW_CONVERGENCE, SHADOW_CONSOLIDATION, SHADOW_REFORMATION, VOID_OVERFLOW};
	}

	private static ItemEntry<?>[] abyssal() {
		return new ItemEntry<?>[]{ABYSSAL_TREASURE, ABYSSAL_WATCHER, ABYSSAL_SHELL, ABYSSAL_CROWN, ABYSSAL_WILL};
	}

	private static ItemEntry<?>[] barbaric() {
		return new ItemEntry<?>[]{BARBARIC_INSTINCT, BARBARIC_WRATH, BARBARIC_ROAR, BARBARIC_BLADE, PRIMAL_FORCE};
	}

	private static ItemEntry<?>[] elemental() {
		return new ItemEntry<?>[]{WIND_THRUST, EARTH_CRUSH, FLAMING_EXPLOSION, WAVING_SPELL, CURSE_REDIRECTION};
	}


	private static LootTable.Builder buildPlaceholderLoot() {
		return new PoolBuilder().fromTag(100, attr()).build();
	}

	public enum LootDefinition {
		// end
		END_CITY_TREASURE(0.2, 0.1, BuiltInLootTables.END_CITY_TREASURE,
				() -> new PoolBuilder().fromTag(100, attr()).fromTag(50, shadow())
						.addItem(200, CoPItems.ENDER_CHARM.get()).build()),
		// nethers
		NETHER_BRIDGE(0.2, 0.1, BuiltInLootTables.NETHER_BRIDGE,
				() -> new PoolBuilder().fromTag(100, attr()).fromTag(50, hell())
						.addItem(200, CoPItems.BLESS_LAVA_WALKER.get()).build()),
		RUINED_PORTAL(0.2, 0.1, BuiltInLootTables.RUINED_PORTAL,
				() -> new PoolBuilder().fromTag(100, attr())
						.addItem(200, CoPItems.BLESS_LAVA_WALKER.get())
						.addItem(200, CoPItems.GOLDEN_HEART.get()).build()),
		BASTION_TREASURE(1, 0.2, BuiltInLootTables.BASTION_TREASURE,
				() -> new PoolBuilder().fromTag(100, attr()).fromTag(50, hell())
						.addItem(200, CoPItems.BLESS_LAVA_WALKER.get())
						.addItem(200, CoPItems.GOLDEN_HEART.get())
						.addItem(200, CoPItems.STABLE_BODY.get()).build()),
		BASTION_OTHER(0.2, 0.1, BuiltInLootTables.BASTION_OTHER,
				() -> new PoolBuilder().fromTag(100, attr()).fromTag(50, hell())
						.addItem(200, CoPItems.BLESS_LAVA_WALKER.get())
						.addItem(200, CoPItems.GOLDEN_HEART.get())
						.addItem(200, CoPItems.STABLE_BODY.get()).build()),
		BASTION_BRIDGE(0.2, 0.1, BuiltInLootTables.BASTION_BRIDGE,
				() -> new PoolBuilder().fromTag(100, attr()).fromTag(50, barbaric())
						.addItem(200, CoPItems.BLESS_LAVA_WALKER.get())
						.addItem(200, CoPItems.GOLDEN_HEART.get())
						.addItem(200, CoPItems.STABLE_BODY.get()).build()),
		// overworld
		IGLOO_CHEST(0.2, 0.1, BuiltInLootTables.IGLOO_CHEST,
				() -> new PoolBuilder().fromTag(100, attr())
						.addItem(400, CoPItems.BLESS_SNOW_WALKER.get()).build()),
		ANCIENT_CITY(0.4, 0.2, BuiltInLootTables.ANCIENT_CITY,
				() -> new PoolBuilder().fromTag(100, attr()).fromTag(50, abyssal()).build()),
		WOODLAND_MANSION(0.4, 0.2, BuiltInLootTables.WOODLAND_MANSION,
				() -> new PoolBuilder().fromTag(100, attr()).fromTag(50, elemental()).build()),
		DESERT_PYRAMID(0.2, 0.1, BuiltInLootTables.DESERT_PYRAMID,
				() -> new PoolBuilder().fromTag(100, attr()).fromTag(50, angelic()).build()),
		SHIPWRECK_TREASURE(0.2, 0.1, BuiltInLootTables.SHIPWRECK_TREASURE,
				() -> new PoolBuilder().fromTag(100, attr()).fromTag(50, elemental()).build()),
		UNDERWATER_RUIN_BIG(0.2, 0.1, BuiltInLootTables.UNDERWATER_RUIN_BIG,
				() -> new PoolBuilder().fromTag(100, attr()).fromTag(50, angelic()).build()),
		STRONGHOLD_CORRIDOR(0.2, 0.1, BuiltInLootTables.STRONGHOLD_CORRIDOR,
				() -> new PoolBuilder().fromTag(100, attr()).fromTag(50, elemental()).build()),
		PILLAGER_OUTPOST(0.2, 0.1, BuiltInLootTables.PILLAGER_OUTPOST,
				() -> new PoolBuilder().fromTag(100, attr()).fromTag(50, elemental()).build()),
		JUNGLE_TEMPLE(0.2, 0.1, BuiltInLootTables.JUNGLE_TEMPLE,
				() -> new PoolBuilder().fromTag(100, attr()).fromTag(50, angelic()).build()),
		ABANDONED_MINESHAFT(0.1, 0.05, BuiltInLootTables.ABANDONED_MINESHAFT, LootGen::buildPlaceholderLoot),
		SIMPLE_DUNGEON(0.1, 0.05, BuiltInLootTables.SIMPLE_DUNGEON, LootGen::buildPlaceholderLoot),
		VILLAGE_TEMPLE(0.1, 0.05, BuiltInLootTables.VILLAGE_TEMPLE, LootGen::buildPlaceholderLoot),
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
