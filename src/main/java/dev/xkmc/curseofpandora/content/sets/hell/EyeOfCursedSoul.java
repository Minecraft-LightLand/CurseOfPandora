package dev.xkmc.curseofpandora.content.sets.hell;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.event.ItemEffectHandlers;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPDamageTypeGen;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EyeOfCursedSoul extends ITokenProviderItem<EyeOfCursedSoul.Data> {

	public static int getIndexReq() {
		return CoPConfig.COMMON.hell.eyeOfCursedSoulRealityIndex.get();
	}

	public static int getCoolDown() {
		return CoPConfig.COMMON.hell.eyeOfCursedSoulCoolDown.get();
	}

	public static double getRange() {
		return CoPConfig.COMMON.hell.eyeOfCursedSoulRange.get();
	}

	public EyeOfCursedSoul(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Hell.EYE.get(
				Math.round(getRange()),
				Math.round(getCoolDown() / 20d)
		).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPMisc.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		@SerialClass.SerialField
		public int coolDown;

		@Override
		protected void removeImpl(Player player) {

		}

		@Override
		protected void tickImpl(Player player) {
			if (coolDown > 0) {
				coolDown--;
			}
		}

		@Override
		public void onPlayerHurtTarget(Player player, AttackCache cache) {
			var target = cache.getAttackTarget();
			if (coolDown == 0) {
				coolDown = getCoolDown();
				for (var e : target.level().getEntities(EntityTypeTest.forClass(Mob.class), target.getBoundingBox().inflate(getRange()), e -> e != target)) {
					if (!(e instanceof Enemy)) continue;
					if (e.distanceTo(target) > getRange()) continue;
					e.setTarget(null);
					for (var x : e.targetSelector.getAvailableGoals()) {
						if (x.getGoal() instanceof NearestAttackableTargetGoal<?> goal) {
							goal.setTarget(null);
						}
					}
					e.hurt(new DamageSource(CoPDamageTypeGen.forKey(target.level(), CoPDamageTypeGen.SOUL_CURSE), e), 1);
				}
				ItemEffectHandlers.EYE_OF_CURSED_SOUL.trigger(target);
			}
		}

	}

}
