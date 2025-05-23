package dev.xkmc.curseofpandora.init.data;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.curseofpandora.compat.CoPTraits;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.registrate.CoPEffects;
import dev.xkmc.l2complements.init.data.LCTagGen;
import dev.xkmc.l2hostility.init.registrate.LHItems;
import dev.xkmc.pandora.init.data.PandoraTagGen;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class CoPTagGen {
	public static final TagKey<Item> PANDORA_BASE = ItemTags.create(CurseOfPandora.loc("pandora_base"));
	public static final TagKey<Item> ATTR = ItemTags.create(CurseOfPandora.loc("attribute_charms"));
	public static final TagKey<Item> BEACON = ItemTags.create(CurseOfPandora.loc("mini_beacons"));
	public static final TagKey<Item> CURSE = ItemTags.create(CurseOfPandora.loc("pandora_curse"));

	public static final TagKey<Item> REALITY = ItemTags.create(CurseOfPandora.loc("reality_charms"));
	public static final TagKey<Item> ANGELIC = ItemTags.create(CurseOfPandora.loc("angelic_charms"));
	public static final TagKey<Item> HELL = ItemTags.create(CurseOfPandora.loc("hell_charms"));
	public static final TagKey<Item> SHADOW = ItemTags.create(CurseOfPandora.loc("shadow_charms"));
	public static final TagKey<Item> ABYSSAL = ItemTags.create(CurseOfPandora.loc("abyssal_charms"));
	public static final TagKey<Item> BARBARIC = ItemTags.create(CurseOfPandora.loc("barbaric_charms"));
	public static final TagKey<Item> MUTATION = ItemTags.create(CurseOfPandora.loc("mutation_charms"));
	public static final TagKey<Item> EVIL = ItemTags.create(CurseOfPandora.loc("evil_spirit_charms"));
	public static final TagKey<Item> ELEMENTAL = ItemTags.create(CurseOfPandora.loc("elemental_charms"));

	public static final TagKey<Item> PANDORA_SLOT = ItemTags.create(CurseOfPandora.loc("pandora_slot"));
	public static final TagKey<Item> ALLOW_DUPLICATE = ItemTags.create(CurseOfPandora.loc("allow_duplicate"));

	public static final TagKey<EntityType<?>> PRUDENCE_WHITELIST = TagKey.create(Registries.ENTITY_TYPE, CurseOfPandora.loc("prudence_whitelist"));

	public static final TagKey<MobEffect> HIDDEN = TagKey.create(Registries.MOB_EFFECT,
			ResourceLocation.fromNamespaceAndPath("jeed", "hidden"));

	@SuppressWarnings("unchecked")
	public static void onItemTagGen(RegistrateItemTagsProvider pvd) {
		pvd.addTag(REALITY).addTags(ANGELIC, HELL, SHADOW, ABYSSAL, BARBARIC, EVIL, ELEMENTAL);// MUTATION
		pvd.addTag(PandoraTagGen.PANDORA_SLOT).addTags(PANDORA_BASE, BEACON, CURSE)
				.addOptional(LHItems.LOOT_1.getId())
				.addOptional(LHItems.LOOT_2.getId())
				.addOptional(LHItems.LOOT_3.getId())
				.addOptional(LHItems.LOOT_4.getId());
		pvd.addTag(PANDORA_BASE).addTags(ATTR, REALITY)
				.addOptional(CoPTraits.SEAL_OF_SWORDS.getId())
				.addOptional(CoPTraits.SPELL_SINGULARITY.getId());
	}

	public static void onEffectTagGen(RegistrateTagsProvider.IntrinsicImpl<MobEffect> pvd) {
		pvd.addTag(LCTagGen.SKILL_EFFECT).add(
				CoPEffects.FAKE_TERRORIZED.get(),
				CoPEffects.FAKE_TERROR_PRE.get(),
				CoPEffects.FAKE_TERROR.get(),
				CoPEffects.PRUDENCE.get(),
				CoPEffects.SHADOW.get(),
				CoPEffects.AWAKENING.get(),
				CoPEffects.SPIRIT_WALK.get()
		);
		pvd.addTag(HIDDEN).add(
				CoPEffects.FAKE_TERRORIZED.get(),
				CoPEffects.FAKE_TERROR_PRE.get(),
				CoPEffects.FAKE_TERROR.get(),
				CoPEffects.PRUDENCE.get());
	}

	public static void onEntityTagGen(RegistrateTagsProvider.IntrinsicImpl<EntityType<?>> pvd) {
		pvd.addTag(PRUDENCE_WHITELIST).add(EntityType.VEX);

	}

}
