package dev.xkmc.curseofpandora.init.loot;

import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2core.init.reg.simple.CdcReg;
import dev.xkmc.l2core.init.reg.simple.CdcVal;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.concurrent.CompletableFuture;

public class CoPGLMProvider extends GlobalLootModifierProvider {

	private static final CdcReg<IGlobalLootModifier> REG = CdcReg.of(CurseOfPandora.REG, NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS);

	public static final CdcVal<LuckAppendTableLootModifier> GLM = REG.reg("append_table", LuckAppendTableLootModifier.CODEC);
	public static final CdcVal<MobKillMobLootModifier> MKM = REG.reg("mob_kills_mob", MobKillMobLootModifier.CODEC);

	public static void register() {

	}

	public CoPGLMProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, CurseOfPandora.MODID);
	}

	@Override
	protected void start() {
		for (LootGen.LootDefinition def : LootGen.LootDefinition.values()) {
			this.add(def.id, new LuckAppendTableLootModifier(def.chance, def.bonus, def.getInner(), LootTableIdCondition.builder(def.table.location()).build()));
		}
		//add(EntityType.CREEPER, EntityType.PIG, CoPItems.TRANSMUTED_TISSUE.get(), 0.5);
		//add(EntityType.PIG, EntityType.CREEPER, CoPItems.UNSTABLE_MATTER.get(), 1);
		add(EntityType.ZOMBIE, EntityType.PILLAGER, CoPItems.ROTTEN_SPINE.get(), 1);
		add(EntityType.DROWNED, EntityType.PILLAGER, CoPItems.ERODED_SPINE.get(), 1);
		add(EntityType.HUSK, EntityType.PILLAGER, CoPItems.DRIED_SPINE.get(), 1);
		add(EntityType.ZOMBIE, EntityType.EVOKER, CoPItems.ROTTEN_BRAIN.get(), 1);
		add(EntityType.DROWNED, EntityType.EVOKER, CoPItems.ERODED_BRAIN.get(), 1);
		add(EntityType.HUSK, EntityType.EVOKER, CoPItems.DRIED_BRAIN.get(), 1);
		add(EntityType.ZOGLIN, EntityType.HOGLIN, CoPItems.BARBARIC_BLOOD.get(), 1);
	}

	private void add(EntityType<?> killer, EntityType<?> target, Item item, double chance) {
		var rl = BuiltInRegistries.ITEM.getKey(item);
		add(rl.getPath(), new MobKillMobLootModifier(killer, target, chance, item));
	}

}
