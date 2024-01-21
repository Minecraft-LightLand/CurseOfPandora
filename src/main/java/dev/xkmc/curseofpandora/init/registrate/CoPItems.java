package dev.xkmc.curseofpandora.init.registrate;

import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.xkmc.curseofpandora.content.pandora.*;
import dev.xkmc.curseofpandora.content.reality.*;
import dev.xkmc.curseofpandora.content.sets.angle.*;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;

import static dev.xkmc.curseofpandora.init.CurseOfPandora.REGISTRATE;

public class CoPItems {

	public static final ItemEntry<Item> CHARM, MINI_BEACON, MINI_BEACON_BASE;
	public static final ItemEntry<EnchDescItem> FIRE_REJECT, EXPLOSION_REJECT, MAGIC_REJECT,
			ENVIRONMENTAL_REJECT, PROJECTILE_REJECT, OWNER_PROTECTION, STABLE_BODY;
	public static final ItemEntry<EffectRefreshItem> MINI_BEACON_SPEED, MINI_BEACON_HASTE, MINI_BEACON_JUMP,
			MINI_BEACON_ATTACK, MINI_BEACON_RESISTANCE, MINI_BEACON_REGEN, NIGHT_VISION_CHARM;
	public static final ItemEntry<PiglinShinnyCharm> GOLDEN_HEART;
	public static final ItemEntry<EnderMaskCharm> ENDER_CHARM;
	public static final ItemEntry<SnowWalkerCharm> BLESS_SNOW_WALKER;
	public static final ItemEntry<DescCurioItem> BLESS_LAVA_WALKER;
	public static final ItemEntry<AttributeItem> CHARM_HEALTH, CHARM_ARMOR, CHARM_SPEED,
			CHARM_DAMAGE, CHARM_HEAVY, CHARM_ACCURACY, CHARM_CRIT, CHARM_BOW;

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


