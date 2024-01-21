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
		CURSE_INERTIA("tooltip.curse.inertia", "Negate all other non-tool attack speed bonus. Cap player attack speed to %s. When player attack speed is %s or lower, grant %s%% attack speed bonus.", 3),
		CURSE_PROXIMITY("tooltip.curse.proximity", "Negate all other non-tool attack reach bonus. Cap player attack reach to %s. When player attack reach is %s or lower, grant %s%% attack reach bonus.", 3),
		CURSE_FLESH("tooltip.curse.flesh", "Negate all max health bonus. If player maintains at least %s food level for %s minutes, grant +%s%% max health.", 3),
		CURSE_METABOLISM_1("tooltip.curse.metabolism_1", "Gain %s%% attack and speed per food level above %s.", 2),
		CURSE_METABOLISM_2("tooltip.curse.metabolism_2", "Loss %s%% attack, speed, and attack speed per food level below %s.", 2),
		CURSE_METABOLISM_3("tooltip.curse.metabolism_3", "When food level is full, gain %s%% attack and speed, and negate all speed reduction", 1),
		CURSE_TENSION_1("tooltip.curse.tension_1", "When you hit a target, place a Terror Token on it. Tokens take %s seconds to mature, then increase your damage to it by %s%% for %s seconds, stacking to at most %s levels.", 4),
		CURSE_TENSION_2("tooltip.curse.tension_2", "When a mob deals you a damage higher than %s%% of your current health, all Terror Token from you breaks, and you cannot deal damage to that target for %s seconds", 2),
		CURSE_PRUDENCE_1("tooltip.curse.prudence_1", "When you hit a target, your damage to the same target for the next %s second will -%s%% (stackable).", 2),
		CURSE_PRUDENCE_2("tooltip.curse.prudence_2", "Your damage dealt against a target cannot exceed %s%% of target max health.", 1),
		CURSE_OF_SPELL_1("tooltip.curse.spell_1", "Enchantments on your weapons and armors will put a burden on you, increase damage you take and reduce damage you dealt. Use equipments with high enchantment affinity and gain spell tolerance to mitigate that.", 0),
		CURSE_OF_SPELL_2("tooltip.curse.spell_2", "Current total spell overload: %s%%", 1),
		CURSE_OF_SPELL_3("tooltip.curse.spell_3", "Item spell load: %s%%", 1),

		ANGELIC_CHECK("tooltip.angel.check", "Effective only when player is under sky", 0),
		ANGELIC_WING("tooltip.angel.wing", "When player is elytra flying, gives player a velocity boost.", 0),
		ANGELIC_WING_IMMUNE("tooltip.angel.wing_immune", "Player takes no damage for falling and flying into wall.", 0),
		ANGELIC_BLESS("tooltip.angel.bless", "All damage taken is reduced by %s%%.", 1),
		ANGELIC_DESCENT("tooltip.angel.descent", "All damage against undead mobs is increased by %s%%.", 1),
		ANGELIC_PROTECTION("tooltip.angel.protection", "Works as an unbreakable totem of undying with a cool down of %s seconds.", 1),
		ANGELIC_PUNISHMENT_1("tooltip.angel.punishment_1", "All Angelic pandora charms triggers when not under sky as well.", 0),
		ANGELIC_PUNISHMENT_2("tooltip.angel.punishment_2", "Damage dealt to target will be at least %s%% of their current health. This effect can trigger at most once every %s second.", 2),


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

	public static void addTranslations(RegistrateLangProvider pvd) {
		for (IDS id : IDS.values()) {
			pvd.add(CurseOfPandora.MODID + "." + id.id, id.def);
		}
	}

	public static String asId(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	public static MutableComponent translate(String key, Object... objs) {
		return Component.translatable(key, objs);
	}

}
