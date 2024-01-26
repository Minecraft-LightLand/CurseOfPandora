package dev.xkmc.curseofpandora.init.data;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.pandora.init.data.PandoraTagGen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

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


	@SuppressWarnings("unchecked")
	public static void onItemTagGen(RegistrateItemTagsProvider pvd) {
		pvd.addTag(REALITY).addTags(ANGELIC, HELL, SHADOW, ELEMENTAL);
		pvd.addTag(PandoraTagGen.ALLOW_DUPLICATE).addTag(ATTR);
		pvd.addTag(PandoraTagGen.PANDORA_SLOT).addTags(ATTR, BEACON, CURSE, REALITY);
		pvd.addTag(PANDORA_BASE).addTags(ATTR, BEACON, REALITY);
	}
}
