package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.*;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPFakeEffects;
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

import java.util.*;

public class CurseOfPrudenceItem extends ISlotAdderItem<CurseOfPrudenceItem.Ticker> {

	public static final TokenKey<Ticker> KEY = new TokenKey<>(CurseOfPandora.MODID, "curse_of_prudence");
	private static final AttrAdder R = CursePandoraUtil.reality(KEY), S = CursePandoraUtil.spell(KEY);
	public static final SlotAdder ADDER = SlotAdder.of("curse_of_prudence", "charm", 3);

	public static int getMaxLevel() {
		return CoPConfig.COMMON.curseOfPrudenceMaxLevel.get();
	}

	public static double getDamageFactor() {
		return CoPConfig.COMMON.curseOfPrudenceDamageFactor.get();
	}

	public static int getDuration() {
		return CoPConfig.COMMON.curseOfPrudenceDuration.get();
	}

	public static double getMaxHurtDamage() {
		return CoPConfig.COMMON.curseOfPrudenceMaxDamage.get();
	}

	public CurseOfPrudenceItem(Properties properties) {
		super(properties, Ticker::new, ADDER, R, S);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		int dur = getDuration();
		int damage = (int) Math.round(getDamageFactor() * 100);
		int hurt = (int) Math.round(getMaxHurtDamage() * 100);
		list.add(CoPLangData.IDS.CURSE_PRUDENCE_1.get(dur / 20, damage).withStyle(ChatFormatting.RED));
		list.add(CoPLangData.IDS.CURSE_PRUDENCE_2.get(hurt).withStyle(ChatFormatting.RED));
	}

	@SerialClass
	public static class Ticker extends ListTickingToken
			implements IAttackListenerToken, NetworkSensitiveToken<Ticker> {

		@SerialClass.SerialField
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
		public void onPlayerDamageTarget(Player player, AttackCache cache) {
			if (!(player instanceof ServerPlayer sp)) return;
			long time = player.level().getGameTime();
			var target = cache.getAttackTarget();
			Set<Long> list = fear.get(target.getUUID());
			int count = list == null ? 0 : list.size();
			if (count > 0) {
				count = Math.min(count, getMaxLevel());
				cache.addDealtModifier(DamageModifier.multTotal((float) Math.pow(getDamageFactor(), count)));
			}
			fear.computeIfAbsent(target.getUUID(), k -> new HashSet<>()).add(time);
			sync(sp);
			double maxDamage = cache.getAttackTarget().getMaxHealth() * getMaxHurtDamage();
			cache.addDealtModifier(DamageModifier.nonlinearFinal(9000, e -> Math.min(e, (float) maxDamage)));
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
					var cap = ClientEffectCap.HOLDER.get(le);
					cap.map.remove(CoPFakeEffects.PRUDENCE.get());
				}
			}
		}

		private void checkEffect(Player player) {
			if (!player.level().isClientSide()) return;
			for (var pair : fear.entrySet()) {
				var ent = ((LevelAccessor) player.level()).callGetEntities().get(pair.getKey());
				if (ent instanceof LivingEntity le) {
					var cap = ClientEffectCap.HOLDER.get(le);
					cap.map.put(CoPFakeEffects.PRUDENCE.get(), 0);
				}
			}
		}

	}

}
