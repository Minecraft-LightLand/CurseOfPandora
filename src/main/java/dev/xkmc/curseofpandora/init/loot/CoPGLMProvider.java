package dev.xkmc.curseofpandora.init.loot;

import com.mojang.serialization.Codec;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.registries.ForgeRegistries;

public class CoPGLMProvider extends GlobalLootModifierProvider {

	public static final RegistryEntry<Codec<LuckAppendTableLootModifier>> GLM = CurseOfPandora.REGISTRATE.simple("append_table",
			ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> LuckAppendTableLootModifier.CODEC);

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
	}
}
