package dev.xkmc.curseofpandora.init.registrate;

import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.xkmc.curseofpandora.content.pandora.*;
import dev.xkmc.curseofpandora.content.reality.*;
import dev.xkmc.curseofpandora.content.sets.abyss.*;
import dev.xkmc.curseofpandora.content.sets.angle.*;
import dev.xkmc.curseofpandora.content.sets.elemental.*;
import dev.xkmc.curseofpandora.content.sets.hell.*;
import dev.xkmc.curseofpandora.content.sets.shadow.*;
import dev.xkmc.curseofpandora.content.weapon.AbyssalEdge;
import dev.xkmc.curseofpandora.content.weapon.AngelicJudgement;
import dev.xkmc.curseofpandora.content.weapon.CursedKarma;
import dev.xkmc.curseofpandora.content.weapon.DoomStar;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPTagGen;
import dev.xkmc.l2complements.content.feature.CurioFeaturePredicate;
import dev.xkmc.l2complements.content.feature.EntityFeature;
import dev.xkmc.l2complements.content.item.curios.DescCurioItem;
import dev.xkmc.l2complements.init.data.TagGen;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.pandora.init.data.PandoraTagGen;
import dev.xkmc.pandora.init.registrate.PandoraItems;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;

import static dev.xkmc.curseofpandora.init.CurseOfPandora.REGISTRATE;

public class CoPItems {

	public static final ItemEntry<Item> CHARM, MINI_BEACON, MINI_BEACON_BASE,
			ANGELIC_FEATHER, HELLFIRE_RUNE, SHADOW_FRAGMENT,
			BARBARIC_EDGE, SPELLBOUND_ORB, ABYSS_CRYSTAL, SCULK_CRYSTAL;
	public static final ItemEntry<EnchDescItem> EXPLOSION_REJECT, MAGIC_REJECT,
			ENVIRONMENTAL_REJECT, PROJECTILE_REJECT, OWNER_PROTECTION;
	public static final ItemEntry<EffectRefreshItem> MINI_BEACON_SPEED, MINI_BEACON_HASTE, MINI_BEACON_JUMP,
			MINI_BEACON_ATTACK, MINI_BEACON_RESISTANCE, MINI_BEACON_REGEN, NIGHT_VISION_CHARM;
	public static final ItemEntry<PiglinShinnyCharm> GOLDEN_HEART;
	public static final ItemEntry<FireRejectItem> FIRE_REJECT;
	public static final ItemEntry<EnderMaskCharm> ENDER_CHARM;
	public static final ItemEntry<SnowWalkerCharm> BLESS_SNOW_WALKER;
	public static final ItemEntry<StabilityCharm> STABLE_BODY;
	public static final ItemEntry<DescCurioItem> BLESS_LAVA_WALKER;
	public static final ItemEntry<AttributeItem> CHARM_HEALTH, CHARM_ARMOR, CHARM_SPEED,
			CHARM_DAMAGE, CHARM_HEAVY, CHARM_ACCURACY, CHARM_CRIT, CHARM_BOW, CHARM_PROTECTION,
			CHARM_MAGIC, CHARM_EXPLOSION;

