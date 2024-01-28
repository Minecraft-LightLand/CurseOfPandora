package dev.xkmc.curseofpandora.content.sets.shadow;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.event.ItemEffectHandlers;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPDamageTypeGen;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ShadowConsolidation extends ITokenProviderItem<ShadowConsolidation.Data> {

	public static int getIndexReq() {
		return CoPConfig.COMMON.shadow.shadowConsolidationRealityIndex.get();
	}

	public static double getRange() {
		return CoPConfig.COMMON.shadow.shadowConsolidationRange.get();
	}

	public static double getFactor() {
		return CoPConfig.COMMON.shadow.shadowConsolidationFactor.get();
	}

	public static int getDelay() {
		return CoPConfig.COMMON.shadow.shadowConsolidationDelay.get();
	}

	public static int getCoolDown() {
		return CoPConfig.COMMON.shadow.shadowConsolidationCoolDown.get();
	}

	public ShadowConsolidation(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq()).withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Shadow.CONSOLIDATION.get(
				Math.round(getRange()), Math.round(getFactor() * 100),
				Math.round(getDelay() / 20d), Math.round(getCoolDown() / 20d)
		).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		private Frame current;
		@SerialClass.SerialField
		private int cooldown = 0;

		@Override
		protected void removeImpl(Player player) {

		}

		@Override
		protected void tickImpl(Player player) {
			if (current != null) {
				current.tick--;
				if (current.tick <= 0) {
					current.execute(player);
					current = null;
				}
			}
			if (cooldown > 0) {
				cooldown--;
			}
		}

		@Override
		public void onPlayerDamageTargetFinal(Player player, AttackCache cache) {
			var event = cache.getLivingDamageEvent();
			assert event != null;
			if (event.getSource().is(CoPDamageTypeGen.SHADOW)) return;

			if (current == null) {
				if (cooldown > 0) {
					return;
				}
				current = new Frame();
				cooldown = getCoolDown();
			}
			current.add(cache.getAttackTarget(), cache.getDamageDealt());
		}
	}

	private static class Frame {

		private final List<Entry> list = new ArrayList<>();

		private int tick = getDelay();

		public void add(LivingEntity target, float damage) {
			ItemEffectHandlers.SHADOW_CONSOLIDATION.trigger(target);
			list.add(new Entry(target.position(), damage));
		}

		public void execute(Player player) {
			Level level = player.level();
			double r = getRange();
			AABB aabb = null;
			for (var e : list) {
				AABB self = AABB.ofSize(e.pos(), r * 2, r * 2, r * 2);
				if (aabb == null) aabb = self;
				else aabb = aabb.minmax(self);
			}
			if (aabb == null) return;
			double r2 = r * r;
			var type = VoidOverflow.check(player) ? CoPDamageTypeGen.VOID_CURSE : CoPDamageTypeGen.SHADOW_CURSE;
			var source = CoPDamageTypeGen.forKey(level, type);
			for (var e : level.getEntities(EntityTypeTest.forClass(Mob.class), aabb, e -> e instanceof Enemy)) {
				float damage = 0;
				for (var pos : list) {
					if (e.distanceToSqr(pos.pos) <= r2) {
						damage += pos.damage;
					}
				}
				e.hurt(new DamageSource(source, player), (float) (damage * getFactor()));
			}
		}
	}

	private record Entry(Vec3 pos, float damage) {

	}

}
