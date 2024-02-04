package dev.xkmc.curseofpandora.init.loot;

import com.mojang.serialization.Codec;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.registries.ForgeRegistries;

public class CoPGLMProvider extends GlobalLootModifierProvider {

	public static final RegistryEntry<Codec<LuckAppendTableLootModifier>> GLM = CurseOfPandora.REGISTRATE.simple("append_table",
			ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> LuckAppendTableLootModifier.CODEC);
	public static final RegistryEntry<Codec<MobKillMobLootModifier>> MKM = CurseOfPandora.REGISTRATE.simple("mob_kills_mob",
			ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> MobKillMobLootModifier.CODEC);

	public static void register() {

	}

	public CoPGLMProvider(PackOutput gen) {
		super(gen, CurseOfPandora.MODID);
	}

	@Override
	protected void start() {
		for (LootGen.LootDefinition def : LootGen.LootDefinition.values()) {
			this.add(def.id, new LuckAppendTableLootModifier(def.chance, def.bonus, def.getInner(), LootTableIdCondition.builder(def.table).build()));
		}
		add(EntityType.CREEPER, EntityType.PIG, CoPItems.TRANSMUTED_TISSUE.get(), 1);
		add(EntityType.PIG, EntityType.CREEPER, CoPItems.UNSTABLE_MATTER.get(), 1);
		add(EntityType.ZOMBIE, EntityType.PILLAGER, CoPItems.ROTTEN_SPINE.get(), 1);
		add(EntityType.DROWNED, EntityType.PILLAGER, CoPItems.ERODED_SPINE.get(), 1);
		add(EntityType.HUSK, EntityType.PILLAGER, CoPItems.DRIED_SPINE.get(), 1);
		add(EntityType.ZOMBIE, EntityType.EVOKER, CoPItems.ROTTEN_BRAIN.get(), 1);
		add(EntityType.DROWNED, EntityType.EVOKER, CoPItems.ERODED_BRAIN.get(), 1);
		add(EntityType.HUSK, EntityType.EVOKER, CoPItems.DRIED_BRAIN.get(), 1);
	}

	private void add(EntityType<?> killer, EntityType<?> target, Item item, double chance) {
		var rl = ForgeRegistries.ITEMS.getKey(item);
		assert rl != null;
		add(rl.toString(), new MobKillMobLootModifier(killer, target, chance, item));
	}

}