	public static final ItemEntry<CurseOfInertiaItem> CURSE_OF_INERTIA;
	public static final ItemEntry<CurseOfProximityItem> CURSE_OF_PROXIMITY;
	public static final ItemEntry<CurseOfFleshItem> CURSE_OF_FLESH;
	public static final ItemEntry<CurseOfMetabolismItem> CURSE_OF_METABOLISM;
	public static final ItemEntry<CurseOfTensionItem> CURSE_OF_TENSION;
	public static final ItemEntry<CurseOfPrudenceItem> CURSE_OF_PRUDENCE;
	public static final ItemEntry<CurseOfSpellItem> CURSE_OF_SPELL;
	public static final ItemEntry<AngelicWing> ANGELIC_WING;
	public static final ItemEntry<AngelicBless> ANGELIC_BLESS;
	public static final ItemEntry<AngelicDescent> ANGELIC_DESCENT;
	public static final ItemEntry<AngelicProtection> ANGELIC_PROTECTION;
	public static final ItemEntry<AngelicPunishment> ANGELIC_PUNISHMENT;
	public static final ItemEntry<HellfireSkull> HELLFIRE_SKULL;
	public static final ItemEntry<HellfireReformation> HELLFIRE_REFORMATION;
	public static final ItemEntry<EyeOfCursedSoul> EYE_OF_CURSED_SOULS;
	public static final ItemEntry<CursedSoulCrystal> CURSED_SOUL_CRYSTAL;
	public static final ItemEntry<CrownOfDemon> CROWN_OF_DEMON;
	public static final ItemEntry<ShadowCore> SHADOW_CORE;
	public static final ItemEntry<ShadowConvergence> SHADOW_CONVERGENCE;
	public static final ItemEntry<ShadowConsolidation> SHADOW_CONSOLIDATION;
	public static final ItemEntry<ShadowReformation> SHADOW_REFORMATION;
	public static final ItemEntry<VoidOverflow> VOID_OVERFLOW;
	public static final ItemEntry<AbyssalTreasure> ABYSSAL_TREASURE;
	public static final ItemEntry<AbyssalWatcher> ABYSSAL_WATCHER;
	public static final ItemEntry<AbyssalShell> ABYSSAL_SHELL;
	public static final ItemEntry<AbyssalCrown> ABYSSAL_CROWN;
	public static final ItemEntry<AbyssalWill> ABYSSAL_WILL;
	public static final ItemEntry<WindThrust> WIND_THRUST;
	public static final ItemEntry<EarthCrush> EARTH_CRUSH;
	public static final ItemEntry<FlamingExplosion> FLAMING_EXPLOSION;
	public static final ItemEntry<WavingSpell> WAVING_SPELL;
	public static final ItemEntry<CurseRedirection> CURSE_REDIRECTION;

	public static final ItemEntry<AngelicJudgement> ANGELIC_JUDGEMENT;
	public static final ItemEntry<DoomStar> DOOM_STAR;
	public static final ItemEntry<CursedKarma> CURSED_KARMA;
	public static final ItemEntry<AbyssalEdge> ABYSSAL_EDGE;

