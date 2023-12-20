package dev.xkmc.curseofpandora.init;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.curseofpandora.event.PandoraAttackListener;
import dev.xkmc.curseofpandora.init.data.*;
import dev.xkmc.curseofpandora.init.registrate.CoPFakeEffects;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2damagetracker.contents.attack.AttackEventHandler;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.serial.config.PacketHandlerWithConfig;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CurseOfPandora.MODID)
@Mod.EventBusSubscriber(modid = CurseOfPandora.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CurseOfPandora {

	public static final String MODID = "curseofpandora";

	public static final RegistryEntry<Attribute> SPELL = reg("spell_tolerance", 1, 10000, "Spell Tolerance");
	public static final RegistryEntry<Attribute> REALITY = reg("reality_index", 0, 10000, "Reality Index");

	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			new ResourceLocation(MODID, "main"), 2
	);
	public static final Logger LOGGER = LogManager.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);

	public CurseOfPandora() {
		CoPItems.register();
		CoPFakeEffects.register();
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
		event.add(EntityType.PLAYER, SPELL.get());
		event.add(EntityType.PLAYER, REALITY.get());
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		boolean gen = event.includeServer();
		PackOutput output = event.getGenerator().getPackOutput();
		var pvd = event.getLookupProvider();
		var helper = event.getExistingFileHelper();
		event.getGenerator().addProvider(gen, new CoPConfigGen(event.getGenerator()));
	}

	private static RegistryEntry<Attribute> reg(String id, double def, double max, String name) {
		REGISTRATE.addRawLang("attribute.name." + id, name);
		return REGISTRATE.simple(id, ForgeRegistries.ATTRIBUTES.getRegistryKey(),
				() -> new RangedAttribute("attribute.name." + id, def, 0, max)
						.setSyncable(true));
	}

}
