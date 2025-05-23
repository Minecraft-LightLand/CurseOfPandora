package dev.xkmc.curseofpandora.init.registrate;

import dev.xkmc.curseofpandora.content.effect.EmptyEffect;
import dev.xkmc.curseofpandora.content.effect.FakeRenderEffect;
import dev.xkmc.curseofpandora.content.effect.ShadowEffect;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import static dev.xkmc.curseofpandora.init.CurseOfPandora.REGISTRATE;

public class CoPEffects {

	public static final SimpleEntry<MobEffect> FAKE_TERROR_PRE = REGISTRATE
			.effect("terror_token_pre", () -> new FakeRenderEffect(4),
					"For Render only. Shows premature Terror Token on target.")
			.lang(MobEffect::getDescriptionId).register();
	public static final SimpleEntry<MobEffect> FAKE_TERROR = REGISTRATE
			.effect("terror_token", () -> new FakeRenderEffect(4),
					"For render only. Shows Mature Terror Token count on target.")
			.lang(MobEffect::getDescriptionId).register();
	public static final SimpleEntry<MobEffect> FAKE_TERRORIZED = REGISTRATE
			.effect("terrorized", FakeRenderEffect::new,
					"For render only. Shows if you cannot deal damage against this target due to Curse of Tension effect.")
			.lang(MobEffect::getDescriptionId).register();
	public static final SimpleEntry<MobEffect> PRUDENCE = REGISTRATE
			.effect("prudence", () -> new FakeRenderEffect(4),
					"For render only. Shows if your damage is reduced due to Curse of Prudence effect.")
			.lang(MobEffect::getDescriptionId).register();

	public static final SimpleEntry<MobEffect> SHADOW = REGISTRATE
			.effect("shadow", () -> new ShadowEffect(MobEffectCategory.NEUTRAL, 0x000000),
					"Shadow Mark")
			.lang(MobEffect::getDescriptionId).register();

	public static final SimpleEntry<MobEffect> AWAKENING = REGISTRATE
			.effect("evil_spirit_awakening", () -> new EmptyEffect(MobEffectCategory.BENEFICIAL, 0x000000),
					"Evil Spirit Awakening")
			.lang(MobEffect::getDescriptionId).register();

	public static final SimpleEntry<MobEffect> SPIRIT_WALK = REGISTRATE
			.effect("evil_spirit_walk", () -> new EmptyEffect(MobEffectCategory.BENEFICIAL, 0x000000),
					"Evil Spirit Walk")
			.lang(MobEffect::getDescriptionId).register();

	public static final SimpleEntry<MobEffect> ABYSSAL_PROTECTION = REGISTRATE
			.effect("abyssal_protection", () -> new EmptyEffect(MobEffectCategory.BENEFICIAL, 0x000000),
					"Negates magic-bypassing damage when you have Abyssal Will")
			.lang(MobEffect::getDescriptionId).register();

	public static void register() {

	}

}
