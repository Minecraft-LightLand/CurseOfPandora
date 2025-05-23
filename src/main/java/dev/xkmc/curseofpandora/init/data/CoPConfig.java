package dev.xkmc.curseofpandora.init.data;

import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.l2core.util.ConfigInit;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CoPConfig {

	public static class Client extends ConfigInit {

		Client(Builder builder) {
			markL2();
		}

	}

	public static class Recipe extends ConfigInit {

		public final ModConfigSpec.BooleanValue enableImmunityCharms;

		Recipe(Builder builder) {
			markL2();
			enableImmunityCharms = builder.text("Enable Immunity Charms").define("enableImmunityCharms", true);
		}

	}

	public static class Server extends ConfigInit {

		public static class Attr {

			public final ModConfigSpec.DoubleValue charmOfHealth;
			public final ModConfigSpec.DoubleValue charmOfArmor;
			public final ModConfigSpec.DoubleValue charmOfArmorToughness;
			public final ModConfigSpec.DoubleValue charmOfSpeed;
			public final ModConfigSpec.DoubleValue charmOfDamage;
			public final ModConfigSpec.DoubleValue charmOfCritical;
			public final ModConfigSpec.DoubleValue charmOfArchery;
			public final ModConfigSpec.DoubleValue charmOfHeavyWeapon;
			public final ModConfigSpec.DoubleValue charmOfHeavyWeaponSlow;
			public final ModConfigSpec.DoubleValue charmOfAccuracy;
			public final ModConfigSpec.DoubleValue charmOfAccuracySlow;
			public final ModConfigSpec.DoubleValue charmOfProtection;
			public final ModConfigSpec.DoubleValue charmOfMagic;
			public final ModConfigSpec.DoubleValue charmOfExplosion;
			public final ModConfigSpec.DoubleValue charmOfLuck;
			public final ModConfigSpec.DoubleValue orbOfExecutorAttack;
			public final ModConfigSpec.DoubleValue orbOfExecutorHealth;
			public final ModConfigSpec.DoubleValue orbOfSoulGuardSpeed;
			public final ModConfigSpec.DoubleValue orbOfSoulGuardReduction;
			public final ModConfigSpec.DoubleValue orbOfProsecutorCritRate;
			public final ModConfigSpec.DoubleValue orbOfProsecutorCritDmg;

			private Attr(Builder builder) {
				builder.push("attribute", "Attribute Charms");
				charmOfHealth = builder.text("Charm Of Health").defineInRange("charmOfHealth", 2d, 0, 100);
				charmOfArmor = builder.text("Charm Of Armor").defineInRange("charmOfArmor", 2d, 0, 100);
				charmOfArmorToughness = builder.text("Charm Of Armor Toughness").defineInRange("charmOfArmorToughness", 1d, 0, 100);
				charmOfSpeed = builder.text("Charm Of Speed").defineInRange("charmOfSpeed", 0.05, 0, 10);
				charmOfDamage = builder.text("Charm Of Damage").defineInRange("charmOfDamage", 0.05, 0, 10);
				charmOfCritical = builder.text("Charm Of Critical").defineInRange("charmOfCritical", 0.05, 0, 10);
				charmOfArchery = builder.text("Charm Of Archery").defineInRange("charmOfArchery", 0.05, 0, 10);
				charmOfHeavyWeapon = builder.text("Charm Of Heavy Weapon - Attack").defineInRange("charmOfHeavyWeapon", 1d, 0, 100);
				charmOfHeavyWeaponSlow = builder.text("Charm Of Heavy Weapon - Attack Speed").defineInRange("charmOfHeavyWeaponSlow", 2d, 0, 10);
				charmOfAccuracy = builder.text("Charm Of Accuracy - Crit").defineInRange("charmOfAccuracy", 0.2, 0, 10);
				charmOfAccuracySlow = builder.text("Charm Of Accuracy - Attack Speed").defineInRange("charmOfAccuracySlow", 0.5, 0, 10);
				charmOfProtection = builder.text("Charm Of Protection").defineInRange("charmOfProtection", 0.04, 0, 1);
				charmOfMagic = builder.text("Charm Of Magic").defineInRange("charmOfMagic", 0.1, 0, 10);
				charmOfExplosion = builder.text("Charm Of Explosion").defineInRange("charmOfExplosion", 0.1, 0, 10);
				charmOfLuck = builder.text("Charm Of Luck").defineInRange("charmOfLuck", 1d, 0, 10);
				orbOfExecutorAttack = builder.text("Orb Of Executor - Attack").defineInRange("orbOfExecutorAttack", 0.5d, 0, 10);
				orbOfExecutorHealth = builder.text("Orb Of Executor - Health").defineInRange("orbOfExecutorHealth", 0.5d, 0, 10);
				orbOfSoulGuardSpeed = builder.text("Orb Of Soul Guard - Speed").defineInRange("orbOfSoulGuardSpeed", 0.2d, 0, 1);
				orbOfSoulGuardReduction = builder.text("Orb Of Soul Guard - Reduction").defineInRange("orbOfSoulGuardReduction", 0.1d, 0, 1);
				orbOfProsecutorCritRate = builder.text("Orb Of Prosecutor - Crit Rate").defineInRange("orbOfProsecutorCritRate", 0.1d, 0, 1);
				orbOfProsecutorCritDmg = builder.text("Orb Of Prosecutor - Crit Dmg").defineInRange("orbOfProsecutorCritDmg", 0.2d, 0, 1);
				builder.pop();
			}

		}

		public static class Curse {

			// reality
			public final ModConfigSpec.DoubleValue curseOfInertiaCap;
			public final ModConfigSpec.DoubleValue curseOfInertiaBase;
			public final ModConfigSpec.DoubleValue curseOfInertiaBonus;
			public final ModConfigSpec.DoubleValue curseOfProximityCap;
			public final ModConfigSpec.DoubleValue curseOfProximityBase;
			public final ModConfigSpec.DoubleValue curseOfProximityBonus;
			public final ModConfigSpec.IntValue curseOfFleshThreshold;
			public final ModConfigSpec.IntValue curseOfFleshDuration;
			public final ModConfigSpec.DoubleValue curseOfFleshBonus;
			public final ModConfigSpec.IntValue curseOfMetabolismThreshold;
			public final ModConfigSpec.DoubleValue curseOfMetabolismFactor;
			public final ModConfigSpec.DoubleValue curseOfMetabolismBonus;
			public final ModConfigSpec.IntValue curseOfMetabolismIndexReq;
			public final ModConfigSpec.IntValue curseOfTensionPenaltyDuration;
			public final ModConfigSpec.DoubleValue curseOfTensionPenaltyThreshold;
			public final ModConfigSpec.IntValue curseOfTensionTokenMatureTime;
			public final ModConfigSpec.IntValue curseOfTensionTokenEffectiveTime;
			public final ModConfigSpec.DoubleValue curseOfTensionDamageBonus;
			public final ModConfigSpec.IntValue curseOfTensionMaxLevel;
			public final ModConfigSpec.IntValue curseOfPrudenceMaxLevel;
			public final ModConfigSpec.DoubleValue curseOfPrudenceDamageFactor;
			public final ModConfigSpec.IntValue curseOfPrudenceDuration;
			public final ModConfigSpec.DoubleValue curseOfPrudenceMaxDamage;
			public final ModConfigSpec.DoubleValue curseOfSpellLoadFactor;
			public final ModConfigSpec.DoubleValue curseOfSpellDamageFactor;
			public final ModConfigSpec.DoubleValue curseOfSpellWeakenFactor;


			public final ModConfigSpec.ConfigValue<String> curseOfInertiaSlot;
			public final ModConfigSpec.ConfigValue<String> curseOfProximitySlot;
			public final ModConfigSpec.ConfigValue<String> curseOfFleshSlot;
			public final ModConfigSpec.ConfigValue<String> curseOfTensionSlot;
			public final ModConfigSpec.ConfigValue<String> curseOfPrudenceSlot;
			public final ModConfigSpec.ConfigValue<String> curseOfSpellSlot;

			private Curse(Builder builder) {
				builder.push("reality", "Reality");

				curseOfInertiaCap = builder.text("Curse of Inertia max attack speed for weapon allowed")
						.defineInRange("curseOfInertiaCap", 3d, 0, 20);
				curseOfInertiaBase = builder.text("Curse of Inertia attack speed threshold for bonus")
						.defineInRange("curseOfInertiaBase", 0.5d, 0, 20);
				curseOfInertiaBonus = builder.text("Curse of Inertia attack speed bonus")
						.defineInRange("curseOfInertiaBonus", 0.8d, 0, 20);
				curseOfProximityCap = builder.text("Curse of Proximity max attack range for weapon allowed")
						.defineInRange("curseOfProximityCap", 6d, 0, 20);
				curseOfProximityBase = builder.text("Curse of Proximity attack range threshold for bonus")
						.defineInRange("curseOfProximityBase", 2d, 0, 20);
				curseOfProximityBonus = builder.text("Curse of Proximity attack range bonus")
						.defineInRange("curseOfProximityBonus", 0.5d, 0, 20);
				curseOfFleshThreshold = builder.text("Curse of Flesh food bar threshold")
						.defineInRange("curseOfFleshThreshold", 14, 0, 20);
				curseOfFleshDuration = builder.text("Curse of Flesh required duration in minutes for food bar to be above threshold")
						.defineInRange("curseOfFleshDuration", 2, 0, 20);
				curseOfFleshBonus = builder.text("Curse of Flesh max health bonus")
						.defineInRange("curseOfFleshBonus", 1d, 0, 100);
				curseOfMetabolismThreshold = builder.text("Curse of Metabolism food bar threshold")
						.defineInRange("curseOfMetabolismThreshold", 16, 0, 20);
				curseOfMetabolismFactor = builder.text("Curse of Metabolism buff / debuff per food bar")
						.defineInRange("curseOfMetabolismFactor", 0.05d, 0, 1);
				curseOfMetabolismBonus = builder.text("Curse of Metabolism max food bonus")
						.defineInRange("curseOfMetabolismBonus", 0.2d, 0, 10);
				curseOfMetabolismIndexReq = builder.text("Curse of Metabolism reality index requirement for bonus")
						.defineInRange("curseOfMetabolismIndexReq", 5, 0, 7);
				curseOfTensionPenaltyDuration = builder.text("Curse of Tension penalty duration in ticks when hit by target")
						.defineInRange("curseOfTensionPenaltyDuration", 60, 0, 2000);
				curseOfTensionPenaltyThreshold = builder.text("Curse of Tension penalty threshold as percentage of max health")
						.defineInRange("curseOfTensionPenaltyThreshold", 0.1, 0, 1);
				curseOfTensionTokenMatureTime = builder.text("Curse of Tension time for token to take effect")
						.defineInRange("curseOfTensionTokenMatureTime", 200, 0, 2000);
				curseOfTensionTokenEffectiveTime = builder.text("Curse of Tension time for token to be effective")
						.defineInRange("curseOfTensionTokenEffectiveTime", 200, 0, 2000);
				curseOfTensionDamageBonus = builder.text("Curse of Tension damage bonus per token")
						.defineInRange("curseOfTensionDamageBonus", 0.2, 0, 100);
				curseOfTensionMaxLevel = builder.text("Curse of Tension max token level")
						.defineInRange("curseOfTensionMaxLevel", 5, 1, 100);
				curseOfPrudenceMaxLevel = builder.text("Curse of Prudence max token level")
						.defineInRange("curseOfPrudenceMaxLevel", 20, 1, 100);
				curseOfPrudenceDamageFactor = builder.text("Curse of Prudence damage factor per token, 0.9 means -10% damage per token")
						.defineInRange("curseOfPrudenceDamageFactor", 0.5, 0, 1);
				curseOfPrudenceDuration = builder.text("Curse of Prudence token duration in ticks")
						.defineInRange("curseOfPrudenceDuration", 40, 1, 200);
				curseOfPrudenceMaxDamage = builder.text("Curse of Prudence max damage as percentage of target max health")
						.defineInRange("curseOfPrudenceMaxDamage", 0.2, 0, 1);
				curseOfSpellLoadFactor = builder.text("Curse of Spell: factor for spell load calculation.")
						.comment("Lower number means enchantment puts less load on items and player")
						.defineInRange("curseOfSpellLoadFactor", 1d, 0, 100);
				curseOfSpellDamageFactor = builder.text("Curse of Spell: player will take more damage when having spell overload")
						.comment("Higher number means player takes more damage")
						.defineInRange("curseOfSpellDamageFactor", 1d, 0, 100);
				curseOfSpellWeakenFactor = builder.text("Curse of Spell: player will deal less damage when having spell overload")
						.comment("Higher number means mob takes less damage")
						.defineInRange("curseOfSpellWeakenFactor", 1d, 0, 100);

				curseOfInertiaSlot = builder.text("Curse of Inertia: bonus slot")
						.define("curseOfInertiaSlot", "necklace#1");
				curseOfProximitySlot = builder.text("Curse of Proximity: bonus slot")
						.define("curseOfProximitySlot", "bracelet#1");
				curseOfFleshSlot = builder.text("Curse of Flesh: bonus slot")
						.define("curseOfFleshSlot", "curio#1");
				curseOfTensionSlot = builder.text("Curse of Tension: bonus slot")
						.define("curseOfTensionSlot", "hands#1");
				curseOfPrudenceSlot = builder.text("Curse of Prudence: bonus slot")
						.define("curseOfPrudenceSlot", "charm#3");
				curseOfSpellSlot = builder.text("Curse of Spell: bonus slot")
						.define("curseOfSpellSlot", "hands#1");

				builder.pop();
			}
		}

		public static class Angelic {

			// angelic
			public final ModConfigSpec.IntValue angelicWingRealityIndex;
			public final ModConfigSpec.DoubleValue angelicWingBoost;
			public final ModConfigSpec.DoubleValue angelicWingMaxSpeed;
			public final ModConfigSpec.IntValue angelicBlessRealityIndex;
			public final ModConfigSpec.DoubleValue angelicBlessDamageReduction;
			public final ModConfigSpec.DoubleValue angelicBlessAbsorption;
			public final ModConfigSpec.IntValue angelicDescentRealityIndex;
			public final ModConfigSpec.DoubleValue angelicDescentMeleeBonus;
			public final ModConfigSpec.DoubleValue angelicDescentUndeadBonus;
			public final ModConfigSpec.IntValue angelicProtectionRealityIndex;
			public final ModConfigSpec.IntValue angelicProtectionCoolDown;
			public final ModConfigSpec.IntValue angelicPunishmentRealityIndex;
			public final ModConfigSpec.IntValue angelicPunishmentCoolDown;
			public final ModConfigSpec.DoubleValue angelicPunishmentDamageBase;

			private Angelic(Builder builder) {
				builder.push("angelic", "Angelic");

				angelicWingRealityIndex = builder.text("Reality Index requirement for Angelic Wing")
						.defineInRange("angelicWingRealityIndex", 3, 0, 7);
				angelicWingBoost = builder.text("Angelic Wing speed boost in block per tick")
						.defineInRange("angelicWingBoost", 0.03, 0, 0.1);
				angelicWingMaxSpeed = builder.text("Angelic Wing max speed in block per tick")
						.defineInRange("angelicWingMaxSpeed", 5d, 0, 50);
				angelicBlessRealityIndex = builder.text("Reality Index requirement for Angelic Bless")
						.defineInRange("angelicBlessRealityIndex", 4, 0, 7);
				angelicBlessDamageReduction = builder.text("Angelic Bless total damage reduction")
						.defineInRange("angelicBlessDamageReduction", 0.25, 0, 1);
				angelicBlessAbsorption = builder.text("Angelic Bless damage absorption")
						.defineInRange("angelicBlessAbsorption", 1d, 0, 100);
				angelicDescentRealityIndex = builder.text("Reality Index requirement for Angelic Descent")
						.defineInRange("angelicDescentRealityIndex", 5, 0, 7);
				angelicDescentMeleeBonus = builder.text("Angelic Descent melee damage bonus")
						.defineInRange("angelicDescentMeleeBonus", 0.5, 0, 10);
				angelicDescentUndeadBonus = builder.text("Angelic Descent damage bonus against undead")
						.defineInRange("angelicDescentUndeadBonus", 0.5, 0, 10);
				angelicProtectionRealityIndex = builder.text("Requirement of Reality Index of Angelic Protection")
						.defineInRange("angelicProtectionRealityIndex", 6, 0, 7);
				angelicProtectionCoolDown = builder.text("Angelic Protection cool down in ticks")
						.defineInRange("angelicProtectionCoolDown", 600, 0, 1000000);
				angelicPunishmentRealityIndex = builder.text("Requirement of Reality Index of Angelic Punishment")
						.defineInRange("angelicPunishmentRealityIndex", 7, 0, 7);
				angelicPunishmentCoolDown = builder.text("Angelic Punishment cool down in ticks")
						.defineInRange("angelicPunishmentCoolDown", 20, 0, 6000);
				angelicPunishmentDamageBase = builder.text("Angelic Punishment minimum damage in percentage of target current health")
						.defineInRange("angelicPunishmentDamageBase", 0.05, 0, 1);

				builder.pop();
			}
		}

		public static class Hell {

			public final ModConfigSpec.IntValue hellfireSkullRealityIndex;
			public final ModConfigSpec.IntValue hellfireSkullMinimumDuration;
			public final ModConfigSpec.IntValue hellfireReformationRealityIndex;
			public final ModConfigSpec.IntValue eyeOfCursedSoulRealityIndex;
			public final ModConfigSpec.DoubleValue eyeOfCursedSoulRange;
			public final ModConfigSpec.IntValue eyeOfCursedSoulCoolDown;
			public final ModConfigSpec.IntValue cursedSoulCrystalRealityIndex;
			public final ModConfigSpec.IntValue cursedSoulCrystalTriggerCoolDown;
			public final ModConfigSpec.DoubleValue cursedSoulCrystalRange;
			public final ModConfigSpec.IntValue crownOfDemonRealityIndex;
			public final ModConfigSpec.IntValue crownOfDemonBaseHealthThreshold;
			public final ModConfigSpec.IntValue crownOfDemonRange;

			private Hell(Builder builder) {
				builder.push("hell", "Hell");

				hellfireSkullRealityIndex = builder.text("Reality Index requirement for Hellfire Skull")
						.defineInRange("hellfireSkullRealityIndex", 3, 0, 7);
				hellfireSkullMinimumDuration = builder.text("Minimum soul flame effect duration in ticks for Hellfire Skull to work")
						.defineInRange("hellfireSkullMinimumDuration", 200, 1, 200000);
				hellfireReformationRealityIndex = builder.text("Reality Index requirement for Hellfire Reformation")
						.defineInRange("hellfireReformationRealityIndex", 4, 0, 7);
				eyeOfCursedSoulRealityIndex = builder.text("Reality Index requirement for Eye of Cursed Souls")
						.defineInRange("eyeOfCursedSoulRealityIndex", 5, 0, 7);
				eyeOfCursedSoulRange = builder.text("Eye of Cursed Souls damage radius")
						.defineInRange("eyeOfCursedSoulRange", 8d, 0, 64);
				eyeOfCursedSoulCoolDown = builder.text("Effect trigger cool down in ticks for Eye of Cursed Souls")
						.defineInRange("eyeOfCursedSoulCoolDown", 200, 0, 1000000);
				cursedSoulCrystalRealityIndex = builder.text("Reality Index requirement for Cursed Soul Crystal")
						.defineInRange("cursedSoulCrystalRealityIndex", 6, 0, 7);
				cursedSoulCrystalTriggerCoolDown = builder.text("Effect trigger cool down in ticks for Cursed Soul Crystal")
						.defineInRange("cursedSoulCrystalTriggerCoolDown", 200, 0, 1000000);
				cursedSoulCrystalRange = builder.text("Max range to search undead mobs for Cursed Soul Crystal")
						.defineInRange("cursedSoulCrystalRange", 8d, 0, 64);
				crownOfDemonRealityIndex = builder.text("Reality Index requirement for Crown of Demon")
						.defineInRange("crownOfDemonRealityIndex", 7, 0, 7);
				crownOfDemonBaseHealthThreshold = builder.text("Crown of Demon can command undead mobs with base health lower than:")
						.defineInRange("crownOfDemonBaseHealthThreshold", 50, 1, 1000000);
				crownOfDemonRange = builder.text("Crown of Demon range of detection")
						.defineInRange("crownOfDemonRange", 24, 0, 128);

				builder.pop();
			}
		}

		public static class Shadow {

			public final ModConfigSpec.IntValue shadowCoreRealityIndex;
			public final ModConfigSpec.IntValue shadowCoreDurationPerIndex;
			public final ModConfigSpec.DoubleValue damageReduction;
			public final ModConfigSpec.IntValue shadowConvergenceRealityIndex;
			public final ModConfigSpec.DoubleValue shadowConvergenceHealFactor;
			public final ModConfigSpec.IntValue shadowConsolidationRealityIndex;
			public final ModConfigSpec.DoubleValue shadowConsolidationRange;
			public final ModConfigSpec.DoubleValue shadowConsolidationFactor;
			public final ModConfigSpec.IntValue shadowConsolidationDelay;
			public final ModConfigSpec.IntValue shadowConsolidationCoolDown;
			public final ModConfigSpec.IntValue shadowReformationRealityIndex;
			public final ModConfigSpec.DoubleValue shadowReformationBonus;
			public final ModConfigSpec.DoubleValue shadowReformationReduction;
			public final ModConfigSpec.IntValue voidOverflowRealityIndex;

			private Shadow(Builder builder) {
				builder.push("shadow", "Shadow");
				shadowCoreRealityIndex = builder.text("Reality Index requirement for Shadow Core")
						.defineInRange("shadowCoreRealityIndex", 3, 0, 7);
				shadowCoreDurationPerIndex = builder.text("Shadow effect duration per reality index")
						.defineInRange("shadowCoreDurationPerIndex", 100, 0, 60000);
				damageReduction = builder.text("Damage reduction for shadow effect")
						.defineInRange("damageReduction", 0.2, 0, 1);
				shadowConvergenceRealityIndex = builder.text("Reality Index requirement for Shadow Convergence")
						.defineInRange("shadowConvergenceRealityIndex", 4, 0, 7);
				shadowConvergenceHealFactor = builder.text("Shadow Convergence healing factor")
						.defineInRange("shadowConvergenceHealFactor", 0.5d, 0, 100d);
				shadowConsolidationRealityIndex = builder.text("Reality Index requirement for Shadow Consolidation")
						.defineInRange("shadowConsolidationRealityIndex", 5, 0, 7);
				shadowConsolidationRange = builder.text("Shadow Consolidation scatter range")
						.defineInRange("shadowConsolidationRange", 8d, 0, 64);
				shadowConsolidationFactor = builder.text("Shadow Consolidation damage factor")
						.defineInRange("shadowConsolidationFactor", 1d, 0, 100);
				shadowConsolidationDelay = builder.text("Shadow Consolidation damage delay")
						.defineInRange("shadowConsolidationDelay", 20, 0, 100);
				shadowConsolidationCoolDown = builder.text("Shadow Consolidation trigger cool down")
						.defineInRange("shadowConsolidationCoolDown", 60, 0, 10000);
				shadowReformationRealityIndex = builder.text("Reality Index requirement for Shadow Reformation")
						.defineInRange("shadowReformationRealityIndex", 6, 0, 7);
				shadowReformationBonus = builder.text("Shadow Reformation magic damage bonus")
						.defineInRange("shadowReformationBonus", 0.5, 0, 100);
				shadowReformationReduction = builder.text("Shadow Reformation damage reduction")
						.defineInRange("shadowReformationReduction", 0.5, 0, 1);
				voidOverflowRealityIndex = builder.text("Reality Index requirement for Void Reformation")
						.defineInRange("voidOverflowRealityIndex", 7, 0, 7);
				builder.pop();
			}

		}

		public static class Elemental {

			public final ModConfigSpec.IntValue windThrustRealityIndex;
			public final ModConfigSpec.DoubleValue windThrustSpeed;
			public final ModConfigSpec.DoubleValue windThrustDamage;
			public final ModConfigSpec.IntValue earthCrushRealityIndex;
			public final ModConfigSpec.DoubleValue earthCrushThreshold;
			public final ModConfigSpec.DoubleValue earthCrushBonus;
			public final ModConfigSpec.IntValue flamingExplosionRealityIndex;
			public final ModConfigSpec.DoubleValue flamingExplosionBonus;
			public final ModConfigSpec.IntValue wavingSpellRealityIndex;
			public final ModConfigSpec.DoubleValue wavingSpellBonus;
			public final ModConfigSpec.IntValue curseRedirectionRealityIndex;
			public final ModConfigSpec.DoubleValue curseRedirectionBonus;

			private Elemental(Builder builder) {
				builder.push("elemental", "Elemental");
				windThrustRealityIndex = builder.text("Reality Index requirement for Wind Thrust")
						.defineInRange("windThrustRealityIndex", 4, 0, 7);
				windThrustSpeed = builder.text("Speed bonus for Wind Thrust")
						.defineInRange("windThrustSpeed", 0.5, 0, 10);
				windThrustDamage = builder.text("Melee damage bonus for Wind Thrust")
						.defineInRange("windThrustDamage", 1d, 0, 10);
				earthCrushRealityIndex = builder.text("Reality Index requirement for Earth Crush")
						.defineInRange("earthCrushRealityIndex", 4, 0, 7);
				earthCrushThreshold = builder.text("Attack speed threshold for Earth Crush")
						.defineInRange("earthCrushThreshold", 1d, 0, 4);
				earthCrushBonus = builder.text("Explosion damage bonus for Earth Crush")
						.defineInRange("earthCrushBonus", 1d, 0, 100);
				flamingExplosionRealityIndex = builder.text("Reality Index requirement for Flaming Explosion")
						.defineInRange("flamingExplosionRealityIndex", 4, 0, 7);
				flamingExplosionBonus = builder.text("Explosion damage bonus for Flaming Explosion")
						.defineInRange("flamingExplosionBonus", 1d, 0, 100);
				wavingSpellRealityIndex = builder.text("Reality Index requirement for Waving Spell")
						.defineInRange("wavingSpellRealityIndex", 4, 0, 7);
				wavingSpellBonus = builder.text("Magic damage bonus for Waving Spell")
						.defineInRange("wavingSpellBonus", 1d, 0, 100);
				curseRedirectionRealityIndex = builder.text("Reality Index requirement for Curse Redirection")
						.defineInRange("curseRedirectionRealityIndex", 4, 0, 7);
				curseRedirectionBonus = builder.text("Magic damage bonus per curse enchantment for Curse Redirection")
						.defineInRange("curseRedirectionBonus", 0.5d, 0, 10);
				builder.pop();
			}

		}

		public static class Abyssal {

			public final ModConfigSpec.IntValue abyssalTreasureRealityIndex;
			public final ModConfigSpec.IntValue abyssalWatcherRealityIndex;
			public final ModConfigSpec.IntValue abyssalShellRealityIndex;
			public final ModConfigSpec.IntValue abyssalCrownRealityIndex;
			public final ModConfigSpec.IntValue abyssalWillRealityIndex;
			public final ModConfigSpec.IntValue abyssalDepthStep;
			public final ModConfigSpec.IntValue abyssalWillDepthStep;
			public final ModConfigSpec.DoubleValue abyssalWatcherRegen;
			public final ModConfigSpec.DoubleValue abyssalShellBonus;
			public final ModConfigSpec.DoubleValue abyssalCrownChance;
			public final ModConfigSpec.IntValue abyssalWillCoolDown;
			public final ModConfigSpec.IntValue abyssalWillDuration;

			private Abyssal(Builder builder) {
				builder.push("abyssal", "Abyssal");
				abyssalDepthStep = builder.text("Depth requirement for Abyssal charms")
						.defineInRange("abyssalDepthStep", 12, 0, 64);
				abyssalWillDepthStep = builder.text("Depth requirement for Abyssal charms with Abyssal Will")
						.defineInRange("abyssalWillDepthStep", 8, 0, 64);
				abyssalTreasureRealityIndex = builder.text("Reality Index requirement for Abyssal Treasure")
						.defineInRange("abyssalTreasureRealityIndex", 3, 0, 7);
				abyssalWatcherRealityIndex = builder.text("Reality Index requirement for Abyssal Watcher")
						.defineInRange("abyssalWatcherRealityIndex", 4, 0, 7);
				abyssalShellRealityIndex = builder.text("Reality Index requirement for Abyssal Shell")
						.defineInRange("abyssalShellRealityIndex", 5, 0, 7);
				abyssalCrownRealityIndex = builder.text("Reality Index requirement for Abyssal Crown")
						.defineInRange("abyssalCrownRealityIndex", 6, 0, 7);
				abyssalWillRealityIndex = builder.text("Reality Index requirement for Abyssal Will")
						.defineInRange("abyssalWillRealityIndex", 7, 0, 7);
				abyssalWatcherRegen = builder.text("Abyssal Watcher regen per depth step")
						.defineInRange("abyssalWatcherRegen", 0.01, 0, 1);
				abyssalShellBonus = builder.text("Abyssal Shell armor and toughness bonus per depth step")
						.defineInRange("abyssalShellBonus", 0.2, 0, 1);
				abyssalCrownChance = builder.text("Abyssal Crown magic bypassing chance per depth step")
						.defineInRange("abyssalCrownChance", 0.05, 0, 1);
				abyssalWillCoolDown = builder.text("Abyssal Will totem effect cool down in ticks")
						.defineInRange("abyssalWillCoolDown", 600, 1, 100000);
				abyssalWillDuration = builder.text("Abyssal Will protection effect duration in ticks")
						.defineInRange("abyssalWillDuration", 200, 1, 100000);
				builder.pop();
			}

		}

		public static class Barbaric {

			public final ModConfigSpec.DoubleValue magicDamageDebuff;
			public final ModConfigSpec.IntValue barbaricInstinctRealityIndex;
			public final ModConfigSpec.DoubleValue barbaricInstinctHeal;
			public final ModConfigSpec.IntValue barbaricInstinctCoolDown;
			public final ModConfigSpec.IntValue barbaricWrathRealityIndex;
			public final ModConfigSpec.DoubleValue barbaricWrathCritBonus;
			public final ModConfigSpec.DoubleValue barbaricWrathToughBonus;
			public final ModConfigSpec.IntValue barbaricRoarRealityIndex;
			public final ModConfigSpec.DoubleValue barbaricRoarReduction;
			public final ModConfigSpec.DoubleValue barbaricRoarAttack;
			public final ModConfigSpec.IntValue barbaricBladeRealityIndex;
			public final ModConfigSpec.DoubleValue barbaricBladeProjectile;
			public final ModConfigSpec.DoubleValue barbaricBladeAttack;
			public final ModConfigSpec.IntValue primalForceRealityIndex;
			public final ModConfigSpec.DoubleValue primalForceSelfArmor;
			public final ModConfigSpec.DoubleValue primalForceTargetArmor;

			private Barbaric(Builder builder) {
				builder.push("barbaric", "Barbaric");
				magicDamageDebuff = builder.text("Magic damage reduction in percentage for all barbaric set")
						.defineInRange("magicDamageDebuff", 1d, 0, 100);
				barbaricInstinctRealityIndex = builder.text("Reality Index requirement for Barbaric Instinct")
						.defineInRange("barbaricInstinctRealityIndex", 3, 0, 7);
				barbaricInstinctHeal = builder.text("Barbaric Instinct healing percentage")
						.defineInRange("barbaricInstinctHeal", 0.1, 0, 1);
				barbaricInstinctCoolDown = builder.text("Barbaric Instinct healing cool down")
						.defineInRange("barbaricInstinctCoolDown", 40, 0, 1000);
				barbaricWrathRealityIndex = builder.text("Reality Index requirement for Barbaric Wrath")
						.defineInRange("barbaricWrathRealityIndex", 4, 0, 7);
				barbaricWrathCritBonus = builder.text("Barbaric Wrath crit damage bonus")
						.defineInRange("barbaricWrathCritBonus", 0.5, 0, 10);
				barbaricWrathToughBonus = builder.text("Barbaric Wrath armor toughness bonus")
						.defineInRange("barbaricWrathToughBonus", 10d, 0, 1000);
				barbaricRoarRealityIndex = builder.text("Reality Index requirement for Barbaric Scare")
						.defineInRange("barbaricScareRealityIndex", 5, 0, 7);
				barbaricRoarAttack = builder.text("Barbaric Roar attack damage bonus")
						.defineInRange("barbaricRoarAttack", 0.25, 0, 10);
				barbaricRoarReduction = builder.text("Barbaric Roar damage reduction")
						.defineInRange("barbaricRoarReduction", 0.25, 0, 1);
				barbaricBladeRealityIndex = builder.text("Reality Index requirement for Barbaric Blade")
						.defineInRange("barbaricBladeRealityIndex", 6, 0, 7);
				barbaricBladeAttack = builder.text("Barbaric Blade attack damage bonus")
						.defineInRange("barbaricBladeAttack", 0.5, 0, 10);
				barbaricBladeProjectile = builder.text("Barbaric Blade projectile damage bonus")
						.defineInRange("barbaricBladeProjectile", 0.5, 0, 10);
				primalForceRealityIndex = builder.text("Reality Index requirement for Primal Force")
						.defineInRange("primalForceRealityIndex", 7, 0, 7);
				primalForceSelfArmor = builder.text("Primal Force armor bonus for player as percentage of max health")
						.defineInRange("primalForceSelfArmor", 1d, 0, 100);
				primalForceTargetArmor = builder.text("Primal Force armor bonus for attacker as percentage of max health")
						.defineInRange("primalForceTargetArmor", 1d, 0, 100);
				builder.pop();
			}

		}

		public static class Mutation {

			public final ModConfigSpec.IntValue infectiveMutationRealityIndex;
			public final ModConfigSpec.IntValue parasiticMutationRealityIndex;
			public final ModConfigSpec.IntValue deformingMutationRealityIndex;
			public final ModConfigSpec.IntValue hostileMutationRealityIndex;
			public final ModConfigSpec.IntValue distortedMutationRealityIndex;

			private Mutation(Builder builder) {
				builder.push("mutation", "Mutation");
				infectiveMutationRealityIndex = builder.text("Reality Index requirement for Infective Mutation")
						.defineInRange("infectiveMutationRealityIndex", 3, 0, 7);
				parasiticMutationRealityIndex = builder.text("Reality Index requirement for Parasitic Mutation")
						.defineInRange("parasiticMutationRealityIndex", 4, 0, 7);
				deformingMutationRealityIndex = builder.text("Reality Index requirement for Deforming Mutation")
						.defineInRange("deformingMutationRealityIndex", 5, 0, 7);
				hostileMutationRealityIndex = builder.text("Reality Index requirement for Hostile Mutation")
						.defineInRange("hostileMutationRealityIndex", 6, 0, 7);
				distortedMutationRealityIndex = builder.text("Reality Index requirement for Distorted Mutation")
						.defineInRange("distortedMutationRealityIndex", 7, 0, 7);
				builder.pop();
			}

		}

		public static class Evil {

			public final ModConfigSpec.IntValue evilSpiritRitualRealityIndex;
			public final ModConfigSpec.DoubleValue evilSpiritRitualExpRate;
			public final ModConfigSpec.IntValue evilSpiritEvokeRealityIndex;
			public final ModConfigSpec.IntValue evilSpiritEvokeDuration;
			public final ModConfigSpec.IntValue evilSpiritEvokeCoolDown;
			public final ModConfigSpec.IntValue evilSpiritAwakeningRealityIndex;
			public final ModConfigSpec.DoubleValue evilSpiritAwakeningMagicBonus;
			public final ModConfigSpec.DoubleValue evilSpiritAwakeningReduction;
			public final ModConfigSpec.IntValue evilSpiritAwakeningDuration;
			public final ModConfigSpec.IntValue evilSpiritAwakeningMaxLevel;
			public final ModConfigSpec.IntValue evilSpiritCurseRealityIndex;
			public final ModConfigSpec.DoubleValue evilSpiritCurseThreshold;
			public final ModConfigSpec.DoubleValue evilSpiritCurseBonus;
			public final ModConfigSpec.IntValue evilSpiritWalkRealityIndex;
			public final ModConfigSpec.DoubleValue evilSpiritWalkMagicBonus;
			public final ModConfigSpec.DoubleValue evilSpiritWalkAtkBonus;
			public final ModConfigSpec.IntValue evilSpiritWalkCoolDown;

			private Evil(Builder builder) {
				builder.push("evil", "Evil");
				evilSpiritRitualRealityIndex = builder.text("Reality Index requirement for Evil Spirit Ritual")
						.defineInRange("evilSpiritRitualRealityIndex", 3, 0, 7);
				evilSpiritRitualExpRate = builder.text("Evil Spirit Ritual extra exp drop as percentage of max health")
						.defineInRange("evilSpiritRitualExpRate", 0.5, 0, 100);
				evilSpiritEvokeRealityIndex = builder.text("Reality Index requirement for Evil Spirit Evoke")
						.defineInRange("evilSpiritEvokeRealityIndex", 4, 0, 7);
				evilSpiritEvokeDuration = builder.text("Evil Spirit Evoke summoned Vex lifetime")
						.defineInRange("evilSpiritEvokeDuration", 400, 0, 100000);
				evilSpiritEvokeCoolDown = builder.text("Evil Spirit Evoke summoning cool down")
						.defineInRange("evilSpiritEvokeCoolDown", 60, 0, 10000);
				evilSpiritAwakeningRealityIndex = builder.text("Reality Index requirement for Evil Spirit Awakening")
						.defineInRange("evilSpiritAwakeningRealityIndex", 5, 0, 7);
				evilSpiritAwakeningMagicBonus = builder.text("Evil Spirit Awakening magic damage bonus")
						.defineInRange("evilSpiritAwakeningMagicBonus", 0.15, 0, 10);
				evilSpiritAwakeningReduction = builder.text("Evil Spirit Awakening damage reduction")
						.defineInRange("evilSpiritAwakeningAtkBonus", 0.1, 0, 10);
				evilSpiritAwakeningDuration = builder.text("Evil Spirit Awakening bonus duration")
						.defineInRange("evilSpiritAwakeningDuration", 600, 0, 1000000);
				evilSpiritAwakeningMaxLevel = builder.text("Evil Spirit Awakening bonus max level")
						.defineInRange("evilSpiritAwakeningMaxLevel", 5, 0, 1000);
				evilSpiritCurseRealityIndex = builder.text("Reality Index requirement for Evil Spirit Curse")
						.defineInRange("evilSpiritCurseRealityIndex", 6, 0, 7);
				evilSpiritCurseThreshold = builder.text("Evil Spirit Curse health threshold")
						.defineInRange("evilSpiritCurseThreshold", 0.2, 0, 10);
				evilSpiritCurseBonus = builder.text("Evil Spirit Curse magic damage bonus")
						.defineInRange("evilSpiritCurseBonus", 1d, 0, 10);
				evilSpiritWalkRealityIndex = builder.text("Reality Index requirement for Evil Spirit Walk")
						.defineInRange("evilSpiritWalkRealityIndex", 7, 0, 7);
				evilSpiritWalkMagicBonus = builder.text("Evil Spirit Walk magic damage bonus")
						.defineInRange("evilSpiritWalkMagicBonus", 0.5, 0, 10);
				evilSpiritWalkAtkBonus = builder.text("Evil Spirit Walk attack damage bonus")
						.defineInRange("evilSpiritWalkAtkBonus", 0.5, 0, 10);
				evilSpiritWalkCoolDown = builder.text("Evil Spirit Walk cool down")
						.defineInRange("evilSpiritWalkCoolDown", 600, 0, 1000000);
				builder.pop();
			}

		}

		public static class Weapon {

			public final ModConfigSpec.IntValue angelicJudgementRealityIndex;
			public final ModConfigSpec.IntValue doomStarRealityIndex;
			public final ModConfigSpec.IntValue cursedKarmaRealityIndex;
			public final ModConfigSpec.IntValue cursedKarmaExplosionRadius;
			public final ModConfigSpec.IntValue cursedKarmaEffectDuration;
			public final ModConfigSpec.IntValue abyssalEdgeRealityIndex;

			private Weapon(Builder builder) {
				builder.push("weapon", "Weapon");
				angelicJudgementRealityIndex = builder.text("Reality Index requirement for Angelic Judgement")
						.defineInRange("angelicJudgementRealityIndex", 5, 0, 7);
				doomStarRealityIndex = builder.text("Reality Index requirement for Doom Star")
						.defineInRange("doomStarRealityIndex", 5, 0, 7);
				cursedKarmaRealityIndex = builder.text("Reality Index requirement for Cursed Karma")
						.defineInRange("cursedKarmaRealityIndex", 5, 0, 7);
				cursedKarmaExplosionRadius = builder.text("Cursed Karma explosion radius")
						.defineInRange("cursedKarmaExplosionRadius", 3, 0, 8);
				cursedKarmaEffectDuration = builder.text("Cursed Karma effect duration")
						.defineInRange("cursedKarmaEffectDuration", 300, 0, 1000000);
				abyssalEdgeRealityIndex = builder.text("Reality Index requirement for Abyssal Edge")
						.defineInRange("abyssalEdgeRealityIndex", 5, 0, 7);
				builder.pop();
			}

		}

		public static class Compat {

			public final ModConfigSpec.IntValue sealOfSwordDifficultyPerBonus;
			public final ModConfigSpec.IntValue sealOfSwordMaxRealityBonus;
			public final ModConfigSpec.DoubleValue spellSingularitySpellBonusPerReality;
			public final ModConfigSpec.DoubleValue spellSingularityMagicDamageBonusPerReality;

			public final ModConfigSpec.BooleanValue allowRealityTrait;

			private Compat(Builder builder) {
				builder.push("compat", "Compat");
				allowRealityTrait = builder.text("L2Hostility compat: allow Reality trait")
						.define("allowRealityTrait", true);

				sealOfSwordDifficultyPerBonus = builder.text("Seal of Swords: player difficulty required per reality index bonus")
						.defineInRange("sealOfSwordDifficultyPerBonus", 500, 1, 10000);
				sealOfSwordMaxRealityBonus = builder.text("Seal of Swords: max reality index bonus")
						.defineInRange("sealOfSwordMaxRealityBonus", 3, 1, 10000);
				spellSingularitySpellBonusPerReality = builder.text("Spell Singularity: Spell Tolerance per reality index")
						.defineInRange("spellSingularitySpellBonusPerReality", 0.5, 0, 5);
				spellSingularityMagicDamageBonusPerReality = builder.text("Spell Singularity: Magic Damage bonus per reality index")
						.defineInRange("spellSingularityMagicDamageBonusPerReality", 0.5, 0, 5);


				builder.pop();
			}

		}


		public final Attr attr;
		public final Curse curse;
		public final Angelic angelic;
		public final Hell hell;
		public final Shadow shadow;
		public final Elemental elemental;
		public final Abyssal abyssal;
		public final Barbaric barbaric;
		public final Mutation mutation;
		public final Evil evil;
		public final Weapon weapon;
		public final Compat compat;

		public final ModConfigSpec.DoubleValue lootLuckFactor;
		public final ModConfigSpec.IntValue maxItemGenerated;

		Server(Builder builder) {
			markL2();
			builder.push("loot", "Loot");
			lootLuckFactor = builder.text("Scale up/down luck in calculation of chance to find a pandora charm in chest")
					.defineInRange("lootLuckFactor", 1d, 0, 10);
			maxItemGenerated = builder.text("Maximum number of pandora charms generated in loot chest")
					.defineInRange("maxItemGenerated", 2, 0, 10);
			builder.pop();
			this.attr = new Attr(builder);
			this.curse = new Curse(builder);
			this.angelic = new Angelic(builder);
			this.hell = new Hell(builder);
			this.shadow = new Shadow(builder);
			this.elemental = new Elemental(builder);
			this.abyssal = new Abyssal(builder);
			this.barbaric = new Barbaric(builder);
			this.mutation = new Mutation(builder);
			this.evil = new Evil(builder);
			this.weapon = new Weapon(builder);
			this.compat = new Compat(builder);
		}

	}

	public static final Client CLIENT;
	public static final Recipe RECIPE;
	public static final Server SERVER;

	static {
		CLIENT = CurseOfPandora.REGISTRATE.registerClient(Client::new);
		RECIPE = CurseOfPandora.REGISTRATE.registerUnsynced(Recipe::new);
		SERVER = CurseOfPandora.REGISTRATE.registerSynced(Server::new);
	}

	public static void init() {
	}

}
