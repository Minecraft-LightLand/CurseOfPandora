package dev.xkmc.curseofpandora.init.data;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.registrate.CoPEffects;
import dev.xkmc.l2complements.init.data.TagGen;
import dev.xkmc.pandora.init.data.PandoraTagGen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class CoPTagGen {
	public static final TagKey<Item> PANDORA_BASE = ItemTags.create(new ResourceLocation(CurseOfPandora.MODID, "pandora_base"));
	public static final TagKey<Item> ATTR = ItemTags.create(new ResourceLocation(CurseOfPandora.MODID, "attribute_charms"));
	public static final TagKey<Item> BEACON = ItemTags.create(new ResourceLocation(CurseOfPandora.MODID, "mini_beacons"));
	public static final TagKey<Item> CURSE = ItemTags.create(new ResourceLocation(CurseOfPandora.MODID, "pandora_curse"));

	public static final TagKey<Item> REALITY = ItemTags.create(new ResourceLocation(CurseOfPandora.MODID, "reality_charms"));
	public static final TagKey<Item> ANGELIC = ItemTags.create(new ResourceLocation(CurseOfPandora.MODID, "angelic_charms"));
	public static final TagKey<Item> HELL = ItemTags.create(new ResourceLocation(CurseOfPandora.MODID, "hell_charms"));
	public static final TagKey<Item> SHADOW = ItemTags.create(new ResourceLocation(CurseOfPandora.MODID, "shadow_charms"));
	public static final TagKey<Item> ELEMENTAL = ItemTags.create(new ResourceLocation(CurseOfPandora.MODID, "elemental_charms"));

	public static final TagKey<MobEffect> HIDDEN = TagKey.create(ForgeRegistries.MOB_EFFECTS.getRegistryKey(),
			new ResourceLocation("jeed", "hidden"));

	@SuppressWarnings("unchecked")
	public static void onItemTagGen(RegistrateItemTagsProvider pvd) {
		pvd.addTag(REALITY).addTags(ANGELIC, HELL, SHADOW, ELEMENTAL);
		pvd.addTag(PandoraTagGen.PANDORA_SLOT).addTags(ATTR, BEACON, CURSE, REALITY);
		pvd.addTag(PANDORA_BASE).addTags(ATTR, REALITY);
	}

	public static void onEffectTagGen(RegistrateTagsProvider.IntrinsicImpl<MobEffect> pvd) {
		pvd.addTag(TagGen.SKILL_EFFECT).add(
				CoPEffects.FAKE_TERRORIZED.get(),
				CoPEffects.FAKE_TERROR_PRE.get(),
				CoPEffects.FAKE_TERROR.get(),
				CoPEffects.PRUDENCE.get(),
				CoPEffects.SHADOW.get()
		);
		pvd.addTag(HIDDEN).add(
				CoPEffects.FAKE_TERRORIZED.get(),
				CoPEffects.FAKE_TERROR_PRE.get(),
				CoPEffects.FAKE_TERROR.get(),
				CoPEffects.PRUDENCE.get());
	}

}
