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
import dev.xkmc.l2core.init.L2TagGen;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2core.init.reg.simple.IngReg;
import dev.xkmc.l2core.init.reg.simple.IngVal;
import dev.xkmc.l2core.init.reg.simple.Reg;
import dev.xkmc.l2core.serial.config.PacketHandlerWithConfig;
import dev.xkmc.l2damagetracker.contents.attack.AttackEventHandler;
import dev.xkmc.l2hostility.init.L2Hostility;
import dev.xkmc.l2serial.network.PacketHandler;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CurseOfPandora.MODID)
@EventBusSubscriber(modid = CurseOfPandora.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CurseOfPandora {

	public static final String MODID = "curseofpandora";

	public static final Logger LOGGER = LogManager.getLogger();
	public static final Reg REG = new Reg(MODID);
	public static final L2Registrate REGISTRATE;

	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			MODID, 2,
			e -> e.create(LootDataToClient.class, PacketHandler.NetDir.PLAY_TO_CLIENT)
	);

	public static final IngVal<EnchantmentTagIngredient> ING_ENCH_TAG;

	static {
		if (ModList.get().isLoaded(L2Hostility.MODID)) {
			REGISTRATE = CoPLoader.getLHRegistrate(MODID);
		} else {
			REGISTRATE = new L2Registrate(MODID);
		}
		ING_ENCH_TAG = IngReg.of(REG).reg("enchantment_tag", EnchantmentTagIngredient.class);
	}

	public CurseOfPandora() {
		CoPItems.register();
		CoPEffects.register();
		CoPAttrs.register();
		CoPEntities.register();
		CoPGLMProvider.register();
		CoPConfig.init();
		CoPDamageTypeGen.register();
		if (ModList.get().isLoaded(L2Hostility.MODID))
			CoPTraits.register();
		AttackEventHandler.register(5200, new PandoraAttackListener());
	}

	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			ItemUseEventHandler.LIST.add(new ItemClickListener());
		});
	}

	@SubscribeEvent
	public static void modifyAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, CoPAttrs.SPELL);
		event.add(EntityType.PLAYER, CoPAttrs.REALITY);
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void gatherData(GatherDataEvent event) {
		CoPAdvGen.init();
		REGISTRATE.addDataGenerator(ProviderType.LANG, CoPLangData::addTranslations);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, CoPRecipeGen::recipeGen);
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, CoPTagGen::onItemTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, CoPTagGen::onEntityTagGen);
		REGISTRATE.addDataGenerator(L2TagGen.EFF_TAGS, CoPTagGen::onEffectTagGen);
		REGISTRATE.addDataGenerator(ProviderType.ADVANCEMENT, CoPAdvGen::onAdvGen);
		REGISTRATE.addDataGenerator(ProviderType.LOOT, LootGen::genLoot);
		REGISTRATE.addDataGenerator(ProviderType.DATA_MAP, CoPDataMapGen::genDataMap);
		new CoPDamageTypeGen(REGISTRATE).generate();
		var init = REGISTRATE.getDataGenInitializer();


		boolean run = event.includeServer();
		var gen = event.getGenerator();
		PackOutput output = gen.getPackOutput();
		var pvd = event.getLookupProvider();
		var helper = event.getExistingFileHelper();
		gen.addProvider(run, new CoPGLMProvider(output, pvd));
		gen.addProvider(run, new CoPSlotGen(output, helper, pvd));
	}

	public static ResourceLocation loc(String id) {
		return ResourceLocation.fromNamespaceAndPath(MODID, id);
	}

}
