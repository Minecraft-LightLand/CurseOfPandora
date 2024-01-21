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

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("Reality");
			{
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
			}
			builder.pop();
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