	static {
		REGISTRATE.defaultCreativeTab(PandoraItems.TAB.getKey());

		{

			CHARM = item("plain_charm", Item::new)
					.register();

			MINI_BEACON_BASE = item("mini_beacon_base", p -> new Item(p.fireResistant()))
					.register();

			MINI_BEACON = item("mini_beacon", Item::new)
					.tag(CoPTagGen.PANDORA_BASE)
					.register();

			FIRE_REJECT = item("orb_of_fire_rejection",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_FIRE::get))
					.lang("Orb of Fire Rejection")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			EXPLOSION_REJECT = item("orb_of_explosion_rejection",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_EXPLOSION::get))
					.lang("Orb of Explosion Rejection")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			MAGIC_REJECT = item("orb_of_magic_rejection",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_MAGIC::get))
					.lang("Orb of Magic Rejection")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			PROJECTILE_REJECT = item("orb_of_projectile_rejection",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_PROJECTILE::get))
					.lang("Orb of Projectile Rejection")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			ENVIRONMENTAL_REJECT = item("orb_of_nature",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_ENVIRONMENT::get))
					.lang("Orb of Nature")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			OWNER_PROTECTION = item("orb_of_master",
					p -> new EnchDescItem(p, LCEnchantments.ENCH_MATES::get))
					.lang("Orb of Master")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			STABLE_BODY = item("orb_of_stability",
					p -> new EnchDescItem(p, LCEnchantments.STABLE_BODY::get))
					.lang("Orb of Stability")
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
					"Allows you to walk on lava");

			MINI_BEACON_SPEED = item("mini_beacon_speed",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.MOVEMENT_SPEED, 40, 1, true, true)))
					.lang("Mini Beacon: Speed")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			MINI_BEACON_HASTE = item("mini_beacon_haste",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.DIG_SPEED, 40, 1, true, true)))
					.lang("Mini Beacon: Haste")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			MINI_BEACON_ATTACK = item("mini_beacon_strength",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.DAMAGE_BOOST, 40, 1, true, true)))
					.lang("Mini Beacon: Strength")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			MINI_BEACON_JUMP = item("mini_beacon_jump",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.JUMP, 40, 1, true, true)))
					.lang("Mini Beacon: Jump Boost")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			MINI_BEACON_RESISTANCE = item("mini_beacon_resistance",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.DAMAGE_RESISTANCE, 40, 1, true, true)))
					.lang("Mini Beacon: Resistance")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			MINI_BEACON_REGEN = item("mini_beacon_regen",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.REGENERATION, 60, 1, true, true)))
					.lang("Mini Beacon: Regeneration")
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

			NIGHT_VISION_CHARM = item("charm_of_night_vision",
					p -> new EffectRefreshItem(p, () -> new MobEffectInstance(
							MobEffects.NIGHT_VISION, 440, 0, true, true)))
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();

		}

		// attributes

		{
			CHARM_HEALTH = item("charm_of_health", p -> new AttributeItem(p,
					(uuid, map) -> map.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "charm_of_health",
							2, AttributeModifier.Operation.ADDITION))))
					.tag(PandoraTagGen.PANDORA_SLOT, PandoraTagGen.ALLOW_DUPLICATE, CoPTagGen.PANDORA_BASE).register();

			CHARM_ARMOR = item("charm_of_armor", p -> new AttributeItem(p,
					(uuid, map) -> map.put(Attributes.ARMOR, new AttributeModifier(uuid, "charm_of_armor",
							2, AttributeModifier.Operation.ADDITION)),
					(uuid, map) -> map.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "charm_of_armor",
							1, AttributeModifier.Operation.ADDITION))
			)).tag(PandoraTagGen.PANDORA_SLOT, PandoraTagGen.ALLOW_DUPLICATE, CoPTagGen.PANDORA_BASE).register();

			CHARM_SPEED = item("charm_of_speed", p -> new AttributeItem(p,
					(uuid, map) -> map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "charm_of_speed",
							0.05, AttributeModifier.Operation.MULTIPLY_BASE))))
					.tag(PandoraTagGen.PANDORA_SLOT, PandoraTagGen.ALLOW_DUPLICATE, CoPTagGen.PANDORA_BASE).register();

			CHARM_DAMAGE = item("charm_of_damage", p -> new AttributeItem(p,
					(uuid, map) -> map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "charm_of_damage",
							0.05, AttributeModifier.Operation.MULTIPLY_BASE))))
					.tag(PandoraTagGen.PANDORA_SLOT, PandoraTagGen.ALLOW_DUPLICATE, CoPTagGen.PANDORA_BASE).register();

			CHARM_CRIT = item("charm_of_critical", p -> new AttributeItem(p,
					(uuid, map) -> map.put(L2DamageTracker.CRIT_DMG.get(), new AttributeModifier(uuid, "charm_of_critical",
							0.05, AttributeModifier.Operation.ADDITION))))
					.tag(PandoraTagGen.PANDORA_SLOT, PandoraTagGen.ALLOW_DUPLICATE, CoPTagGen.PANDORA_BASE).register();

			CHARM_BOW = item("charm_of_archery", p -> new AttributeItem(p,
					(uuid, map) -> map.put(L2DamageTracker.BOW_STRENGTH.get(), new AttributeModifier(uuid, "charm_of_archery",
							0.05, AttributeModifier.Operation.ADDITION))))
					.tag(PandoraTagGen.PANDORA_SLOT, PandoraTagGen.ALLOW_DUPLICATE, CoPTagGen.PANDORA_BASE).register();

			CHARM_HEAVY = item("charm_of_heavy_weapon", p -> new AttributeItem(p,
					(uuid, map) -> map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "charm_of_heavy_weapon",
							1, AttributeModifier.Operation.MULTIPLY_BASE)),
					(uuid, map) -> map.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "charm_of_heavy_weapon",
							-2, AttributeModifier.Operation.ADDITION))
			)).tag(PandoraTagGen.PANDORA_SLOT, PandoraTagGen.ALLOW_DUPLICATE, CoPTagGen.PANDORA_BASE).register();

			CHARM_ACCURACY = item("charm_of_accuracy", p -> new AttributeItem(p,
					(uuid, map) -> map.put(L2DamageTracker.CRIT_RATE.get(), new AttributeModifier(uuid, "charm_of_accuracy",
							0.2, AttributeModifier.Operation.ADDITION)),
					(uuid, map) -> map.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "charm_of_accuracy",
							-0.5, AttributeModifier.Operation.ADDITION))
			)).tag(PandoraTagGen.PANDORA_SLOT, PandoraTagGen.ALLOW_DUPLICATE, CoPTagGen.PANDORA_BASE).register();


		}

		// curse
		{

			CURSE_OF_INERTIA = item("curse_of_inertia", CurseOfInertiaItem::new)
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.CURSE).register();

			CURSE_OF_PROXIMITY = item("curse_of_proximity", CurseOfProximityItem::new)
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.CURSE).register();

			CURSE_OF_FLESH = item("curse_of_flesh", CurseOfFleshItem::new)
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.CURSE).register();

			CURSE_OF_METABOLISM = item("curse_of_metabolism", CurseOfMetabolismItem::new)
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.CURSE).register();

			CURSE_OF_TENSION = item("curse_of_tension", CurseOfTensionItem::new)
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.CURSE).register();

			CURSE_OF_PRUDENCE = item("curse_of_prudence", CurseOfPrudenceItem::new)
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.CURSE).register();

			CURSE_OF_SPELL = item("curse_of_spell", CurseOfSpellItem::new)
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.CURSE).register();

		}

		//sets

		{
			ANGELIC_WING = item("angelic_wing", AngelicWing::new)
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();
			ANGELIC_BLESS = item("angelic_bless", AngelicBless::new)
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();
			ANGELIC_DESCENT = item("angelic_descent", AngelicDescent::new)
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();
			ANGELIC_PROTECTION = item("angelic_protection", AngelicProtection::new)
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE, TagGen.TOTEM).register();
			ANGELIC_PUNISHMENT = item("angelic_punishment", AngelicPunishment::new)
					.tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();
		}

	}

	public static <T extends Item> ItemBuilder<T, L2Registrate> item(String id, NonNullFunction<Item.Properties, T> factory) {
		return REGISTRATE.item(id, factory)
				.model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("item/pandora/" + id)));
	}

	public static ItemEntry<DescCurioItem> descItem(String id, String name, String desc) {
		REGISTRATE.addRawLang("item." + CurseOfPandora.MODID + "." + id + ".desc", desc);
		return item(id, DescCurioItem::new)
				.lang(name).tag(PandoraTagGen.PANDORA_SLOT, CoPTagGen.PANDORA_BASE).register();
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