	static {
		REGISTRATE.defaultCreativeTab(PandoraItems.TAB.getKey());

		// curse
		{

			CURSE_OF_INERTIA = item("curse_of_inertia", CurseOfInertiaItem::new)
					.tag(CoPTagGen.CURSE).register();

			CURSE_OF_PROXIMITY = item("curse_of_proximity", CurseOfProximityItem::new)
					.tag(CoPTagGen.CURSE).register();

			CURSE_OF_FLESH = item("curse_of_flesh", CurseOfFleshItem::new)
					.tag(CoPTagGen.CURSE).register();

			CURSE_OF_METABOLISM = item("curse_of_metabolism", CurseOfMetabolismItem::new)
					.tag(CoPTagGen.CURSE).register();

			CURSE_OF_TENSION = item("curse_of_tension", CurseOfTensionItem::new)
					.tag(CoPTagGen.CURSE).register();

			CURSE_OF_PRUDENCE = item("curse_of_prudence", CurseOfPrudenceItem::new)
					.tag(CoPTagGen.CURSE).register();

			CURSE_OF_SPELL = item("curse_of_spell", CurseOfSpellItem::new)
					.tag(CoPTagGen.CURSE).register();

		}

		// ingredient
		{
			CHARM = ingredient("plain_charm", Item::new).register();
			MINI_BEACON_BASE = ingredient("mini_beacon_base", p -> new Item(p.fireResistant())).register();
			MINI_BEACON = ingredient("mini_beacon", Item::new).tag(CoPTagGen.PANDORA_BASE).register();
			SPELLBOUND_ORB = ingredient("spellbound_orb", Item::new).register();
			BARBARIC_EDGE = ingredient("barbaric_edge", Item::new).register();
			SCULK_CRYSTAL = ingredient("sculk_crystal", Item::new).register();
			ANGELIC_FEATHER = ingredient("angelic_feather", Item::new).register();
			HELLFIRE_RUNE = ingredient("hellfire_rune", Item::new).register();
			SHADOW_FRAGMENT = ingredient("shadow_fragment", Item::new).register();
			ABYSS_CRYSTAL = ingredient("abyss_crystal", Item::new).register();
		}

		// attributes
		{
			CHARM_HEALTH = item("charm_of_health", p -> new AttributeItem(p,
					AttributeItem.add(() -> Attributes.MAX_HEALTH, "charm_of_health", CoPConfig.COMMON.attr.charmOfHealth::get)))
					.tag(CoPTagGen.ATTR, PandoraTagGen.ALLOW_DUPLICATE).register();

			CHARM_ARMOR = item("charm_of_armor", p -> new AttributeItem(p,
					AttributeItem.add(() -> Attributes.ARMOR, "charm_of_armor", CoPConfig.COMMON.attr.charmOfArmor::get),
					AttributeItem.add(() -> Attributes.ARMOR_TOUGHNESS, "charm_of_armor", CoPConfig.COMMON.attr.charmOfArmorToughness::get)
			)).tag(CoPTagGen.ATTR, PandoraTagGen.ALLOW_DUPLICATE).register();

			CHARM_SPEED = item("charm_of_speed", p -> new AttributeItem(p,
					AttributeItem.multBase(() -> Attributes.MOVEMENT_SPEED, "charm_of_speed", CoPConfig.COMMON.attr.charmOfSpeed::get)))
					.tag(CoPTagGen.ATTR, PandoraTagGen.ALLOW_DUPLICATE).register();

			CHARM_DAMAGE = item("charm_of_damage", p -> new AttributeItem(p,
					AttributeItem.multBase(() -> Attributes.ATTACK_DAMAGE, "charm_of_damage", CoPConfig.COMMON.attr.charmOfDamage::get)))
					.tag(CoPTagGen.ATTR, PandoraTagGen.ALLOW_DUPLICATE).register();

			CHARM_CRIT = item("charm_of_critical", p -> new AttributeItem(p,
					AttributeItem.add(L2DamageTracker.CRIT_DMG::get, "charm_of_critical", CoPConfig.COMMON.attr.charmOfCritical::get)))
					.tag(CoPTagGen.ATTR, PandoraTagGen.ALLOW_DUPLICATE).register();

			CHARM_BOW = item("charm_of_archery", p -> new AttributeItem(p,
					AttributeItem.add(L2DamageTracker.BOW_STRENGTH::get, "charm_of_archery", CoPConfig.COMMON.attr.charmOfArchery::get)))
					.tag(CoPTagGen.ATTR, PandoraTagGen.ALLOW_DUPLICATE).register();

			CHARM_PROTECTION = item("charm_of_protection", p -> new AttributeItem(p,
					AttributeItem.multBase(CoPAttrs.REDUCTION, "charm_of_protection", () -> -CoPConfig.COMMON.attr.charmOfProtection.get())))
					.tag(CoPTagGen.ATTR, PandoraTagGen.ALLOW_DUPLICATE).register();

			CHARM_MAGIC = item("charm_of_magic", p -> new AttributeItem(p,
					AttributeItem.add(L2DamageTracker.MAGIC_FACTOR::get, "charm_of_magic", CoPConfig.COMMON.attr.charmOfMagic::get)))
					.tag(CoPTagGen.ATTR, PandoraTagGen.ALLOW_DUPLICATE).register();

			CHARM_EXPLOSION = item("charm_of_explosion", p -> new AttributeItem(p,
					AttributeItem.add(L2DamageTracker.EXPLOSION_FACTOR::get, "charm_of_explosion", CoPConfig.COMMON.attr.charmOfExplosion::get)))
					.tag(CoPTagGen.ATTR, PandoraTagGen.ALLOW_DUPLICATE).register();

			CHARM_HEAVY = item("charm_of_heavy_weapon", p -> new AttributeItem(p,
					AttributeItem.multBase(() -> Attributes.ATTACK_DAMAGE, "charm_of_heavy_weapon", CoPConfig.COMMON.attr.charmOfHeavyWeapon::get),
					AttributeItem.add(() -> Attributes.ATTACK_SPEED, "charm_of_heavy_weapon", () -> -CoPConfig.COMMON.attr.charmOfHeavyWeaponSlow.get())
			)).tag(CoPTagGen.ATTR).register();

			CHARM_ACCURACY = item("charm_of_accuracy", p -> new AttributeItem(p,
					AttributeItem.add(L2DamageTracker.CRIT_RATE::get, "charm_of_accuracy", CoPConfig.COMMON.attr.charmOfAccuracy::get),
					AttributeItem.add(() -> Attributes.ATTACK_SPEED, "charm_of_accuracy", () -> -CoPConfig.COMMON.attr.charmOfAccuracySlow.get())
			)).tag(CoPTagGen.ATTR).register();

		}

		//sets
		{

			ANGELIC_WING = item("angelic_wing", AngelicWing::new)
					.tag(CoPTagGen.ANGELIC).register();
			ANGELIC_BLESS = item("angelic_bless", AngelicBless::new)
					.tag(CoPTagGen.ANGELIC).register();
			ANGELIC_DESCENT = item("angelic_descent", AngelicDescent::new)
					.tag(CoPTagGen.ANGELIC).register();
			ANGELIC_PROTECTION = item("angelic_protection", AngelicProtection::new)
					.tag(CoPTagGen.ANGELIC, TagGen.TOTEM).register();
			ANGELIC_PUNISHMENT = item("angelic_punishment", AngelicPunishment::new)
					.tag(CoPTagGen.ANGELIC).register();

			HELLFIRE_SKULL = item("hellfire_skull", HellfireSkull::new)
					.tag(CoPTagGen.HELL).register();
			HELLFIRE_REFORMATION = item("hellfire_reformation", HellfireReformation::new)
					.tag(CoPTagGen.HELL).register();
			EYE_OF_CURSED_SOULS = item("eye_of_cursed_souls", EyeOfCursedSoul::new)
					.tag(CoPTagGen.HELL).lang("Eye of Cursed Souls").register();
			CURSED_SOUL_CRYSTAL = item("cursed_soul_crystal", CursedSoulCrystal::new)
					.tag(CoPTagGen.HELL).register();
			CROWN_OF_DEMON = item("crown_of_demon", CrownOfDemon::new)
					.tag(CoPTagGen.HELL).lang("Crown of Demon").register();

			SHADOW_CORE = item("shadow_core", ShadowCore::new)
					.tag(CoPTagGen.SHADOW).register();
			SHADOW_CONVERGENCE = item("shadow_convergence", ShadowConvergence::new)
					.tag(CoPTagGen.SHADOW).register();
			SHADOW_CONSOLIDATION = item("shadow_consolidation", ShadowConsolidation::new)
					.tag(CoPTagGen.SHADOW).register();
			SHADOW_REFORMATION = item("shadow_reformation", ShadowReformation::new)
					.tag(CoPTagGen.SHADOW).register();
			VOID_OVERFLOW = item("void_overflow", VoidOverflow::new)
					.tag(CoPTagGen.SHADOW).register();

			ABYSSAL_TREASURE = item("abyssal_treasure", AbyssalTreasure::new)
					.tag(CoPTagGen.ABYSSAL).register();
			ABYSSAL_WATCHER = item("abyssal_watcher", AbyssalWatcher::new)
					.tag(CoPTagGen.ABYSSAL).register();
			ABYSSAL_SHELL = item("abyssal_shell", AbyssalShell::new)
					.tag(CoPTagGen.ABYSSAL).register();
			ABYSSAL_CROWN = item("abyssal_crown", AbyssalCrown::new)
					.tag(CoPTagGen.ABYSSAL).register();
			ABYSSAL_WILL = item("abyssal_will", AbyssalWill::new)
					.tag(CoPTagGen.ABYSSAL).register();


			WIND_THRUST = item("wind_thrust", WindThrust::new)
					.tag(CoPTagGen.ELEMENTAL).register();
			EARTH_CRUSH = item("earth_crush", EarthCrush::new)
					.tag(CoPTagGen.ELEMENTAL).register();
			FLAMING_EXPLOSION = item("flaming_explosion", FlamingExplosion::new)
					.tag(CoPTagGen.ELEMENTAL).register();
			WAVING_SPELL = item("waving_spell", WavingSpell::new)
					.tag(CoPTagGen.ELEMENTAL).register();
			CURSE_REDIRECTION = item("curse_redirection", CurseRedirection::new)
					.tag(CoPTagGen.ELEMENTAL).register();
		}

		// enchs
		{

			STABLE_BODY = descItem("orb_of_stability", "Orb of Stability", StabilityCharm::new,
					"When attacked, you won't be knocked back, and your screen won't shake")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			GOLDEN_HEART = item("golden_heart",
					p -> new PiglinShinnyCharm(p, LCEnchantments.SHINNY::get))
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			ENDER_CHARM = item("charm_of_calmness",
					p -> new EnderMaskCharm(p, LCEnchantments.ENDER_MASK::get))
					.lang("Charm of Calmness")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			BLESS_SNOW_WALKER = item("bless_of_snow_walker",
					p -> new SnowWalkerCharm(p, LCEnchantments.SNOW_WALKER::get))
					.lang("Bless of Snow Walker")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			BLESS_LAVA_WALKER = descItem("bless_of_lava_walker", "Bless of Lava Walker",
					"Allows you to walk on lava")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			NIGHT_VISION_CHARM = item("charm_of_night_vision",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.NIGHT_VISION, 440, 0, true, true)))
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			FIRE_REJECT = item("orb_of_fire_rejection", FireRejectItem::new)
					.lang("Orb of Fire Rejection")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			OWNER_PROTECTION = item("orb_of_master",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_MATES::get))
					.lang("Orb of Master")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			ENVIRONMENTAL_REJECT = item("orb_of_nature",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_ENVIRONMENT::get))
					.lang("Orb of Nature")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			EXPLOSION_REJECT = item("orb_of_explosion_rejection",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_EXPLOSION::get))
					.lang("Orb of Explosion Rejection")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			PROJECTILE_REJECT = item("orb_of_projectile_rejection",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_PROJECTILE::get))
					.lang("Orb of Projectile Rejection")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			MAGIC_REJECT = item("orb_of_magic_rejection",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_MAGIC::get))
					.lang("Orb of Magic Rejection")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();
		}

		// beacon
		{


			MINI_BEACON_SPEED = item("mini_beacon_speed",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.MOVEMENT_SPEED, 40, 1, true, true)))
					.lang("Mini Beacon: Speed")
					.tag(CoPTagGen.BEACON).register();

			MINI_BEACON_HASTE = item("mini_beacon_haste",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.DIG_SPEED, 40, 1, true, true)))
					.lang("Mini Beacon: Haste")
					.tag(CoPTagGen.BEACON).register();

			MINI_BEACON_ATTACK = item("mini_beacon_strength",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.DAMAGE_BOOST, 40, 1, true, true)))
					.lang("Mini Beacon: Strength")
					.tag(CoPTagGen.BEACON).register();

			MINI_BEACON_JUMP = item("mini_beacon_jump",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.JUMP, 40, 1, true, true)))
					.lang("Mini Beacon: Jump Boost")
					.tag(CoPTagGen.BEACON).register();

			MINI_BEACON_RESISTANCE = item("mini_beacon_resistance",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.DAMAGE_RESISTANCE, 40, 1, true, true)))
					.lang("Mini Beacon: Resistance")
					.tag(CoPTagGen.BEACON).register();

			MINI_BEACON_REGEN = item("mini_beacon_regen",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.REGENERATION, 60, 1, true, true)))
					.lang("Mini Beacon: Regeneration")
					.tag(CoPTagGen.BEACON).register();

		}

		// sword
		{
			ANGELIC_JUDGEMENT = weapon("angelic_judgement", p -> new AngelicJudgement(p.fireResistant()))
					.tag(ItemTags.SWORDS).register();

			DOOM_STAR = weapon("doom_star", p -> new DoomStar(p.fireResistant()))
					.tag(ItemTags.SWORDS).register();

			CURSED_KARMA = weapon("cursed_karma", p -> new CursedKarma(p.fireResistant()))
					.tag(ItemTags.SWORDS).register();

			ABYSSAL_EDGE = weapon("abyssal_edge", p -> new AbyssalEdge(p.fireResistant()))
					.tag(ItemTags.SWORDS).register();
		}

	}


	public static <T extends Item> ItemBuilder<T, L2Registrate> ingredient(String id, NonNullFunction<Item.Properties, T> factory) {
		return REGISTRATE.item(id, factory)
				.model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("item/mat/" + id)));
	}

	public static <T extends Item> ItemBuilder<T, L2Registrate> weapon(String id, NonNullFunction<Item.Properties, T> factory) {
		return REGISTRATE.item(id, factory)
				.model((ctx, pvd) -> pvd.handheld(ctx, pvd.modLoc("item/weapon/" + id)));
	}

	public static <T extends Item> ItemBuilder<T, L2Registrate> item(String id, NonNullFunction<Item.Properties, T> factory) {
		return REGISTRATE.item(id, factory)
				.model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("item/pandora/" + id)));
	}

	public static ItemBuilder<DescCurioItem, L2Registrate> descItem(String id, String name, String desc) {
		REGISTRATE.addRawLang("item." + CurseOfPandora.MODID + "." + id + ".desc", desc);
		return item(id, DescCurioItem::new)
				.lang(name);
	}

	public static <T extends Item> ItemBuilder<T, L2Registrate> descItem(String id, String name, NonNullFunction<Item.Properties, T> sup, String desc) {
		REGISTRATE.addRawLang("item." + CurseOfPandora.MODID + "." + id + ".desc", desc);
		return item(id, sup).lang(name);
	}

	public static void register() {
		EntityFeature.STABLE_BODY.add(new CurioFeaturePredicate(STABLE_BODY::get));
		EntityFeature.PROJECTILE_REJECT.add(new CurioFeaturePredicate(CoPItems.PROJECTILE_REJECT::get));
		EntityFeature.EXPLOSION_REJECT.add(new CurioFeaturePredicate(CoPItems.EXPLOSION_REJECT::get));
		EntityFeature.FIRE_REJECT.add(new CurioFeaturePredicate(CoPItems.FIRE_REJECT::get));
		EntityFeature.ENVIRONMENTAL_REJECT.add(new CurioFeaturePredicate(CoPItems.ENVIRONMENTAL_REJECT::get));
		EntityFeature.MAGIC_REJECT.add(new CurioFeaturePredicate(CoPItems.MAGIC_REJECT::get));
		EntityFeature.OWNER_PROTECTION.add(new CurioFeaturePredicate(CoPItems.OWNER_PROTECTION::get));
		EntityFeature.LAVA_WALKER.add(new CurioFeaturePredicate(CoPItems.BLESS_LAVA_WALKER::get));
	}

}
