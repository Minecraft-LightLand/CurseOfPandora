package dev.xkmc.curseofpandora.init.data;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class CoPConfig {

	public static class Client {

		Client(ForgeConfigSpec.Builder builder) {
		}

	}

	public static class Common {

		public static class Attr {

			public final ForgeConfigSpec.DoubleValue charmOfHealth;
			public final ForgeConfigSpec.DoubleValue charmOfArmor;
			public final ForgeConfigSpec.DoubleValue charmOfArmorToughness;
			public final ForgeConfigSpec.DoubleValue charmOfSpeed;
			public final ForgeConfigSpec.DoubleValue charmOfDamage;
			public final ForgeConfigSpec.DoubleValue charmOfCritical;
			public final ForgeConfigSpec.DoubleValue charmOfArchery;
			public final ForgeConfigSpec.DoubleValue charmOfHeavyWeapon;
			public final ForgeConfigSpec.DoubleValue charmOfHeavyWeaponSlow;
			public final ForgeConfigSpec.DoubleValue charmOfAccuracy;
			public final ForgeConfigSpec.DoubleValue charmOfAccuracySlow;
			public final ForgeConfigSpec.DoubleValue charmOfProtection;
			public final ForgeConfigSpec.DoubleValue charmOfMagic;
			public final ForgeConfigSpec.DoubleValue charmOfExplosion;
			public final ForgeConfigSpec.DoubleValue charmOfLuck;

			private Attr(ForgeConfigSpec.Builder builder) {
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
				builder.pop();
			}

		}

		public static class Curse {

			// reality
			public final ForgeConfigSpec.DoubleValue curseOfInertiaCap;
			public final ForgeConfigSpec.DoubleValue curseOfInertiaBase;
			public final ForgeConfigSpec.DoubleValue curseOfInertiaBonus;
			public final ForgeConfigSpec.DoubleValue curseOfProximityCap;
			public final ForgeConfigSpec.DoubleValue curseOfProximityBase;
			public final ForgeConfigSpec.DoubleValue curseOfProximityBonus;
			public final ForgeConfigSpec.IntValue curseOfFleshThreshold;
			public final ForgeConfigSpec.IntValue curseOfFleshDuration;
			public final ForgeConfigSpec.DoubleValue curseOfFleshBonus;
			public final ForgeConfigSpec.IntValue curseOfMetabolismThreshold;
			public final ForgeConfigSpec.DoubleValue curseOfMetabolismFactor;
			public final ForgeConfigSpec.DoubleValue curseOfMetabolismBonus;
			public final ForgeConfigSpec.IntValue curseOfMetabolismIndexReq;
			public final ForgeConfigSpec.IntValue curseOfTensionPenaltyDuration;
			public final ForgeConfigSpec.DoubleValue curseOfTensionPenaltyThreshold;
			public final ForgeConfigSpec.IntValue curseOfTensionTokenMatureTime;
			public final ForgeConfigSpec.IntValue curseOfTensionTokenEffectiveTime;
			public final ForgeConfigSpec.DoubleValue curseOfTensionDamageBonus;
			public final ForgeConfigSpec.IntValue curseOfTensionMaxLevel;
			public final ForgeConfigSpec.IntValue curseOfPrudenceMaxLevel;
			public final ForgeConfigSpec.DoubleValue curseOfPrudenceDamageFactor;
			public final ForgeConfigSpec.IntValue curseOfPrudenceDuration;
			public final ForgeConfigSpec.DoubleValue curseOfPrudenceMaxDamage;
			public final ForgeConfigSpec.DoubleValue curseOfSpellLoadFactor;
			public final ForgeConfigSpec.DoubleValue curseOfSpellDamageFactor;
			public final ForgeConfigSpec.DoubleValue curseOfSpellWeakenFactor;

			private Curse(ForgeConfigSpec.Builder builder) {
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

				builder.pop();
			}
		}

		public static class Angelic {

			// angelic
			public final ForgeConfigSpec.IntValue angelicWingRealityIndex;
			public final ForgeConfigSpec.DoubleValue angelicWingBoost;
			public final ForgeConfigSpec.DoubleValue angelicWingMaxSpeed;
			public final ForgeConfigSpec.IntValue angelicBlessRealityIndex;
			public final ForgeConfigSpec.DoubleValue angelicBlessDamageReduction;
			public final ForgeConfigSpec.DoubleValue angelicBlessAbsorption;
			public final ForgeConfigSpec.IntValue angelicDescentRealityIndex;
			public final ForgeConfigSpec.DoubleValue angelicDescentMeleeBonus;
			public final ForgeConfigSpec.DoubleValue angelicDescentUndeadBonus;
			public final ForgeConfigSpec.IntValue angelicProtectionRealityIndex;
			public final ForgeConfigSpec.IntValue angelicProtectionCoolDown;
			public final ForgeConfigSpec.IntValue angelicPunishmentRealityIndex;
			public final ForgeConfigSpec.IntValue angelicPunishmentCoolDown;
			public final ForgeConfigSpec.DoubleValue angelicPunishmentDamageBase;

			private Angelic(ForgeConfigSpec.Builder builder) {
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

			public final ForgeConfigSpec.IntValue hellfireSkullRealityIndex;
			public final ForgeConfigSpec.IntValue hellfireSkullMinimumDuration;
			public final ForgeConfigSpec.IntValue hellfireReformationRealityIndex;
			public final ForgeConfigSpec.IntValue eyeOfCursedSoulRealityIndex;
			public final ForgeConfigSpec.DoubleValue eyeOfCursedSoulRange;
			public final ForgeConfigSpec.IntValue eyeOfCursedSoulCoolDown;
			public final ForgeConfigSpec.IntValue cursedSoulCrystalRealityIndex;
			public final ForgeConfigSpec.IntValue cursedSoulCrystalTriggerCoolDown;
			public final ForgeConfigSpec.DoubleValue cursedSoulCrystalRange;
			public final ForgeConfigSpec.IntValue crownOfDemonRealityIndex;
			public final ForgeConfigSpec.IntValue crownOfDemonBaseHealthThreshold;
			public final ForgeConfigSpec.IntValue crownOfDemonRange;

			private Hell(ForgeConfigSpec.Builder builder) {
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

			public final ForgeConfigSpec.IntValue shadowCoreRealityIndex;
			public final ForgeConfigSpec.IntValue shadowCoreDurationPerIndex;
			public final ForgeConfigSpec.DoubleValue damageReduction;
			public final ForgeConfigSpec.IntValue shadowConvergenceRealityIndex;
			public final ForgeConfigSpec.DoubleValue shadowConvergenceHealFactor;
			public final ForgeConfigSpec.IntValue shadowConsolidationRealityIndex;
			public final ForgeConfigSpec.DoubleValue shadowConsolidationRange;
			public final ForgeConfigSpec.DoubleValue shadowConsolidationFactor;
			public final ForgeConfigSpec.IntValue shadowConsolidationDelay;
			public final ForgeConfigSpec.IntValue shadowConsolidationCoolDown;
			public final ForgeConfigSpec.IntValue shadowReformationRealityIndex;
			public final ForgeConfigSpec.DoubleValue shadowReformationBonus;
			public final ForgeConfigSpec.DoubleValue shadowReformationReduction;
			public final ForgeConfigSpec.IntValue voidOverflowRealityIndex;

			private Shadow(ForgeConfigSpec.Builder builder) {
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

			public final ForgeConfigSpec.IntValue windThrustRealityIndex;
			public final ForgeConfigSpec.DoubleValue windThrustSpeed;
			public final ForgeConfigSpec.DoubleValue windThrustDamage;
			public final ForgeConfigSpec.IntValue earthCrushRealityIndex;
			public final ForgeConfigSpec.DoubleValue earthCrushThreshold;
			public final ForgeConfigSpec.DoubleValue earthCrushBonus;
			public final ForgeConfigSpec.IntValue flamingExplosionRealityIndex;
			public final ForgeConfigSpec.DoubleValue flamingExplosionBonus;
			public final ForgeConfigSpec.IntValue wavingSpellRealityIndex;
			public final ForgeConfigSpec.DoubleValue wavingSpellBonus;
			public final ForgeConfigSpec.IntValue curseRedirectionRealityIndex;
			public final ForgeConfigSpec.DoubleValue curseRedirectionBonus;

			private Elemental(ForgeConfigSpec.Builder builder) {
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

			public final ForgeConfigSpec.IntValue abyssalTreasureRealityIndex;
			public final ForgeConfigSpec.IntValue abyssalWatcherRealityIndex;
			public final ForgeConfigSpec.IntValue abyssalShellRealityIndex;
			public final ForgeConfigSpec.IntValue abyssalCrownRealityIndex;
			public final ForgeConfigSpec.IntValue abyssalWillRealityIndex;
			public final ForgeConfigSpec.IntValue abyssalDepthRequirement;
			public final ForgeConfigSpec.IntValue abyssalWillDepthRequirement;
			public final ForgeConfigSpec.DoubleValue abyssalWatcherRegen;
			public final ForgeConfigSpec.DoubleValue abyssalShellBonus;
			public final ForgeConfigSpec.DoubleValue abyssalCrownChance;

			private Abyssal(ForgeConfigSpec.Builder builder) {
				builder.push("Abyssal");
				abyssalDepthRequirement = builder.comment("Depth requirement for Abyssal charms")
						.defineInRange("abyssalDepthRequirement", 12, 0, 64);
				abyssalWillDepthRequirement = builder.comment("Depth requirement for Abyssal charms with Abyssal Will")
						.defineInRange("abyssalDepthRequirement", 8, 0, 64);
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
				builder.pop();
			}

		}

		public static class Barbaric {

			public final ForgeConfigSpec.DoubleValue magicDamageDebuff;
			public final ForgeConfigSpec.IntValue barbaricInstinctRealityIndex;
			public final ForgeConfigSpec.DoubleValue barbaricInstinctHeal;
			public final ForgeConfigSpec.IntValue barbaricInstinctCoolDown;
			public final ForgeConfigSpec.IntValue barbaricWrathRealityIndex;
			public final ForgeConfigSpec.DoubleValue barbaricWrathCritBonus;
			public final ForgeConfigSpec.DoubleValue barbaricWrathToughBonus;
			public final ForgeConfigSpec.IntValue barbaricRoarRealityIndex;
			public final ForgeConfigSpec.DoubleValue barbaricRoarReduction;
			public final ForgeConfigSpec.DoubleValue barbaricRoarAttack;
			public final ForgeConfigSpec.IntValue barbaricBladeRealityIndex;
			public final ForgeConfigSpec.DoubleValue barbaricBladeProjectile;
			public final ForgeConfigSpec.DoubleValue barbaricBladeAttack;
			public final ForgeConfigSpec.IntValue primalForceRealityIndex;
			public final ForgeConfigSpec.DoubleValue primalForceSelfArmor;
			public final ForgeConfigSpec.DoubleValue primalForceTargetArmor;

			private Barbaric(ForgeConfigSpec.Builder builder) {
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

			public final ForgeConfigSpec.IntValue infectiveMutationRealityIndex;
			public final ForgeConfigSpec.IntValue parasiticMutationRealityIndex;
			public final ForgeConfigSpec.IntValue deformingMutationRealityIndex;
			public final ForgeConfigSpec.IntValue hostileMutationRealityIndex;
			public final ForgeConfigSpec.IntValue distortedMutationRealityIndex;

			private Mutation(ForgeConfigSpec.Builder builder) {
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

			public final ForgeConfigSpec.IntValue evilSpiritRitualRealityIndex;
			public final ForgeConfigSpec.IntValue evilSpiritEvokeRealityIndex;
			public final ForgeConfigSpec.IntValue evilSpiritAwakeningRealityIndex;
			public final ForgeConfigSpec.IntValue evilSpiritCurseRealityIndex;
			public final ForgeConfigSpec.IntValue evilSpiritWalkRealityIndex;

			private Evil(ForgeConfigSpec.Builder builder) {
				builder.push("Evil");
				evilSpiritRitualRealityIndex = builder.comment("Reality Index requirement for Evil Spirit Ritual")
						.defineInRange("infectiveMutationRealityIndex", 3, 0, 7);
				evilSpiritEvokeRealityIndex = builder.comment("Reality Index requirement for Evil Spirit Evoke")
						.defineInRange("parasiticMutationRealityIndex", 4, 0, 7);
				evilSpiritAwakeningRealityIndex = builder.comment("Reality Index requirement for Evil Spirit Awakening")
						.defineInRange("deformingMutationRealityIndex", 5, 0, 7);
				evilSpiritCurseRealityIndex = builder.comment("Reality Index requirement for Evil Spirit Curse")
						.defineInRange("hostileMutationRealityIndex", 6, 0, 7);
				evilSpiritWalkRealityIndex = builder.comment("Reality Index requirement for Evil Spirit Walk")
						.defineInRange("distortedMutationRealityIndex", 7, 0, 7);
				builder.pop();
			}

		}

		public static class Weapon {

			public final ForgeConfigSpec.IntValue angelicJudgementRealityIndex;
			public final ForgeConfigSpec.IntValue doomStarRealityIndex;
			public final ForgeConfigSpec.IntValue cursedKarmaRealityIndex;
			public final ForgeConfigSpec.IntValue cursedKarmaExplosionRadius;
			public final ForgeConfigSpec.IntValue cursedKarmaEffectDuration;
			public final ForgeConfigSpec.IntValue abyssalEdgeRealityIndex;

			private Weapon(ForgeConfigSpec.Builder builder) {
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

		Common(ForgeConfigSpec.Builder builder) {
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
		}

	}

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Client, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();

		final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}

	/**
	 * Registers any relevant listeners for config
	 */
	public static void init() {
		register(ModConfig.Type.CLIENT, CLIENT_SPEC);
		register(ModConfig.Type.COMMON, COMMON_SPEC);
	}

	private static void register(ModConfig.Type type, IConfigSpec<?> spec) {
		var mod = ModLoadingContext.get().getActiveContainer();
		String path = "l2_configs/" + mod.getModId() + "-" + type.extension() + ".toml";
		ModLoadingContext.get().registerConfig(type, spec, path);
	}


}
