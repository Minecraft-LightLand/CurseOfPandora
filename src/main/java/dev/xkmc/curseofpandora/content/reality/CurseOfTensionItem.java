package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ISlotAdderItem;
import dev.xkmc.curseofpandora.content.complex.ListTickingToken;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPEffects;
import dev.xkmc.l2complements.mixin.LevelAccessor;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2library.base.effects.ClientEffectCap;
import dev.xkmc.l2library.capability.conditionals.NetworkSensitiveToken;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CurseOfTensionItem extends ISlotAdderItem<CurseOfTensionItem.Ticker> {

	public static final TokenKey<Ticker> KEY = new TokenKey<>(CurseOfPandora.MODID, "curse_of_tension");
	private static final AttrAdder R = CursePandoraUtil.reality(KEY), S = CursePandoraUtil.spell(KEY);

	public static int getPenaltyDuration() {
		return CoPConfig.COMMON.curse.curseOfTensionPenaltyDuration.get();
	}

	public static double getDamageThreshold() {
		return CoPConfig.COMMON.curse.curseOfTensionPenaltyThreshold.get();
	}

	public static int getTokenMature() {
		return CoPConfig.COMMON.curse.curseOfTensionTokenMatureTime.get();
	}

	public static int getTokenLife() {
		return CoPConfig.COMMON.curse.curseOfTensionTokenEffectiveTime.get();
	}

	public static float getDamageBonus() {
		return (float) (double) CoPConfig.COMMON.curse.curseOfTensionDamageBonus.get();
	}

	public static int getMaxLevel() {
		return CoPConfig.COMMON.curse.curseOfTensionMaxLevel.get();
	}

	public CurseOfTensionItem(Properties properties) {
		super(properties, Ticker::new, R, S);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		int tkMat = getTokenMature();
		int tkLife = getTokenLife();
		int dur = getPenaltyDuration();
		int max = getMaxLevel();
		int damage = Math.round(getDamageBonus() * 100);
		int th = (int) Math.round(getDamageThreshold() * 100);
		list.add(CoPLangData.Reality.TENSION_1.get(tkMat / 20, damage, tkLife / 20, max).withStyle(ChatFormatting.GRAY));
		list.add(CoPLangData.Reality.TENSION_2.get(th, dur).withStyle(ChatFormatting.RED));
	}

	@SerialClass
	public static class Ticker extends ListTickingToken implements IAttackListenerToken, NetworkSensitiveToken<Ticker> {

		@SerialClass.SerialField
		public HashMap<UUID, Long> terror = new HashMap<>();


		@SerialClass.SerialField
		public HashMap<UUID, ArrayList<Long>> brave = new HashMap<>();

		private boolean sync = false;

		public Ticker() {
			super(List.of(R, S));
		}

		@Override
		protected void removeImpl(Player player) {
			super.removeImpl(player);
			terror.clear();
			removeEffect(player);
		}

		@Override
		protected void tickImpl(Player player) {
			super.tickImpl(player);
			Level level = player.level();
			sync = false;
			if (player instanceof ServerPlayer sp) {
				ServerLevel sl = sp.serverLevel();
				sync = terror.entrySet().removeIf(e ->
						!(sl.getEntity(e.getKey()) instanceof LivingEntity le && le.isAlive()) ||
								level.getGameTime() > e.getValue() + getPenaltyDuration());
				sync |= brave.entrySet().removeIf(ent -> {
					if (!(sl.getEntity(ent.getKey()) instanceof LivingEntity le) || !le.isAlive())
						return true;
					sync |= ent.getValue().removeIf(t -> level.getGameTime() > t + getTokenLife() + getTokenMature());
					return ent.getValue().isEmpty();
				});
				if (sync) {
					sync(sp);
				}
			} else checkEffect(player);
		}

		@Override
		public void onPlayerDamagedFinal(Player player, AttackCache cache) {
			if (!(player instanceof ServerPlayer sp)) return;
			var attacker = cache.getAttacker();
			if (attacker == null) return;
			if (cache.getDamageDealt() >= player.getMaxHealth() * getDamageThreshold())
				terror.put(attacker.getUUID(), player.level().getGameTime());
			brave.remove(attacker.getUUID());
			sync(sp);
		}

		public boolean isTerrorized(LivingEntity target) {
			return terror.containsKey(target.getUUID());
		}

		@Override
		public void onPlayerAttackTarget(Player player, AttackCache cache) {
			if (!(player instanceof ServerPlayer)) return;
			if (isTerrorized(cache.getAttackTarget())) {
				var event = cache.getLivingAttackEvent();
				assert event != null;
				event.setCanceled(true);
			}
		}

		@Override
		public void onPlayerHurtTarget(Player player, AttackCache cache) {
			if (!(player instanceof ServerPlayer sp)) return;
			long time = player.level().getGameTime();
			var target = cache.getAttackTarget();
			List<Long> list = brave.get(target.getUUID());
			int count = 0;
			if (list != null) {
				for (var e : list) {
					if (time > e + getTokenMature()) {
						count++;
					}
				}
			}
			if (count > 0) {
				count = Math.min(count, getMaxLevel());
				cache.addHurtModifier(DamageModifier.multTotal(1 + count * getDamageBonus()));
			}
			brave.computeIfAbsent(target.getUUID(), k -> new ArrayList<>()).add(time);
			sync(sp);
		}

		private void sync(ServerPlayer sp) {
			sync(KEY, this, sp);
		}

		@Override
		public void onSync(@Nullable CurseOfTensionItem.Ticker old, Player player) {
			if (old != null)
				old.removeEffect(player);
			checkEffect(player);
		}

		private void removeEffect(Player player) {
			if (!player.level().isClientSide()) return;
			for (var id : brave.keySet()) {
				var ent = ((LevelAccessor) player.level()).callGetEntities().get(id);
				if (ent instanceof LivingEntity le) {
					var cap = ClientEffectCap.HOLDER.get(le);
					cap.map.remove(CoPEffects.FAKE_TERROR_PRE.get());
					cap.map.remove(CoPEffects.FAKE_TERROR.get());
					cap.map.remove(CoPEffects.FAKE_TERRORIZED.get());
				}
			}
		}

		private void checkEffect(Player player) {
			if (!player.level().isClientSide()) return;
			long time = player.level().getGameTime();
			for (var pair : brave.entrySet()) {
				var ent = ((LevelAccessor) player.level()).callGetEntities().get(pair.getKey());
				if (ent instanceof LivingEntity le) {
					var cap = ClientEffectCap.HOLDER.get(le);
					cap.map.remove(CoPEffects.FAKE_TERROR_PRE.get());
					cap.map.remove(CoPEffects.FAKE_TERROR.get());
					int pre = 0;
					int lost = 0;
					for (long t : pair.getValue()) {
						if (time > t + getTokenMature()) {
							lost++;
						} else {
							pre++;
						}
					}
					if (pre > 0) {
						cap.map.put(CoPEffects.FAKE_TERROR_PRE.get(), pre - 1);
					}
					if (lost > 0) {
						cap.map.put(CoPEffects.FAKE_TERROR.get(), lost - 1);
					}
				}
			}
			for (var pair : terror.entrySet()) {
				var ent = ((LevelAccessor) player.level()).callGetEntities().get(pair.getKey());
				if (ent instanceof LivingEntity le) {
					var cap = ClientEffectCap.HOLDER.get(le);
					cap.map.put(CoPEffects.FAKE_TERRORIZED.get(), 0);
				}
			}
		}

	}

}
