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
		CURSE_INERTIA("tooltip.misc.curse_inertia", "Negate all other non-tool attack speed bonus. Cap player attack speed to %s. When player attack speed is %s or lower, grant %s%% attack speed bonus.", 3),
		CURSE_PROXIMITY("tooltip.misc.curse_proximity", "Negate all other non-tool attack reach bonus. Cap player attack reach to %s. When player attack reach is %s or lower, grant %s%% attack reach bonus.", 3),
		CURSE_FLESH("tooltip.misc.curse_flesh", "Negate all max health bonus. If player maintains at least %s food level for %s minutes, grant +%s%% max health.", 3),
		CURSE_METABOLISM_1("tooltip.misc.curse_metabolism_1", "Gain %s%% attack and speed per food level above %s.", 2),
		CURSE_METABOLISM_2("tooltip.misc.curse_metabolism_2", "Loss %s%% attack, speed, and attack speed per food level below %s.", 2),
		CURSE_METABOLISM_3("tooltip.misc.curse_metabolism_3", "When food level is full, gain %s%% attack and speed, and negate all speed reduction", 1),
		CURSE_TENSION_1("tooltip.misc.curse_tension_1", "When you hit a target, place a Terror Token on it. Tokens take %s seconds to mature, then increase your damage to it by %s%% for %s seconds, stacking to at most %s levels.", 4),
		CURSE_TENSION_2("tooltip.misc.curse_tension_2", "When a mob deals you a damage higher than %s%% of your current health, all Terror Token from you breaks, and you cannot deal damage to that target for %s seconds", 2),
		CURSE_PRUDENCE_1("tooltip.misc.curse_prudence_1", "When you hit a target, your damage to the same target for the next %s second will -%s%% (stackable).", 2),
		CURSE_PRUDENCE_2("tooltip.misc.curse_prudence_2", "Your damage dealt against a target cannot exceed %s%% of target max health.", 1),
		CURSE_OF_SPELL_1("tooltip.misc.curse_of_spell_1", "Enchantments on your weapons and armors will put a burden on you, increase damage you take and reduce damage you dealt. Use equipments with high enchantment affinity and gain spell tolerance to mitigate that.", 0),
		CURSE_OF_SPELL_2("tooltip.misc.curse_of_spell_2", "Current total spell overload: %s%%", 1),
		CURSE_OF_SPELL_3("tooltip.misc.curse_of_spell_3", "Item spell load: %s%%", 1),

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
			String[] strs = id.id.split("\\.");
			String str = strs[strs.length - 1];
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
