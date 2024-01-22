package dev.xkmc.curseofpandora.init;

import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.curseofpandora.event.PandoraAttackListener;
import dev.xkmc.curseofpandora.init.data.*;
import dev.xkmc.curseofpandora.init.registrate.CoPFakeEffects;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2damagetracker.contents.attack.AttackEventHandler;
import dev.xkmc.l2hostility.init.entries.LHRegistrate;
import dev.xkmc.l2library.serial.config.PacketHandlerWithConfig;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CurseOfPandora.MODID)
@Mod.EventBusSubscriber(modid = CurseOfPandora.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CurseOfPandora {

	public static final String MODID = "curseofpandora";

	public static final Logger LOGGER = LogManager.getLogger();
	public static final LHRegistrate REGISTRATE = new LHRegistrate(MODID);

	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			new ResourceLocation(MODID, "main"), 2
	);

	public CurseOfPandora() {
		CoPItems.register();
		CoPFakeEffects.register();
		CoPMisc.register();
		CoPConfig.init();
		AttackEventHandler.register(5200, new PandoraAttackListener());
		REGISTRATE.addDataGenerator(ProviderType.LANG, CoPLangData::addTranslations);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, CoPRecipeGen::recipeGen);
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, CoPTagGen::onItemTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ADVANCEMENT, CoPAdvGen::onAdvGen);
	}

	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {

		});
	}

	@SubscribeEvent
	public static void modifyAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, CoPMisc.SPELL.get());
		event.add(EntityType.PLAYER, CoPMisc.REALITY.get());
		event.add(EntityType.PLAYER, CoPMisc.REDUCTION.get());
		event.add(EntityType.PLAYER, CoPMisc.ABSORB.get());
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		boolean gen = event.includeServer();
		PackOutput output = event.getGenerator().getPackOutput();
		var pvd = event.getLookupProvider();
		var helper = event.getExistingFileHelper();
		event.getGenerator().addProvider(gen, new CoPConfigGen(event.getGenerator()));
		new CoPDamageTypeGen(output, pvd, helper).generate(gen, event.getGenerator());
	}

}
