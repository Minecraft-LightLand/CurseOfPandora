package dev.xkmc.curseofpandora.content.sets.evil;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.curseofpandora.init.registrate.CoPEffects;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EvilSpiritAwakening extends ITokenProviderItem<EvilSpiritAwakening.Data> {

	private static int getIndexReq() {
		return CoPConfig.COMMON.evil.evilSpiritAwakeningRealityIndex.get();
	}

	private static double prot() {
		return CoPConfig.COMMON.evil.evilSpiritAwakeningReduction.get();
	}

	private static double magic() {
		return CoPConfig.COMMON.evil.evilSpiritAwakeningMagicBonus.get();
	}

	private static int getDuration() {
		return CoPConfig.COMMON.evil.evilSpiritAwakeningDuration.get();
	}

	private static int getMaxLevel() {
		return CoPConfig.COMMON.evil.evilSpiritAwakeningMaxLevel.get();
	}

	public EvilSpiritAwakening(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Evil.AWAKENING.get(
				(int) Math.round(magic() * 100),
				(int) Math.round(prot() * 100),
				(int) Math.round(getDuration() / 20d),
				getMaxLevel()
		).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		private static AttrAdder magicAttr(int size) {
			return AttrAdder.of("evil_spirit_awakening", L2DamageTracker.MAGIC_FACTOR::get,
					AttributeModifier.Operation.ADDITION, magic() * size);
		}

		private static AttrAdder protAttr(int size) {
			return AttrAdder.of("evil_spirit_awakening", L2DamageTracker.REDUCTION,
					AttributeModifier.Operation.MULTIPLY_TOTAL, -prot() * size);
		}

		@SerialClass.SerialField
		public ArrayList<Long> list = new ArrayList<>();

		private boolean updated;

		@Override
		protected void removeImpl(Player player) {
			magicAttr(list.size()).removeImpl(player);
			protAttr(list.size()).removeImpl(player);
			player.removeEffect(CoPEffects.AWAKENING.get());
		}

		@Override
		protected void tickImpl(Player player) {
			if (player.level().isClientSide()) return;
			var eff = CoPEffects.AWAKENING.get();
			if (list.isEmpty()) {
				if (player.hasEffect(eff))
					player.removeEffect(eff);
				magicAttr(list.size()).removeImpl(player);
				protAttr(list.size()).removeImpl(player);
				return;
			}
			long start = player.level().getGameTime() - getDuration();
			if (list.get(0) < start) {
				list.remove(0);
			}
			while (list.size() > 5) {
				list.remove(0);
			}
			magicAttr(list.size()).tickImpl(player);
			protAttr(list.size()).tickImpl(player);
			var ins = player.getEffect(eff);
			int lv = ins == null ? 0 : ins.getAmplifier() + 1;
			int level = Math.min(getMaxLevel(), list.size());
			if (updated || lv != level) {
				if (player.hasEffect(eff)) {
					player.removeEffect(eff);
				}
				if (!list.isEmpty()) {
					player.addEffect(new MobEffectInstance(eff, (int) (list.get(0) - start), level - 1));
				}
				updated = false;
			}
		}

		public void trigger(Player player) {
			updated = true;
			list.add(player.level().getGameTime());
		}

	}

}
