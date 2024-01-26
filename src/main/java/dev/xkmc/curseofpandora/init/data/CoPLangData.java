package dev.xkmc.curseofpandora.init.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.Locale;

public class CoPLangData {

	public enum IDS {
		EFFECT_REFRESH_CURIO("tooltip.misc.effect_refresh", "Grants wearer: ", 0),
		BIND("tooltip.misc.bind", "This item cannot be taken down.", 0),
		REALITY_INDEX("tooltip.misc.reality", "When you have at least %s Reality Index:", 1),
		;

		final String id, def;
		final int count;

		IDS(String id, String def, int count) {
			this.id = id;
			this.def = def;
			this.count = count;
		}

		public MutableComponent get(Object... objs) {
			if (objs.length != count)
				throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
			return translate(CurseOfPandora.MODID + "." + id, objs);
		}

	}

	public enum Reality {
		INERTIA("Negate all other non-tool attack speed bonus. Cap player attack speed to %s. When player attack speed is %s or lower, grant %s%% attack speed bonus.", 3),
		PROXIMITY("Negate all other non-tool attack reach bonus. Cap player attack reach to %s. When player attack reach is %s or lower, grant %s%% attack reach bonus.", 3),
		FLESH("Negate all max health bonus. If player maintains at least %s food level for %s minutes, grant +%s%% max health.", 3),
		METABOLISM_1("Gain %s%% attack and speed per food level above %s.", 2),
		METABOLISM_2("Loss %s%% attack, speed, and attack speed per food level below %s.", 2),
		METABOLISM_3("When food level is full, gain %s%% attack and speed, and negate all speed reduction", 1),
		TENSION_1("When you hit a target, place a Terror Token on it. Tokens take %s seconds to mature, then increase your damage to it by %s%% for %s seconds, stacking to at most %s levels.", 4),
		TENSION_2("When a mob deals you a damage higher than %s%% of your current health, all Terror Token from you breaks, and you cannot deal damage to that target for %s seconds", 2),
		PRUDENCE_1("When you hit a target, your damage to the same target for the next %s second will -%s%% (stackable).", 2),
		PRUDENCE_2("Your damage dealt against a target cannot exceed %s%% of target max health.", 1),
		SPELL_1("Enchantments on your weapons and armors will put a burden on you, increase damage you take and reduce damage you dealt. Use equipments with high enchantment affinity and gain spell tolerance to mitigate that.", 0),
		SPELL_2("Current total spell overload: %s%%", 1),
		SPELL_3("Item spell load: %s%%", 1),
		;

		final String id, def;
		final int count;

		Reality(String def, int count) {
			this.id = name().toLowerCase(Locale.ROOT);
			this.def = def;
			this.count = count;
		}

		public MutableComponent get(Object... objs) {
			if (objs.length != count)
				throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
			return translate(CurseOfPandora.MODID + ".tooltip.curse." + id, objs);
		}

	}

	public enum Angelic {
		CHECK("Effective only when player is under sky", 0),
		WING("When player is elytra flying, gives player a velocity boost.", 0),
		WING_IMMUNE("Player takes no damage for falling and flying into wall.", 0),
		DESCENT("All damage against undead mobs is increased by %s%%.", 1),
		PROTECTION("Works as an unbreakable totem of undying with a cool down of %s seconds.", 1),
		PUNISHMENT_1("All Angelic pandora charms triggers when not under sky as well.", 0),
		PUNISHMENT_2("Damage dealt to target will be at least %s%% of their current health. This effect can trigger at most once every %s seconds.", 2),
		;

		final String id, def;
		final int count;

		Angelic(String def, int count) {
			this.id = name().toLowerCase(Locale.ROOT);
			this.def = def;
			this.count = count;
		}

		public MutableComponent get(Object... objs) {
			if (objs.length != count)
				throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
			return translate(CurseOfPandora.MODID + ".tooltip.angel." + id, objs);
		}

	}

