package dev.xkmc.curseofpandora.init.data;

import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.l2core.util.ConfigInit;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CoPConfig {

	public static class Client extends ConfigInit {

		Client(Builder builder) {
		}

	}

	public static class Common extends ConfigInit {

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
				builder.push("AttributeCharms");
				charmOfHealth = builder.defineInRange("charmOfHealth", 2d, 0, 100);
				charmOfArmor = builder.defineInRange("charmOfArmor", 2d, 0, 100);
				charmOfArmorToughness = builder.defineInRange("charmOfArmorToughness", 1d, 0, 100);
				charmOfSpeed = builder.defineInRange("charmOfSpeed", 0.05, 0, 10);
				charmOfDamage = builder.defineInRange("charmOfDamage", 0.05, 0, 10);
				charmOfCritical = builder.defineInRange("charmOfCritical", 0.05, 0, 10);
				charmOfArchery = builder.defineInRange("charmOfArchery", 0.05, 0, 10);
				charmOfHeavyWeapon = builder.defineInRange("charmOfHeavyWeapon", 1d, 0, 100);
				charmOfHeavyWeaponSlow = builder.defineInRange("charmOfHeavyWeaponSlow", 2d, 0, 10);
				charmOfAccuracy = builder.defineInRange("charmOfAccuracy", 0.2, 0, 10);
				charmOfAccuracySlow = builder.defineInRange("charmOfAccuracySlow", 0.5, 0, 10);
				charmOfProtection = builder.defineInRange("charmOfProtection", 0.04, 0, 1);
				charmOfMagic = builder.defineInRange("charmOfMagic", 0.1, 0, 10);
				charmOfExplosion = builder.defineInRange("charmOfExplosion", 0.1, 0, 10);
				charmOfLuck = builder.defineInRange("charmOfLuck", 1d, 0, 10);
				orbOfExecutorAttack = builder.defineInRange("orbOfExecutorAttack", 0.5d, 0, 10);
				orbOfExecutorHealth = builder.defineInRange("orbOfExecutorHealth", 0.5d, 0, 10);
				orbOfSoulGuardSpeed = builder.defineInRange("orbOfSoulGuardSpeed", 0.2d, 0, 1);
				orbOfSoulGuardReduction = builder.defineInRange("orbOfSoulGuardReduction", 0.1d, 0, 1);
				orbOfProsecutorCritRate = builder.defineInRange("orbOfProsecutorCritRate", 0.1d, 0, 1);
				orbOfProsecutorCritDmg = builder.defineInRange("orbOfProsecutorCritDmg", 0.2d, 0, 1);
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
				builder.push("Reality");

				curseOfInertiaCap = builder.comment("Curse of Inertia max attack speed for weapon allowed")
						.defineInRange("curseOfInertiaCap", 3d, 0, 20);
				curseOfInertiaBase = builder.comment("Curse of Inertia attack speed threshold for bonus")
						.defineInRange("curseOfInertiaBase", 0.5d, 0, 20);
				curseOfInertiaBonus = builder.comment("Curse of Inertia attack speed bonus")
						.defineInRange("curseOfInertiaBonus", 0.8d, 0, 20);
				curseOfProximityCap = builder.comment("Curse of Proximity max attack range for weapon allowed")
						.defineInRange("curseOfProximityCap", 6d, 0, 20);
				curseOfProximityBase = builder.comment("Curse of Proximity attack range threshold for bonus")
						.defineInRange("curseOfProximityBase", 2d, 0, 20);
				curseOfProximityBonus = builder.comment("Curse of Proximity attack range bonus")
						.defineInRange("curseOfProximityBonus", 0.5d, 0, 20);
				curseOfFleshThreshold = builder.comment("Curse of Flesh food bar threshold")
						.defineInRange("curseOfFleshThreshold", 14, 0, 20);
				curseOfFleshDuration = builder.comment("Curse of Flesh required duration in minutes for food bar to be above threshold")
						.defineInRange("curseOfFleshDuration", 2, 0, 20);
				curseOfFleshBonus = builder.comment("Curse of Flesh max health bonus")
						.defineInRange("curseOfFleshBonus", 1d, 0, 100);
				curseOfMetabolismThreshold = builder.comment("Curse of Metabolism food bar threshold")
						.defineInRange("curseOfMetabolismThreshold", 16, 0, 20);
				curseOfMetabolismFactor = builder.comment("Curse of Metabolism buff / debuff per food bar")
						.defineInRange("curseOfMetabolismFactor", 0.05d, 0, 1);
				curseOfMetabolismBonus = builder.comment("Curse of Metabolism max food bonus")
						.defineInRange("curseOfMetabolismBonus", 0.2d, 0, 10);
				curseOfMetabolismIndexReq = builder.comment("Curse of Metabolism reality index requirement for bonus")
						.defineInRange("curseOfMetabolismIndexReq", 5, 0, 7);
				curseOfTensionPenaltyDuration = builder.comment("Curse of Tension penalty duration in ticks when hit by target")
						.defineInRange("curseOfTensionPenaltyDuration", 60, 0, 2000);
				curseOfTensionPenaltyThreshold = builder.comment("Curse of Tension penalty threshold as percentage of max health")
						.defineInRange("curseOfTensionPenaltyThreshold", 0.1, 0, 1);
				curseOfTensionTokenMatureTime = builder.comment("Curse of Tension time for token to take effect")
						.defineInRange("curseOfTensionTokenMatureTime", 200, 0, 2000);
				curseOfTensionTokenEffectiveTime = builder.comment("Curse of Tension time for token to be effective")
						.defineInRange("curseOfTensionTokenEffectiveTime", 200, 0, 2000);
				curseOfTensionDamageBonus = builder.comment("Curse of Tension damage bonus per token")
						.defineInRange("curseOfTensionDamageBonus", 0.2, 0, 100);
				curseOfTensionMaxLevel = builder.comment("Curse of Tension max token level")
						.defineInRange("curseOfTensionMaxLevel", 5, 1, 100);
				curseOfPrudenceMaxLevel = builder.comment("Curse of Prudence max token level")
						.defineInRange("curseOfPrudenceMaxLevel", 20, 1, 100);
				curseOfPrudenceDamageFactor = builder.comment("Curse of Prudence damage factor per token, 0.9 means -10% damage per token")
						.defineInRange("curseOfPrudenceDamageFactor", 0.5, 0, 1);
				curseOfPrudenceDuration = builder.comment("Curse of Prudence token duration in ticks")
						.defineInRange("curseOfPrudenceDuration", 40, 1, 200);
				curseOfPrudenceMaxDamage = builder.comment("Curse of Prudence max damage as percentage of target max health")
						.defineInRange("curseOfPrudenceMaxDamage", 0.2, 0, 1);
				curseOfSpellLoadFactor = builder.comment("Curse of Spell: factor for spell load calculation.")
						.comment("Lower number means enchantment puts less load on items and player")
						.defineInRange("curseOfSpellLoadFactor", 1d, 0, 100);
				curseOfSpellDamageFactor = builder.comment("Curse of Spell: player will take more damage when having spell overload")
						.comment("Higher number means player takes more damage")
						.defineInRange("curseOfSpellDamageFactor", 1d, 0, 100);
				curseOfSpellWeakenFactor = builder.comment("Curse of Spell: player will deal less damage when having spell overload")
						.comment("Higher number means mob takes less damage")
						.defineInRange("curseOfSpellWeakenFactor", 1d, 0, 100);

				curseOfInertiaSlot = builder.comment("Curse of Inertia: bonus slot")
						.define("curseOfInertiaSlot", "necklace#1");
				curseOfProximitySlot = builder.comment("Curse of Proximity: bonus slot")
						.define("curseOfProximitySlot", "bracelet#1");
				curseOfFleshSlot = builder.comment("Curse of Flesh: bonus slot")
						.define("curseOfFleshSlot", "curio#1");
				curseOfTensionSlot = builder.comment("Curse of Tension: bonus slot")
						.define("curseOfTensionSlot", "hands#1");
				curseOfPrudenceSlot = builder.comment("Curse of Prudence: bonus slot")
						.define("curseOfPrudenceSlot", "charm#3");
				curseOfSpellSlot = builder.comment("Curse of Spell: bonus slot")
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
				builder.push("Angelic");

				angelicWingRealityIndex = builder.comment("Reality Index requirement for Angelic Wing")
						.defineInRange("angelicWingRealityIndex", 3, 0, 7);
				angelicWingBoost = builder.comment("Angelic Wing speed boost in block per tick")
						.defineInRange("angelicWingBoost", 0.03, 0, 0.1);
				angelicWingMaxSpeed = builder.comment("Angelic Wing max speed in block per tick")
						.defineInRange("angelicWingMaxSpeed", 5d, 0, 50);
				angelicBlessRealityIndex = builder.comment("Reality Index requirement for Angelic Bless")
						.defineInRange("angelicBlessRealityIndex", 4, 0, 7);
				angelicBlessDamageReduction = builder.comment("Angelic Bless total damage reduction")
						.defineInRange("angelicBlessDamageReduction", 0.25, 0, 1);
				angelicBlessAbsorption = builder.comment("Angelic Bless damage absorption")
						.defineInRange("angelicBlessAbsorption", 1d, 0, 100);
				angelicDescentRealityIndex = builder.comment("Reality Index requirement for Angelic Descent")
						.defineInRange("angelicDescentRealityIndex", 5, 0, 7);
				angelicDescentMeleeBonus = builder.comment("Angelic Descent melee damage bonus")
						.defineInRange("angelicDescentMeleeBonus", 0.5, 0, 10);
				angelicDescentUndeadBonus = builder.comment("Angelic Descent damage bonus against undead")
						.defineInRange("angelicDescentUndeadBonus", 0.5, 0, 10);
				angelicProtectionRealityIndex = builder.comment("Requirement of Reality Index of Angelic Protection")
						.defineInRange("angelicProtectionRealityIndex", 6, 0, 7);
				angelicProtectionCoolDown = builder.comment("Angelic Protection cool down in ticks")
						.defineInRange("angelicProtectionCoolDown", 600, 0, 1000000);
				angelicPunishmentRealityIndex = builder.comment("Requirement of Reality Index of Angelic Punishment")
						.defineInRange("angelicPunishmentRealityIndex", 7, 0, 7);
				angelicPunishmentCoolDown = builder.comment("Angelic Punishment cool down in ticks")
						.defineInRange("angelicPunishmentCoolDown", 20, 0, 6000);
				angelicPunishmentDamageBase = builder.comment("Angelic Punishment minimum damage in percentage of target current health")
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
				builder.push("Hell");

				hellfireSkullRealityIndex = builder.comment("Reality Index requirement for Hellfire Skull")
						.defineInRange("hellfireSkullRealityIndex", 3, 0, 7);
				hellfireSkullMinimumDuration = builder.comment("Minimum soul flame effect duration in ticks for Hellfire Skull to work")
						.defineInRange("hellfireSkullMinimumDuration", 200, 1, 200000);
				hellfireReformationRealityIndex = builder.comment("Reality Index requirement for Hellfire Reformation")
						.defineInRange("hellfireReformationRealityIndex", 4, 0, 7);
				eyeOfCursedSoulRealityIndex = builder.comment("Reality Index requirement for Eye of Cursed Souls")
						.defineInRange("eyeOfCursedSoulRealityIndex", 5, 0, 7);
				eyeOfCursedSoulRange = builder.comment("Eye of Cursed Souls damage radius")
						.defineInRange("eyeOfCursedSoulRange", 8d, 0, 64);
				eyeOfCursedSoulCoolDown = builder.comment("Effect trigger cool down in ticks for Eye of Cursed Souls")
						.defineInRange("eyeOfCursedSoulCoolDown", 200, 0, 1000000);
				cursedSoulCrystalRealityIndex = builder.comment("Reality Index requirement for Cursed Soul Crystal")
						.defineInRange("cursedSoulCrystalRealityIndex", 6, 0, 7);
				cursedSoulCrystalTriggerCoolDown = builder.comment("Effect trigger cool down in ticks for Cursed Soul Crystal")
						.defineInRange("cursedSoulCrystalTriggerCoolDown", 200, 0, 1000000);
				cursedSoulCrystalRange = builder.comment("Max range to search undead mobs for Cursed Soul Crystal")
						.defineInRange("cursedSoulCrystalRange", 8d, 0, 64);
				crownOfDemonRealityIndex = builder.comment("Reality Index requirement for Crown of Demon")
						.defineInRange("crownOfDemonRealityIndex", 7, 0, 7);
				crownOfDemonBaseHealthThreshold = builder.comment("Crown of Demon can command undead mobs with base health lower than:")
						.defineInRange("crownOfDemonBaseHealthThreshold", 50, 1, 1000000);
				crownOfDemonRange = builder.comment("Crown of Demon range of detection")
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
				builder.push("Shadow");
				shadowCoreRealityIndex = builder.comment("Reality Index requirement for Shadow Core")
						.defineInRange("shadowCoreRealityIndex", 3, 0, 7);
				shadowCoreDurationPerIndex = builder.comment("Shadow effect duration per reality index")
						.defineInRange("shadowCoreDurationPerIndex", 100, 0, 60000);
				damageReduction = builder.comment("Damage reduction for shadow effect")
						.defineInRange("damageReduction", 0.2, 0, 1);
				shadowConvergenceRealityIndex = builder.comment("Reality Index requirement for Shadow Convergence")
						.defineInRange("shadowConvergenceRealityIndex", 4, 0, 7);
				shadowConvergenceHealFactor = builder.comment("Shadow Convergence healing factor")
						.defineInRange("shadowConvergenceHealFactor", 0.5d, 0, 100d);
				shadowConsolidationRealityIndex = builder.comment("Reality Index requirement for Shadow Consolidation")
						.defineInRange("shadowConsolidationRealityIndex", 5, 0, 7);
				shadowConsolidationRange = builder.comment("Shadow Consolidation scatter range")
						.defineInRange("shadowConsolidationRange", 8d, 0, 64);
				shadowConsolidationFactor = builder.comment("Shadow Consolidation damage factor")
						.defineInRange("shadowConsolidationFactor", 1d, 0, 100);
				shadowConsolidationDelay = builder.comment("Shadow Consolidation damage delay")
						.defineInRange("shadowConsolidationDelay", 20, 0, 100);
				shadowConsolidationCoolDown = builder.comment("Shadow Consolidation trigger cool down")
						.defineInRange("shadowConsolidationCoolDown", 60, 0, 10000);
				shadowReformationRealityIndex = builder.comment("Reality Index requirement for Shadow Reformation")
						.defineInRange("shadowReformationRealityIndex", 6, 0, 7);
				shadowReformationBonus = builder.comment("Shadow Reformation magic damage bonus")
						.defineInRange("shadowReformationBonus", 0.5, 0, 100);
				shadowReformationReduction = builder.comment("Shadow Reformation damage reduction")
						.defineInRange("shadowReformationReduction", 0.5, 0, 1);
				voidOverflowRealityIndex = builder.comment("Reality Index requirement for Void Reformation")
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
				builder.push("Elemental");
				windThrustRealityIndex = builder.comment("Reality Index requirement for Wind Thrust")
						.defineInRange("windThrustRealityIndex", 4, 0, 7);
				windThrustSpeed = builder.comment("Speed bonus for Wind Thrust")
						.defineInRange("windThrustSpeed", 0.5, 0, 10);
				windThrustDamage = builder.comment("Melee damage bonus for Wind Thrust")
						.defineInRange("windThrustDamage", 1d, 0, 10);
				earthCrushRealityIndex = builder.comment("Reality Index requirement for Earth Crush")
						.defineInRange("earthCrushRealityIndex", 4, 0, 7);
				earthCrushThreshold = builder.comment("Attack speed threshold for Earth Crush")
						.defineInRange("earthCrushThreshold", 1d, 0, 4);
				earthCrushBonus = builder.comment("Explosion damage bonus for Earth Crush")
						.defineInRange("earthCrushBonus", 1d, 0, 100);
				flamingExplosionRealityIndex = builder.comment("Reality Index requirement for Flaming Explosion")
						.defineInRange("flamingExplosionRealityIndex", 4, 0, 7);
				flamingExplosionBonus = builder.comment("Explosion damage bonus for Flaming Explosion")
						.defineInRange("flamingExplosionBonus", 1d, 0, 100);
				wavingSpellRealityIndex = builder.comment("Reality Index requirement for Waving Spell")
						.defineInRange("wavingSpellRealityIndex", 4, 0, 7);
				wavingSpellBonus = builder.comment("Magic damage bonus for Waving Spell")
						.defineInRange("wavingSpellBonus", 1d, 0, 100);
				curseRedirectionRealityIndex = builder.comment("Reality Index requirement for Curse Redirection")
						.defineInRange("curseRedirectionRealityIndex", 4, 0, 7);
				curseRedirectionBonus = builder.comment("Magic damage bonus per curse enchantment for Curse Redirection")
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
				builder.push("Abyssal");
				abyssalDepthStep = builder.comment("Depth requirement for Abyssal charms")
						.defineInRange("abyssalDepthStep", 12, 0, 64);
				abyssalWillDepthStep = builder.comment("Depth requirement for Abyssal charms with Abyssal Will")
						.defineInRange("abyssalWillDepthStep", 8, 0, 64);
				abyssalTreasureRealityIndex = builder.comment("Reality Index requirement for Abyssal Treasure")
						.defineInRange("abyssalTreasureRealityIndex", 3, 0, 7);
				abyssalWatcherRealityIndex = builder.comment("Reality Index requirement for Abyssal Watcher")
						.defineInRange("abyssalWatcherRealityIndex", 4, 0, 7);
				abyssalShellRealityIndex = builder.comment("Reality Index requirement for Abyssal Shell")
						.defineInRange("abyssalShellRealityIndex", 5, 0, 7);
				abyssalCrownRealityIndex = builder.comment("Reality Index requirement for Abyssal Crown")
						.defineInRange("abyssalCrownRealityIndex", 6, 0, 7);
				abyssalWillRealityIndex = builder.comment("Reality Index requirement for Abyssal Will")
						.defineInRange("abyssalWillRealityIndex", 7, 0, 7);
				abyssalWatcherRegen = builder.comment("Abyssal Watcher regen per depth step")
						.defineInRange("abyssalWatcherRegen", 0.01, 0, 1);
				abyssalShellBonus = builder.comment("Abyssal Shell armor and toughness bonus per depth step")
						.defineInRange("abyssalShellBonus", 0.2, 0, 1);
				abyssalCrownChance = builder.comment("Abyssal Crown magic bypassing chance per depth step")
						.defineInRange("abyssalCrownChance", 0.05, 0, 1);
				abyssalWillCoolDown = builder.comment("Abyssal Will totem effect cool down in ticks")
						.defineInRange("abyssalWillCoolDown", 600, 1, 100000);
				abyssalWillDuration = builder.comment("Abyssal Will protection effect duration in ticks")
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
				builder.push("Barbaric");
				magicDamageDebuff = builder.comment("Magic damage reduction in percentage for all barbaric set")
						.defineInRange("magicDamageDebuff", 1d, 0, 100);
				barbaricInstinctRealityIndex = builder.comment("Reality Index requirement for Barbaric Instinct")
						.defineInRange("barbaricInstinctRealityIndex", 3, 0, 7);
				barbaricInstinctHeal = builder.comment("Barbaric Instinct healing percentage")
						.defineInRange("barbaricInstinctHeal", 0.1, 0, 1);
				barbaricInstinctCoolDown = builder.comment("Barbaric Instinct healing cool down")
						.defineInRange("barbaricInstinctCoolDown", 40, 0, 1000);
				barbaricWrathRealityIndex = builder.comment("Reality Index requirement for Barbaric Wrath")
						.defineInRange("barbaricWrathRealityIndex", 4, 0, 7);
				barbaricWrathCritBonus = builder.comment("Barbaric Wrath crit damage bonus")
						.defineInRange("barbaricWrathCritBonus", 0.5, 0, 10);
				barbaricWrathToughBonus = builder.comment("Barbaric Wrath armor toughness bonus")
						.defineInRange("barbaricWrathToughBonus", 10d, 0, 1000);
				barbaricRoarRealityIndex = builder.comment("Reality Index requirement for Barbaric Scare")
						.defineInRange("barbaricScareRealityIndex", 5, 0, 7);
				barbaricRoarAttack = builder.comment("Barbaric Roar attack damage bonus")
						.defineInRange("barbaricRoarAttack", 0.25, 0, 10);
				barbaricRoarReduction = builder.comment("Barbaric Roar damage reduction")
						.defineInRange("barbaricRoarReduction", 0.25, 0, 1);
				barbaricBladeRealityIndex = builder.comment("Reality Index requirement for Barbaric Blade")
						.defineInRange("barbaricBladeRealityIndex", 6, 0, 7);
				barbaricBladeAttack = builder.comment("Barbaric Blade attack damage bonus")
						.defineInRange("barbaricBladeAttack", 0.5, 0, 10);
				barbaricBladeProjectile = builder.comment("Barbaric Blade projectile damage bonus")
						.defineInRange("barbaricBladeProjectile", 0.5, 0, 10);
				primalForceRealityIndex = builder.comment("Reality Index requirement for Primal Force")
						.defineInRange("primalForceRealityIndex", 7, 0, 7);
				primalForceSelfArmor = builder.comment("Primal Force armor bonus for player as percentage of max health")
						.defineInRange("primalForceSelfArmor", 1d, 0, 100);
				primalForceTargetArmor = builder.comment("Primal Force armor bonus for attacker as percentage of max health")
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
				builder.push("Mutation");
				infectiveMutationRealityIndex = builder.comment("Reality Index requirement for Infective Mutation")
						.defineInRange("infectiveMutationRealityIndex", 3, 0, 7);
				parasiticMutationRealityIndex = builder.comment("Reality Index requirement for Parasitic Mutation")
						.defineInRange("parasiticMutationRealityIndex", 4, 0, 7);
				deformingMutationRealityIndex = builder.comment("Reality Index requirement for Deforming Mutation")
						.defineInRange("deformingMutationRealityIndex", 5, 0, 7);
				hostileMutationRealityIndex = builder.comment("Reality Index requirement for Hostile Mutation")
						.defineInRange("hostileMutationRealityIndex", 6, 0, 7);
				distortedMutationRealityIndex = builder.comment("Reality Index requirement for Distorted Mutation")
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
				builder.push("Evil");
				evilSpiritRitualRealityIndex = builder.comment("Reality Index requirement for Evil Spirit Ritual")
						.defineInRange("infectiveMutationRealityIndex", 3, 0, 7);
				evilSpiritRitualExpRate = builder.comment("Evil Spirit Ritual extra exp drop as percentage of max health")
						.defineInRange("evilSpiritRitualExpRate", 0.5, 0, 100);
				evilSpiritEvokeRealityIndex = builder.comment("Reality Index requirement for Evil Spirit Evoke")
						.defineInRange("parasiticMutationRealityIndex", 4, 0, 7);
				evilSpiritEvokeDuration = builder.comment("Evil Spirit Evoke summoned Vex lifetime")
						.defineInRange("evilSpiritEvokeDuration", 400, 0, 100000);
				evilSpiritEvokeCoolDown = builder.comment("Evil Spirit Evoke summoning cool down")
						.defineInRange("evilSpiritEvokeCoolDown", 60, 0, 10000);
				evilSpiritAwakeningRealityIndex = builder.comment("Reality Index requirement for Evil Spirit Awakening")
						.defineInRange("deformingMutationRealityIndex", 5, 0, 7);
				evilSpiritAwakeningMagicBonus = builder.comment("Evil Spirit Awakening magic damage bonus")
						.defineInRange("evilSpiritAwakeningMagicBonus", 0.15, 0, 10);
				evilSpiritAwakeningReduction = builder.comment("Evil Spirit Awakening damage reduction")
						.defineInRange("evilSpiritAwakeningAtkBonus", 0.1, 0, 10);
				evilSpiritAwakeningDuration = builder.comment("Evil Spirit Awakening bonus duration")
						.defineInRange("evilSpiritAwakeningDuration", 600, 0, 1000000);
				evilSpiritAwakeningMaxLevel = builder.comment("Evil Spirit Awakening bonus max level")
						.defineInRange("evilSpiritAwakeningMaxLevel", 5, 0, 1000);
				evilSpiritCurseRealityIndex = builder.comment("Reality Index requirement for Evil Spirit Curse")
						.defineInRange("hostileMutationRealityIndex", 6, 0, 7);
				evilSpiritCurseThreshold = builder.comment("Evil Spirit Curse health threshold")
						.defineInRange("evilSpiritCurseThreshold", 0.2, 0, 10);
				evilSpiritCurseBonus = builder.comment("Evil Spirit Curse magic damage bonus")
						.defineInRange("evilSpiritCurseBonus", 1d, 0, 10);
				evilSpiritWalkRealityIndex = builder.comment("Reality Index requirement for Evil Spirit Walk")
						.defineInRange("distortedMutationRealityIndex", 7, 0, 7);
				evilSpiritWalkMagicBonus = builder.comment("Evil Spirit Walk magic damage bonus")
						.defineInRange("evilSpiritWalkMagicBonus", 0.5, 0, 10);
				evilSpiritWalkAtkBonus = builder.comment("Evil Spirit Walk attack damage bonus")
						.defineInRange("evilSpiritWalkAtkBonus", 0.5, 0, 10);
				evilSpiritWalkCoolDown = builder.comment("Evil Spirit Walk cool down")
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
				builder.push("Weapon");
				angelicJudgementRealityIndex = builder.comment("Reality Index requirement for Angelic Judgement")
						.defineInRange("angelicJudgementRealityIndex", 5, 0, 7);
				doomStarRealityIndex = builder.comment("Reality Index requirement for Doom Star")
						.defineInRange("doomStarRealityIndex", 5, 0, 7);
				cursedKarmaRealityIndex = builder.comment("Reality Index requirement for Cursed Karma")
						.defineInRange("cursedKarmaRealityIndex", 5, 0, 7);
				cursedKarmaExplosionRadius = builder.comment("Cursed Karma explosion radius")
						.defineInRange("cursedKarmaExplosionRadius", 3, 0, 8);
				cursedKarmaEffectDuration = builder.comment("Cursed Karma effect duration")
						.defineInRange("cursedKarmaEffectDuration", 300, 0, 1000000);
				abyssalEdgeRealityIndex = builder.comment("Reality Index requirement for Abyssal Edge")
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
				builder.push("Compat");
				allowRealityTrait = builder.comment("L2Hostility compat: allow Reality trait")
						.define("allowRealityTrait", true);

				sealOfSwordDifficultyPerBonus = builder.comment("Seal of Swords: player difficulty required per reality index bonus")
						.defineInRange("sealOfSwordDifficultyPerBonus", 500, 1, 10000);
				sealOfSwordMaxRealityBonus = builder.comment("Seal of Swords: max reality index bonus")
						.defineInRange("sealOfSwordMaxRealityBonus", 3, 1, 10000);
				spellSingularitySpellBonusPerReality = builder.comment("Spell Singularity: Spell Tolerance per reality index")
						.defineInRange("spellSingularitySpellBonusPerReality", 0.5, 0, 5);
				spellSingularityMagicDamageBonusPerReality = builder.comment("Spell Singularity: Magic Damage bonus per reality index")
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

		Common(Builder builder) {
			builder.push("Loot");
			lootLuckFactor = builder.comment("Scale up/down luck in calculation of chance to find a pandora charm in chest")
					.defineInRange("lootLuckFactor", 1d, 0, 10);
			maxItemGenerated = builder.comment("Maximum number of pandora charms generated in loot chest")
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
	public static final Common COMMON;

	static {
		CLIENT = CurseOfPandora.REGISTRATE.registerClient(Client::new);
		COMMON = CurseOfPandora.REGISTRATE.registerSynced(Common::new);
	}

	public static void init() {
	}

}
