package dev.xkmc.curseofpandora.init.data;

import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.l2damagetracker.init.data.DamageTypeAndTagsGen;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class CoPDamageTypeGen extends DamageTypeAndTagsGen {
	public static final ResourceKey<DamageType> SOUL_CURSE = create("soul_curse");
	public static final ResourceKey<DamageType> SHADOW_CURSE = create("shadow_curse");
	public static final ResourceKey<DamageType> VOID_CURSE = create("void_curse");

	public static final TagKey<DamageType> SHADOW = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(CurseOfPandora.MODID, "shadow"));

	public CoPDamageTypeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> pvd, ExistingFileHelper helper) {
		super(output, pvd, helper, CurseOfPandora.MODID);
		new DamageTypeHolder(SOUL_CURSE, new DamageType("soul_curse", DamageScaling.NEVER, 0.1F))
				.add(L2DamageTypes.MAGIC, DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.AVOIDS_GUARDIAN_THORNS);
		new DamageTypeHolder(SHADOW_CURSE, new DamageType("shadow_curse", DamageScaling.NEVER, 0.1F))
				.add(L2DamageTypes.MAGIC, DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.AVOIDS_GUARDIAN_THORNS,
						DamageTypeTags.BYPASSES_COOLDOWN, SHADOW);
		new DamageTypeHolder(VOID_CURSE, new DamageType("void_curse", DamageScaling.NEVER, 0.1F))
				.add(L2DamageTypes.MAGIC, DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.BYPASSES_ENCHANTMENTS, SHADOW,
						DamageTypeTags.BYPASSES_EFFECTS, DamageTypeTags.BYPASSES_RESISTANCE,
						DamageTypeTags.BYPASSES_COOLDOWN, DamageTypeTags.AVOIDS_GUARDIAN_THORNS);
	}

	public static Holder<DamageType> forKey(Level level, ResourceKey<DamageType> key) {
		return level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key);
	}

	private static ResourceKey<DamageType> create(String id) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(CurseOfPandora.MODID, id));
	}
}