	public enum Hell {
		SKULL("When you damage a target on Soul Flame effect with at least %s seconds duration, increase the effect level by 1, capped at your Reality Index.", 1),
		REFORMATION_1("You are immune to Soul Flame damage, but you are not immune to the effect even when you have Cleansed effect. ", 0),
		REFORMATION_2("When you deal damage to targets while you are one Soul Flame effect, inflict the target with same level and duration of Soul Flame", 0),
		EYE("When you damage a target, the target will deal a small magic damage to all monsters around it within %s blocks. Has a cool down of %s seconds.", 2),
		;

		final String id, def;
		final int count;

		Hell(String def, int count) {
			this.id = name().toLowerCase(Locale.ROOT);
			this.def = def;
			this.count = count;
		}

		public MutableComponent get(Object... objs) {
			if (objs.length != count)
				throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
			return translate(CurseOfPandora.MODID + ".tooltip.hell." + id, objs);
		}

	}

	public enum Shadow {
		CORE_1("Inflict shadow effect on attack target with duration of %s seconds per your Reality Index.", 1),
		CORE_2("Shadow effect will reduce damage mobs dealt by %s%%", 1),
		CONVERGENCE("Heal %s%% of the damage you dealt to target with Shadow effect", 1),
		CONSOLIDATION("When you hit targets with shadow effect, after %3$s second, their shadow will scatter and attack surrounding mobs within %1$s blocks, dealing %2$s%% of original damage dealt. This effect can trigger once every %4$s seconds.",4),
		REFORMATION_1("+%s%% magic damage against target with Shadow effect", 1),
		REFORMATION_2("-%s%% physical damage from mobs with Shadow effect", 1),
		VOID("Shadow damage you dealt becomes void shadow damage.", 0);

		final String id, def;
		final int count;

		Shadow(String def, int count) {
			this.id = name().toLowerCase(Locale.ROOT);
			this.def = def;
			this.count = count;
		}

		public MutableComponent get(Object... objs) {
			if (objs.length != count)
				throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
			return translate(CurseOfPandora.MODID + ".tooltip.shadow." + id, objs);
		}

	}

	public enum Elemental {
		WIND_1("Effective only when player is sprinting", 0),
		WIND_2("Automatically attack aimed targets", 0),
		EARTH("Effective only when player attack speed is %s or lower", 1),
		WAVING("Effective only when player is in water or rain", 0),
		;

		final String id, def;
		final int count;

		Elemental(String def, int count) {
			this.id = name().toLowerCase(Locale.ROOT);
			this.def = def;
			this.count = count;
		}

		public MutableComponent get(Object... objs) {
			if (objs.length != count)
				throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
			return translate(CurseOfPandora.MODID + ".tooltip.elemental." + id, objs);
		}

	}

	public static void addTranslations(RegistrateLangProvider pvd) {
		for (var id : IDS.values()) {
			pvd.add(CurseOfPandora.MODID + "." + id.id, id.def);
		}
		for (var id : Reality.values()) {
			pvd.add(CurseOfPandora.MODID + ".tooltip.curse." + id.id, id.def);
		}
		for (var id : Angelic.values()) {
			pvd.add(CurseOfPandora.MODID + ".tooltip.angel." + id.id, id.def);
		}
		for (var id : Hell.values()) {
			pvd.add(CurseOfPandora.MODID + ".tooltip.hell." + id.id, id.def);
		}
		for (var id : Shadow.values()) {
			pvd.add(CurseOfPandora.MODID + ".tooltip.shadow." + id.id, id.def);
		}
		for (var id : Elemental.values()) {
			pvd.add(CurseOfPandora.MODID + ".tooltip.elemental." + id.id, id.def);
		}
		pvd.add("death.attack.soul_curse", "%s is cursed by evil souls");
		pvd.add("death.attack.soul_curse.player", "%s is cursed by %s's evil souls");
		pvd.add("death.attack.shadow_curse", "%s is cursed by shadow");
		pvd.add("death.attack.shadow_curse.player", "%s is cursed by %s's shadow");
		pvd.add("death.attack.void_curse", "%s is cursed by shadow");
		pvd.add("death.attack.void_curse.player", "%s is cursed by %s's shadow");
	}

	public static String asId(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	public static MutableComponent translate(String key, Object... objs) {
		return Component.translatable(key, objs);
	}

}
