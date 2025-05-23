package dev.xkmc.curseofpandora.init.data;

import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2damagetracker.init.data.DamageTypeAndTagsGen;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;

public class CoPDamageTypeGen extends DamageTypeAndTagsGen {

	public static final ResourceKey<DamageType> SOUL_CURSE = create("soul_curse",
			"%s is cursed by evil souls", "%s is cursed by %s's evil souls");
	public static final ResourceKey<DamageType> SHADOW_CURSE = create("shadow_curse",
			"%s is cursed by shadow", "%s is cursed by %s's shadow");
	public static final ResourceKey<DamageType> VOID_CURSE = create("void_curse",
			"%s is cursed by shadow", "%s is cursed by %s's shadow");
	public static final ResourceKey<DamageType> SPELL_CURSE = create("spell_curse",
			"%s is killed by abyssal fangs", "%s is killed by %s's abyssal fangs");
	public static final ResourceKey<DamageType> WIND_BLADE = create("wind_blade",
			"%s is killed by wind blade", "%s is killed by %s's wind blade");
	public static final ResourceKey<DamageType> ABYSSAL_FANG = create("abyssal_fang",
			"%s is killed by abyssal fangs", "%s is killed by %s's abyssal fangs");
	public static final ResourceKey<DamageType> ECHO_ABYSSAL_FANG = create("echo_abyssal_fang",
			"%s is killed by spell overload");

	public static void register() {

	}

	public static final TagKey<DamageType> SHADOW = TagKey.create(Registries.DAMAGE_TYPE, CurseOfPandora.loc("shadow"));

	public CoPDamageTypeGen(L2Registrate reg) {
		super(reg);
		new DamageTypeHolder(WIND_BLADE, new DamageType("wind_blade", DamageScaling.NEVER, 0.1F))
				.add(Tags.DamageTypes.IS_MAGIC, DamageTypeTags.AVOIDS_GUARDIAN_THORNS);
		new DamageTypeHolder(SOUL_CURSE, new DamageType("soul_curse", DamageScaling.NEVER, 0.1F))
				.add(Tags.DamageTypes.IS_MAGIC, DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.AVOIDS_GUARDIAN_THORNS);
		new DamageTypeHolder(SHADOW_CURSE, new DamageType("shadow_curse", DamageScaling.NEVER, 0.1F))
				.add(Tags.DamageTypes.IS_MAGIC, DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.AVOIDS_GUARDIAN_THORNS,
						DamageTypeTags.BYPASSES_COOLDOWN, SHADOW);
		new DamageTypeHolder(VOID_CURSE, new DamageType("void_curse", DamageScaling.NEVER, 0.1F))
				.add(Tags.DamageTypes.IS_MAGIC, DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.AVOIDS_GUARDIAN_THORNS,
						DamageTypeTags.BYPASSES_COOLDOWN, SHADOW).add(L2DamageTypes.BYPASS_MAGIC);
		new DamageTypeHolder(ABYSSAL_FANG, new DamageType("abyssal_fang", DamageScaling.NEVER, 0.1F))
				.add(Tags.DamageTypes.IS_MAGIC, DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.AVOIDS_GUARDIAN_THORNS,
						DamageTypeTags.BYPASSES_ENCHANTMENTS);
		new DamageTypeHolder(ECHO_ABYSSAL_FANG, new DamageType("echo_abyssal_fang", DamageScaling.NEVER, 0.1F))
				.add(Tags.DamageTypes.IS_MAGIC, DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.AVOIDS_GUARDIAN_THORNS)
				.add(L2DamageTypes.BYPASS_MAGIC);
		new DamageTypeHolder(SPELL_CURSE, new DamageType("spell_curse", DamageScaling.NEVER, 0))
				.add(DamageTypeTags.BYPASSES_ARMOR, DamageTypeTags.BYPASSES_COOLDOWN).add(L2DamageTypes.BYPASS_MAGIC);
	}

	public static Holder<DamageType> forKey(Level level, ResourceKey<DamageType> key) {
		return level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key);
	}

	private static ResourceKey<DamageType> create(String id, String msg) {
		return create(id, msg, msg);
	}

	private static ResourceKey<DamageType> create(String id, String msg, String player) {
		return create(id, msg, player, player);
	}

	private static ResourceKey<DamageType> create(String id, String msg, String player, String item) {
		CurseOfPandora.REGISTRATE.addRawLang("death.attack." + id, msg);
		CurseOfPandora.REGISTRATE.addRawLang("death.attack." + id + ".player", player);
		CurseOfPandora.REGISTRATE.addRawLang("death.attack." + id + ".item", item);
		return create(id);
	}

	private static ResourceKey<DamageType> create(String id) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, CurseOfPandora.loc(id));
	}
}
