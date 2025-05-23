package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.*;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.data.CoPTagGen;
import dev.xkmc.curseofpandora.init.registrate.CoPEffects;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2complements.mixin.LevelAccessor;
import dev.xkmc.l2core.capability.conditionals.NetworkSensitiveToken;
import dev.xkmc.l2core.capability.conditionals.TokenKey;
import dev.xkmc.l2core.init.L2LibReg;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CurseOfPrudenceItem extends ISlotAdderItem<CurseOfPrudenceItem.Ticker> {

	public static final TokenKey<Ticker> KEY = new TokenKey<>(CurseOfPandora.MODID, "curse_of_prudence");
	private static final AttrAdder R = CursePandoraUtil.reality(KEY), S = CursePandoraUtil.spell(KEY);
	public static final SlotAdder ADDER = SlotAdder.of("curse_of_prudence", CoPConfig.COMMON.curse.curseOfPrudenceSlot);

	public static int getMaxLevel() {
		return CoPConfig.COMMON.curse.curseOfPrudenceMaxLevel.get();
	}

	public static double getDamageFactor() {
		return CoPConfig.COMMON.curse.curseOfPrudenceDamageFactor.get();
	}

	public static int getDuration() {
		return CoPConfig.COMMON.curse.curseOfPrudenceDuration.get();
	}

	public static double getMaxHurtDamage() {
		return CoPConfig.COMMON.curse.curseOfPrudenceMaxDamage.get();
	}

	public CurseOfPrudenceItem(Properties properties) {
		super(properties, Ticker::new, ADDER, R, S);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		int dur = getDuration();
		int damage = (int) Math.round(getDamageFactor() * 100);
		int hurt = (int) Math.round(getMaxHurtDamage() * 100);
		list.add(CoPLangData.Reality.PRUDENCE_1.get(dur / 20, damage).withStyle(ChatFormatting.RED));
		list.add(CoPLangData.Reality.PRUDENCE_2.get(hurt).withStyle(ChatFormatting.RED));
	}

	@SerialClass
	public static class Ticker extends ListTickingToken
			implements IAttackListenerToken, NetworkSensitiveToken<Ticker> {

		private static ResourceLocation id(String suffix) {
			return CoPItems.CURSE_OF_PRUDENCE.getId().withSuffix(suffix);
		}

		@SerialField
		public HashMap<UUID, HashSet<Long>> fear = new HashMap<>();

		private boolean sync = false;

		public Ticker() {
			super(List.of(ADDER, R, S));
		}

		@Override
		protected void removeImpl(Player player) {
			super.removeImpl(player);
			removeEffect(player);
			fear.clear();
		}

		@Override
		protected void tickImpl(Player player) {
			super.tickImpl(player);
			Level level = player.level();
			sync = false;
			if (player instanceof ServerPlayer sp) {
				ServerLevel sl = sp.serverLevel();
				sync |= fear.entrySet().removeIf(ent -> {
					if (!(sl.getEntity(ent.getKey()) instanceof LivingEntity le) || !le.isAlive())
						return true;
					sync |= ent.getValue().removeIf(t -> level.getGameTime() >= t + getDuration());
					return ent.getValue().isEmpty();
				});
				if (sync) {
					sync(sp);
				}
			} else checkEffect(player);
		}

		@Override
		public void onPlayerDamageTarget(Player player, DamageData.Defence data) {
			if (!(player instanceof ServerPlayer sp)) return;
			long time = player.level().getGameTime();
			var target = data.getTarget();
			if (target == player) return;
			Set<Long> list = fear.get(target.getUUID());
			int count = list == null ? 0 : list.size();
			if (count > 0) {
				if (!data.getSource().is(DamageTypeTags.BYPASSES_COOLDOWN)) {
					count = Math.min(count, getMaxLevel());
					data.addDealtModifier(DamageModifier.multTotal((float) Math.pow(getDamageFactor(), count), id("_cooldown")));
				}
			}
			fear.computeIfAbsent(target.getUUID(), k -> new HashSet<>()).add(time);
			sync(sp);
			if (data.getTarget().getType().is(CoPTagGen.PRUDENCE_WHITELIST)) return;
			if (data.getTarget().getHealth() <= player.getHealth()) return;
			double maxDamage = data.getTarget().getMaxHealth() * getMaxHurtDamage();
			data.addDealtModifier(DamageModifier.nonlinearFinal(9000, e -> Math.min(e, (float) maxDamage), id("_limit")));
		}

		private void sync(ServerPlayer sp) {
			sync(KEY, this, sp);
		}

		@Override
		public void onSync(@Nullable Ticker old, Player player) {
			if (old != null)
				old.removeEffect(player);
			checkEffect(player);
		}

		private void removeEffect(Player player) {
			if (!player.level().isClientSide()) return;
			for (var id : fear.keySet()) {
				var ent = ((LevelAccessor) player.level()).callGetEntities().get(id);
				if (ent instanceof LivingEntity le) {
					var cap = L2LibReg.EFFECT.type().getOrCreate(le);
					cap.map.remove(CoPEffects.PRUDENCE);
				}
			}
		}

		private void checkEffect(Player player) {
			if (!player.level().isClientSide()) return;
			for (var pair : fear.entrySet()) {
				var ent = ((LevelAccessor) player.level()).callGetEntities().get(pair.getKey());
				if (ent instanceof LivingEntity le) {
					var cap = L2LibReg.EFFECT.type().getOrCreate(le);
					cap.map.put(CoPEffects.PRUDENCE, 0);
				}
			}
		}

	}

}
