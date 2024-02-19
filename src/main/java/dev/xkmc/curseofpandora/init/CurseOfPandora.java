package dev.xkmc.curseofpandora.init;

import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.curseofpandora.compat.CoPLoader;
import dev.xkmc.curseofpandora.compat.CoPTraits;
import dev.xkmc.curseofpandora.event.ItemClickListener;
import dev.xkmc.curseofpandora.event.PandoraAttackListener;
import dev.xkmc.curseofpandora.init.data.*;
import dev.xkmc.curseofpandora.init.loot.CoPGLMProvider;
import dev.xkmc.curseofpandora.init.loot.LootDataToClient;
import dev.xkmc.curseofpandora.init.loot.LootGen;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.curseofpandora.init.registrate.CoPEffects;
import dev.xkmc.curseofpandora.init.registrate.CoPEntities;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2complements.events.ItemUseEventHandler;
import dev.xkmc.l2complements.init.data.TagGen;
import dev.xkmc.l2damagetracker.contents.attack.AttackEventHandler;
import dev.xkmc.l2hostility.init.L2Hostility;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.init.events.EffectSyncEvents;
import dev.xkmc.l2library.serial.config.PacketHandlerWithConfig;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CurseOfPandora.MODID)
@Mod.EventBusSubscriber(modid = CurseOfPandora.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CurseOfPandora {

	public static final String MODID = "curseofpandora";

	public static final Logger LOGGER = LogManager.getLogger();
	public static final L2Registrate REGISTRATE;

	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			new ResourceLocation(MODID, "main"), 2,
			e -> e.create(LootDataToClient.class, NetworkDirection.PLAY_TO_CLIENT)
	);

	static {
		if (ModList.get().isLoaded(L2Hostility.MODID)) {
			REGISTRATE = CoPLoader.getLHRegistrate(MODID);
		} else {
			REGISTRATE = new L2Registrate(MODID);
		}
	}

	public CurseOfPandora() {
		CoPItems.register();
		CoPEffects.register();
		CoPAttrs.register();
		CoPEntities.register();
		CoPGLMProvider.register();
		CoPConfig.init();
		if (ModList.get().isLoaded(L2Hostility.MODID))
			CoPTraits.register();
		AttackEventHandler.register(5200, new PandoraAttackListener());
		REGISTRATE.addDataGenerator(ProviderType.LANG, CoPLangData::addTranslations);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, CoPRecipeGen::recipeGen);
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, CoPTagGen::onItemTagGen);
		REGISTRATE.addDataGenerator(TagGen.EFF_TAGS, CoPTagGen::onEffectTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ADVANCEMENT, CoPAdvGen::onAdvGen);
		REGISTRATE.addDataGenerator(ProviderType.LOOT, LootGen::genLoot);
	}

	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			ItemUseEventHandler.LIST.add(new ItemClickListener());

			EffectSyncEvents.TRACKED.add(CoPEffects.SHADOW.get());
		});
	}

	@SubscribeEvent
	public static void modifyAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, CoPAttrs.SPELL.get());
		event.add(EntityType.PLAYER, CoPAttrs.REALITY.get());
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		boolean gen = event.includeServer();
		PackOutput output = event.getGenerator().getPackOutput();
		var pvd = event.getLookupProvider();
		var helper = event.getExistingFileHelper();
		event.getGenerator().addProvider(gen, new CoPConfigGen(event.getGenerator()));
		new CoPDamageTypeGen(output, pvd, helper).generate(gen, event.getGenerator());
		event.getGenerator().addProvider(gen, new CoPGLMProvider(output));
	}

	@SubscribeEvent
	public static void registerRecipeSerializers(RegisterEvent event) {
		if (event.getRegistryKey().equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS)) {
			CraftingHelper.register(CurseIngredient.INSTANCE.id(), CurseIngredient.INSTANCE);
		}
	}

}
