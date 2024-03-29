package dev.xkmc.curseofpandora.init.data;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import dev.xkmc.curseofpandora.content.reality.CursePandoraUtil;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2complements.content.recipe.BurntRecipeBuilder;
import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.materials.LCMats;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2hostility.init.L2Hostility;
import dev.xkmc.l2hostility.init.registrate.LHItems;
import dev.xkmc.l2hostility.init.registrate.LHTraits;
import dev.xkmc.l2library.serial.conditions.BooleanValueCondition;
import dev.xkmc.l2library.serial.ingredients.EnchantmentIngredient;
import dev.xkmc.l2library.serial.ingredients.PotionIngredient;
import dev.xkmc.l2library.serial.recipe.ConditionalRecipeWrapper;
import dev.xkmc.l2library.serial.recipe.NBTRecipe;
import dev.xkmc.pandora.init.registrate.PandoraItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiFunction;

public class CoPRecipeGen {

	private static String currentFolder = "";

	public static void recipeGen(RegistrateRecipeProvider pvd) {

		currentFolder = "burnt/";
		{
			convert(pvd, Ingredient.of(ItemTags.BEACON_PAYMENT_ITEMS), CoPItems.MINI_BEACON_BASE.get(), 164 * 9, "_ingot");
			convert(pvd, Ingredient.of(Items.IRON_BLOCK, Items.GOLD_BLOCK, Items.EMERALD_BLOCK,
					Items.DIAMOND_BLOCK, Items.NETHERITE_BLOCK), CoPItems.MINI_BEACON_BASE.get(), 164, "_block");
		}

		currentFolder = "pandora/";

		unlock(pvd, SimpleCookingRecipeBuilder.blasting(Ingredient.of(CoPTagGen.PANDORA_BASE),
				RecipeCategory.MISC, CoPItems.CHARM.get(), 1, 200)::unlockedBy, CoPItems.CHARM.get())
				.save(pvd, getID(CoPItems.CHARM.get(), "_smelt"));

		unlock(pvd, SimpleCookingRecipeBuilder.blasting(Ingredient.of(CoPTagGen.BEACON),
				RecipeCategory.MISC, CoPItems.MINI_BEACON.get(), 1, 200)::unlockedBy, CoPItems.MINI_BEACON.get())
				.save(pvd, getID(CoPItems.MINI_BEACON.get(), "_smelt"));


		// beacons
		{

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.MINI_BEACON.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern("EBE").pattern("ACA").pattern("EDE")
					.define('B', Items.BEACON)
					.define('C', CoPItems.CHARM.get())
					.define('D', CoPItems.MINI_BEACON_BASE.get())
					.define('A', LCItems.STORM_CORE.get())
					.define('E', Items.ENDER_PEARL)
					.save(pvd, getID(CoPItems.MINI_BEACON.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.MINI_BEACON_SPEED.get())::unlockedBy, CoPItems.MINI_BEACON.get())
					.pattern("ABA").pattern("BCB").pattern("ADA")
					.define('A', Items.BLAZE_POWDER)
					.define('B', new PotionIngredient(Potions.STRONG_SWIFTNESS))
					.define('C', CoPItems.MINI_BEACON.get())
					.define('D', ItemTags.BEACON_PAYMENT_ITEMS)
					.save(pvd, getID(CoPItems.MINI_BEACON_SPEED.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.MINI_BEACON_JUMP.get())::unlockedBy, CoPItems.MINI_BEACON.get())
					.pattern("ABA").pattern("BCB").pattern("ADA")
					.define('A', Items.BLAZE_POWDER)
					.define('B', new PotionIngredient(Potions.STRONG_LEAPING))
					.define('C', CoPItems.MINI_BEACON.get())
					.define('D', ItemTags.BEACON_PAYMENT_ITEMS)
					.save(pvd, getID(CoPItems.MINI_BEACON_JUMP.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.MINI_BEACON_RESISTANCE.get())::unlockedBy, CoPItems.MINI_BEACON.get())
					.pattern("ABA").pattern("BCB").pattern("ADA")
					.define('A', Items.BLAZE_POWDER)
					.define('B', new PotionIngredient(Potions.TURTLE_MASTER))
					.define('C', CoPItems.MINI_BEACON.get())
					.define('D', ItemTags.BEACON_PAYMENT_ITEMS)
					.save(pvd, getID(CoPItems.MINI_BEACON_RESISTANCE.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.MINI_BEACON_REGEN.get())::unlockedBy, CoPItems.MINI_BEACON.get())
					.pattern("ABA").pattern("BCB").pattern("ADA")
					.define('A', Items.BLAZE_POWDER)
					.define('B', new PotionIngredient(Potions.STRONG_REGENERATION))
					.define('C', CoPItems.MINI_BEACON.get())
					.define('D', ItemTags.BEACON_PAYMENT_ITEMS)
					.save(pvd, getID(CoPItems.MINI_BEACON_REGEN.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.MINI_BEACON_ATTACK.get())::unlockedBy, CoPItems.MINI_BEACON.get())
					.pattern("ABA").pattern("BCB").pattern("ADA")
					.define('A', Items.BLAZE_POWDER)
					.define('B', new PotionIngredient(Potions.STRONG_STRENGTH))
					.define('C', CoPItems.MINI_BEACON.get())
					.define('D', ItemTags.BEACON_PAYMENT_ITEMS)
					.save(pvd, getID(CoPItems.MINI_BEACON_ATTACK.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.MINI_BEACON_HASTE.get())::unlockedBy, CoPItems.MINI_BEACON.get())
					.pattern("ABA").pattern("BCB").pattern("ADA")
					.define('A', Items.BLAZE_POWDER)
					.define('B', Items.GOLD_INGOT)
					.define('C', CoPItems.MINI_BEACON.get())
					.define('D', ItemTags.BEACON_PAYMENT_ITEMS)
					.save(pvd, getID(CoPItems.MINI_BEACON_HASTE.get()));

		}

		// reject
		{
			BooleanValueCondition cond = BooleanValueCondition.of(LCConfig.COMMON_PATH, LCConfig.COMMON.enableImmunityEnchantments, true);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.PROJECTILE_REJECT.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern("1B1").pattern("BCB").pattern("2B2")
					.define('1', new EnchantmentIngredient(Enchantments.PROJECTILE_PROTECTION, 4))
					.define('2', new EnchantmentIngredient(Enchantments.ALL_DAMAGE_PROTECTION, 4))
					.define('B', LCItems.FORCE_FIELD.get())
					.define('C', CoPItems.CHARM.get())
					.save(ConditionalRecipeWrapper.of(pvd, cond), getID(CoPItems.PROJECTILE_REJECT.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.EXPLOSION_REJECT.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern("1B1").pattern("BCB").pattern("2B2")
					.define('1', new EnchantmentIngredient(Enchantments.BLAST_PROTECTION, 4))
					.define('2', new EnchantmentIngredient(Enchantments.ALL_DAMAGE_PROTECTION, 4))
					.define('B', LCItems.EXPLOSION_SHARD.get())
					.define('C', CoPItems.CHARM.get())
					.save(ConditionalRecipeWrapper.of(pvd, cond), getID(CoPItems.EXPLOSION_REJECT.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.ENVIRONMENTAL_REJECT.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern("1B1").pattern("BCB").pattern("2B2")
					.define('1', LCItems.SUN_MEMBRANE.get())
					.define('2', new EnchantmentIngredient(Enchantments.ALL_DAMAGE_PROTECTION, 4))
					.define('B', LCItems.VOID_EYE.get())
					.define('C', CoPItems.CHARM.get())
					.save(ConditionalRecipeWrapper.of(pvd, cond), getID(CoPItems.ENVIRONMENTAL_REJECT.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.MAGIC_REJECT.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern("1B1").pattern("BCB").pattern("2B2")
					.define('1', LCItems.VOID_EYE.get())
					.define('C', CoPItems.CHARM.get())
					.define('B', LCItems.RESONANT_FEATHER.get())
					.define('2', LCItems.FORCE_FIELD.get())
					.save(ConditionalRecipeWrapper.of(pvd, cond), getID(CoPItems.MAGIC_REJECT.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.OWNER_PROTECTION.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern("BAB").pattern("B1B").pattern("BAB")
					.define('1', CoPItems.CHARM.get())
					.define('A', Items.NETHER_STAR)
					.define('B', Items.END_ROD)
					.save(pvd, getID(CoPItems.OWNER_PROTECTION.get()));
		}

		// pandora
		{
			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.STABLE_BODY.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern(" C ").pattern("ABA").pattern(" A ")
					.define('A', Items.OBSIDIAN)
					.define('B', CoPItems.CHARM.get())
					.define('C', Items.CRYING_OBSIDIAN)
					.save(pvd, getID(CoPItems.STABLE_BODY.get()));


			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.ENDER_CHARM.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern(" C ").pattern("ABA").pattern(" A ")
					.define('A', Items.ENDER_EYE)
					.define('B', CoPItems.CHARM.get())
					.define('C', Items.CARVED_PUMPKIN)
					.save(pvd, getID(CoPItems.ENDER_CHARM.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.GOLDEN_HEART.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern(" A ").pattern("ABA").pattern(" A ")
					.define('A', Items.GOLD_INGOT)
					.define('B', CoPItems.CHARM.get())
					.save(pvd, getID(CoPItems.GOLDEN_HEART.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.BLESS_SNOW_WALKER.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern(" C ").pattern("ABA").pattern(" C ")
					.define('A', Items.LEATHER)
					.define('C', Items.SNOWBALL)
					.define('B', CoPItems.CHARM.get())
					.save(pvd, getID(CoPItems.BLESS_SNOW_WALKER.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.BLESS_LAVA_WALKER.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern(" C ").pattern("ABA").pattern(" A ")
					.define('A', Items.MAGMA_CREAM)
					.define('C', LCItems.HARD_ICE)
					.define('B', CoPItems.CHARM.get())
					.save(pvd, getID(CoPItems.BLESS_LAVA_WALKER.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.NIGHT_VISION_CHARM.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern(" C ").pattern("ABA").pattern(" A ")
					.define('A', new PotionIngredient(Potions.NIGHT_VISION))
					.define('C', LCItems.SOUL_FLAME)
					.define('B', CoPItems.CHARM.get())
					.save(pvd, getID(CoPItems.NIGHT_VISION_CHARM.get()));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.FIRE_REJECT.get())::unlockedBy, CoPItems.CHARM.get())
					.pattern(" A ").pattern("BCB").pattern(" A ")
					.define('A', LCItems.HARD_ICE.get())
					.define('B', Items.MAGMA_CREAM)
					.define('C', CoPItems.CHARM.get())
					.save(pvd, getID(CoPItems.FIRE_REJECT.get()));

		}

		// sets
		{
			var stack = CursePandoraUtil.allCurses(PandoraItems.PANDORA_NECKLACE.get());
			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PandoraItems.PANDORA_NECKLACE.get(), 1)::unlockedBy, CoPItems.CHARM.get())
					.pattern("ABA").pattern("BCB").pattern("ABA")
					.define('C', Items.NETHER_STAR)
					.define('B', CoPItems.CHARM.get())
					.define('A', Items.GOLD_INGOT)
					.save(e -> pvd.accept(new NBTRecipe(e, stack)), new ResourceLocation(CurseOfPandora.MODID, "seven_curses"));

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.ANGELIC_FEATHER.get(), 1)::unlockedBy, CoPItems.CHARM.get())
					.pattern("ABA").pattern("FCF").pattern("MBM")
					.define('C', CoPItems.CHARM.get())
					.define('B', LCItems.STORM_CORE)
					.define('A', LCItems.CAPTURED_WIND)
					.define('F', Items.FEATHER)
					.define('M', Items.PHANTOM_MEMBRANE)
					.save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.ANGELIC_WING.get(), 1)::unlockedBy, CoPItems.ANGELIC_FEATHER.get())
					.pattern("ABA").pattern("ACA").pattern("ABA")
					.define('C', CoPItems.CHARM)
					.define('B', CoPItems.ANGELIC_FEATHER)
					.define('A', Items.FEATHER).save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.ANGELIC_BLESS.get(), 1)::unlockedBy, CoPItems.ANGELIC_FEATHER.get())
					.pattern("DBD").pattern("BCB").pattern("AAA")
					.define('C', CoPItems.CHARM)
					.define('B', CoPItems.ANGELIC_FEATHER)
					.define('D', LCMats.TOTEMIC_GOLD.getIngot())
					.define('A', Items.FEATHER).save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.ANGELIC_DESCENT.get(), 1)::unlockedBy, CoPItems.ANGELIC_FEATHER.get())
					.pattern(" D ").pattern("BCB").pattern("B B")
					.define('C', CoPItems.CHARM)
					.define('B', CoPItems.ANGELIC_FEATHER)
					.define('D', LCItems.WARDEN_BONE_SHARD).save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.ANGELIC_PROTECTION.get(), 1)::unlockedBy, CoPItems.ANGELIC_FEATHER.get())
					.pattern(" B ").pattern("BCB").pattern("BAB")
					.define('C', CoPItems.CHARM)
					.define('B', CoPItems.ANGELIC_FEATHER)
					.define('A', LCItems.ENCHANTED_TOTEMIC_APPLE).save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.ANGELIC_PUNISHMENT.get(), 1)::unlockedBy, CoPItems.ANGELIC_FEATHER.get())
					.pattern("BDB").pattern("BCB").pattern("BAB")
					.define('C', CoPItems.CHARM)
					.define('B', CoPItems.ANGELIC_FEATHER)
					.define('D', LCItems.SUN_MEMBRANE)
					.define('A', LCItems.RESONANT_FEATHER).save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.HELLFIRE_RUNE.get(), 1)::unlockedBy, CoPItems.CHARM.get())
					.pattern("ABA").pattern("ECE").pattern("ADA")
					.define('C', CoPItems.CHARM)
					.define('D', Ingredient.of(LCItems.BLACKSTONE_CORE, Items.NETHER_STAR))
					.define('B', LCItems.SOUL_FLAME)
					.define('E', LCItems.EXPLOSION_SHARD)
					.define('A', Ingredient.of(LCItems.EXPLOSION_SHARD, Items.NETHERITE_SCRAP)).save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.HELLFIRE_SKULL.get(), 1)::unlockedBy, CoPItems.HELLFIRE_RUNE.get())
					.pattern(" F ").pattern("BCB").pattern(" S ")
					.define('C', CoPItems.CHARM)
					.define('B', CoPItems.HELLFIRE_RUNE)
					.define('S', Ingredient.of(Items.WITHER_SKELETON_SKULL, Items.SKELETON_SKULL))
					.define('F', LCItems.SOUL_FLAME).save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.HELLFIRE_REFORMATION.get(), 1)::unlockedBy, CoPItems.HELLFIRE_RUNE.get())
					.pattern("FXF").pattern("BCB").pattern("FBF")
					.define('C', CoPItems.CHARM)
					.define('B', CoPItems.HELLFIRE_RUNE)
					.define('F', LCItems.SOUL_FLAME)
					.define('X', Items.NETHER_STAR)
					.save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.EYE_OF_CURSED_SOULS.get(), 1)::unlockedBy, CoPItems.HELLFIRE_RUNE.get())
					.pattern("BXB").pattern("FCF").pattern("BFB")
					.define('C', CoPItems.CHARM)
					.define('B', CoPItems.HELLFIRE_RUNE)
					.define('F', LCItems.SOUL_FLAME)
					.define('X', LCItems.GUARDIAN_EYE)
					.save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CURSED_SOUL_CRYSTAL.get(), 1)::unlockedBy, CoPItems.HELLFIRE_RUNE.get())
					.pattern("BXB").pattern("BCB").pattern("FBF")
					.define('C', CoPItems.CHARM)
					.define('B', CoPItems.HELLFIRE_RUNE)
					.define('F', LCItems.CURSED_DROPLET)
					.define('X', LCItems.FORCE_FIELD)
					.save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CROWN_OF_DEMON.get(), 1)::unlockedBy, CoPItems.HELLFIRE_RUNE.get())
					.pattern("BXB").pattern("BCB").pattern("BFB")
					.define('C', CoPItems.CHARM)
					.define('B', CoPItems.HELLFIRE_RUNE)
					.define('F', LCItems.CURSED_DROPLET)
					.define('X', LCItems.GUARDIAN_EYE)
					.save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.SHADOW_FRAGMENT.get(), 1)::unlockedBy, CoPItems.CHARM.get())
					.pattern("ADA").pattern("ACA").pattern("ABA")
					.define('C', CoPItems.CHARM)
					.define('D', LCItems.VOID_EYE)
					.define('B', LCItems.WARDEN_BONE_SHARD)
					.define('A', LCItems.HARD_ICE).save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.SHADOW_CORE.get(), 1)::unlockedBy, CoPItems.CHARM.get())
					.pattern(" D ").pattern("ACA").pattern(" D ")
					.define('C', CoPItems.CHARM)
					.define('D', CoPItems.SHADOW_FRAGMENT)
					.define('A', LCItems.STORM_CORE).save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.SHADOW_CONVERGENCE.get(), 1)::unlockedBy, CoPItems.CHARM.get())
					.pattern(" D ").pattern("ACA").pattern("DTD")
					.define('C', CoPItems.CHARM)
					.define('D', CoPItems.SHADOW_FRAGMENT)
					.define('T', LCMats.TOTEMIC_GOLD.getIngot())
					.define('A', LCItems.CURSED_DROPLET).save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.SHADOW_CONSOLIDATION.get(), 1)::unlockedBy, CoPItems.CHARM.get())
					.pattern("DSD").pattern("ACA").pattern("DTD")
					.define('C', CoPItems.CHARM)
					.define('D', CoPItems.SHADOW_FRAGMENT)
					.define('S', LCItems.EMERALD)
					.define('T', LCItems.STORM_CORE)
					.define('A', LCItems.CURSED_DROPLET).save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.SHADOW_REFORMATION.get(), 1)::unlockedBy, CoPItems.CHARM.get())
					.pattern("TDT").pattern("DCD").pattern("DSD")
					.define('C', CoPItems.CHARM)
					.define('D', CoPItems.SHADOW_FRAGMENT)
					.define('S', LCItems.FORCE_FIELD)
					.define('T', LCItems.STORM_CORE).save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.VOID_OVERFLOW.get(), 1)::unlockedBy, CoPItems.CHARM.get())
					.pattern("DSD").pattern("DCD").pattern("DSD")
					.define('C', CoPItems.CHARM)
					.define('D', CoPItems.SHADOW_FRAGMENT)
					.define('S', LCItems.RESONANT_FEATHER).save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.WIND_THRUST.get(), 1)::unlockedBy, CoPItems.CHARM.get())
					.pattern("DDC").pattern("CCD").pattern("DCD")
					.define('C', CoPItems.CHARM)
					.define('D', LCItems.CAPTURED_WIND)
					.save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.EARTH_CRUSH.get(), 1)::unlockedBy, CoPItems.CHARM.get())
					.pattern("XIX").pattern("CDC").pattern("CXC")
					.define('C', CoPItems.CHARM)
					.define('D', LCItems.BLACKSTONE_CORE)
					.define('I', Items.ANVIL)
					.define('X', Ingredient.of(LCItems.EXPLOSION_SHARD, Items.NETHERITE_SCRAP))
					.save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.FLAMING_EXPLOSION.get(), 1)::unlockedBy, CoPItems.CHARM.get())
					.pattern("CFC").pattern("FDF").pattern("CFC")
					.define('C', CoPItems.CHARM)
					.define('F', LCItems.SOUL_FLAME)
					.define('D', LCItems.STORM_CORE)
					.save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.WAVING_SPELL.get(), 1)::unlockedBy, CoPItems.CHARM.get())
					.pattern("SCS").pattern("CDC").pattern("FCF")
					.define('C', CoPItems.CHARM)
					.define('S', Items.PRISMARINE_CRYSTALS)
					.define('F', Items.PRISMARINE_SHARD)
					.define('D', Items.CONDUIT)
					.save(pvd);

			unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CURSE_REDIRECTION.get(), 1)::unlockedBy, CoPItems.CHARM.get())
					.pattern("SCF").pattern("CDC").pattern("SCF")
					.define('C', CoPItems.CHARM)
					.define('S', new EnchantmentIngredient(Enchantments.BINDING_CURSE, 1))
					.define('F', new EnchantmentIngredient(Enchantments.VANISHING_CURSE, 1))
					.define('D', Items.NETHER_STAR)
					.save(pvd);
		}

		if (ModList.get().isLoaded(L2Hostility.MODID)) {
			{
				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM.get(), 2)::unlockedBy, CoPItems.CHARM.get())
						.pattern("AAA").pattern("ABA").pattern("AAA")
						.define('A', LHItems.MIRACLE_POWDER.get())
						.define('B', CoPItems.CHARM.get())
						.save(ConditionalRecipeWrapper.mod(pvd, L2Hostility.MODID), getID(CoPItems.CHARM.get(), "_renew"));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM.get(), 2)::unlockedBy, CoPItems.CHARM.get())
						.pattern("AAA").pattern("ABA").pattern("AAA")
						.define('A', LHItems.MIRACLE_POWDER.get())
						.define('B', LHItems.MIRACLE_INGOT.get())
						.save(ConditionalRecipeWrapper.mod(pvd, L2Hostility.MODID), getID(CoPItems.CHARM.get(), "_craft"));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_HEALTH.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("AAA").pattern("ABA").pattern("AAA")
						.define('A', LHTraits.TANK.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.save(ConditionalRecipeWrapper.mod(pvd, L2Hostility.MODID), getID(CoPItems.CHARM_HEALTH.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_ARMOR.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.TANK.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.PROTECTION.get().asItem())
						.save(ConditionalRecipeWrapper.mod(pvd, L2Hostility.MODID), getID(CoPItems.CHARM_ARMOR.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_SPEED.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("AAA").pattern("ABA").pattern("AAA")
						.define('A', LHTraits.SPEEDY.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.save(ConditionalRecipeWrapper.mod(pvd, L2Hostility.MODID), getID(CoPItems.CHARM_SPEED.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_DAMAGE.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.FIERY.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.STRIKE.get().asItem())
						.save(ConditionalRecipeWrapper.mod(pvd, L2Hostility.MODID), getID(CoPItems.CHARM_DAMAGE.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_HEAVY.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.GRAVITY.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.SLOWNESS.get().asItem())
						.save(ConditionalRecipeWrapper.mod(pvd, L2Hostility.MODID), getID(CoPItems.CHARM_HEAVY.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_BOW.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.WEAKNESS.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.SHULKER.get().asItem())
						.save(ConditionalRecipeWrapper.mod(pvd, L2Hostility.MODID), getID(CoPItems.CHARM_BOW.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_CRIT.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.MOONWALK.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.LEVITATION.get().asItem())
						.save(ConditionalRecipeWrapper.mod(pvd, L2Hostility.MODID), getID(CoPItems.CHARM_CRIT.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_ACCURACY.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.FREEZING.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.REFLECT.get().asItem())
						.save(ConditionalRecipeWrapper.mod(pvd, L2Hostility.MODID), getID(CoPItems.CHARM_ACCURACY.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_PROTECTION.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.ADAPTIVE.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.PROTECTION.get().asItem())
						.save(ConditionalRecipeWrapper.mod(pvd, L2Hostility.MODID), getID(CoPItems.CHARM_PROTECTION.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_MAGIC.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.KILLER_AURA.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.DEMENTOR.get().asItem())
						.save(ConditionalRecipeWrapper.mod(pvd, L2Hostility.MODID), getID(CoPItems.CHARM_MAGIC.get()));

				unlock(pvd, ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CoPItems.CHARM_EXPLOSION.get())::unlockedBy, CoPItems.CHARM.get())
						.pattern("CAC").pattern("ABA").pattern("CAC")
						.define('A', LHTraits.SHULKER.get().asItem())
						.define('B', CoPItems.CHARM.get())
						.define('C', LHTraits.GRENADE.get().asItem())
						.save(ConditionalRecipeWrapper.mod(pvd, L2Hostility.MODID), getID(CoPItems.CHARM_EXPLOSION.get()));
			}
		}

	}

	private static void convert(RegistrateRecipeProvider pvd, Item in, Item out, int count) {
		(unlock(pvd, new BurntRecipeBuilder(Ingredient.of(in), out.getDefaultInstance(), count)::unlockedBy, in)).save(pvd, getID(in));
	}

	private static void convert(RegistrateRecipeProvider pvd, Ingredient ing, Item out, int count, String id) {
		(unlock(pvd, new BurntRecipeBuilder(ing, out.getDefaultInstance(), count)::unlockedBy, ing.getItems()[0].getItem())).save(pvd, getID(out).withSuffix(id));
	}

	public static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

	private static ResourceLocation getID(Item item) {
		return new ResourceLocation(CurseOfPandora.MODID, CoPRecipeGen.currentFolder + ForgeRegistries.ITEMS.getKey(item).getPath());
	}

	@SuppressWarnings("ConstantConditions")
	private static ResourceLocation getID(Item item, String suffix) {
		return new ResourceLocation(CurseOfPandora.MODID, CoPRecipeGen.currentFolder + ForgeRegistries.ITEMS.getKey(item).getPath() + suffix);
	}


}
