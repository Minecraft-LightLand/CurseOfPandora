package dev.xkmc.curseofpandora.content.sets.angle;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.event.ItemEffectHandlers;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2library.capability.conditionals.ConditionalData;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AngelicPunishment extends ITokenProviderItem<AngelicPunishment.Data> {

	private static AngelicPunishment item() {
		return CoPItems.ANGELIC_PUNISHMENT.get();
	}

	public static boolean check(Player player, int reality) {
		if (ConditionalData.HOLDER.get(player).getData(item().getKey()) != null)
			return true;
		return player.level().canSeeSky(player.blockPosition()) &&
				player.getAttributeValue(CoPMisc.REALITY.get()) >= reality;
	}

	public static int getCoolDown() {
		return CoPConfig.COMMON.angelic.angelicPunishmentCoolDown.get();
	}

	public static int getIndexReq() {
		return CoPConfig.COMMON.angelic.angelicPunishmentRealityIndex.get();
	}

	public static double getDamageBase() {
		return CoPConfig.COMMON.angelic.angelicPunishmentDamageBase.get();
	}

	public AngelicPunishment(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPMisc.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Angelic.PUNISHMENT_1.get())
				.withStyle(pass ? ChatFormatting.GOLD : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Angelic.PUNISHMENT_2.get(
				Math.round(getDamageBase() * 100), Math.round(getCoolDown() / 20d)
		)).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		@SerialClass.SerialField
		private int cooldown;

		@Override
		protected void removeImpl(Player player) {

		}

		@Override
		protected void tickImpl(Player player) {
			if (cooldown > 0) cooldown--;
		}

		@Override
		public void onPlayerDamageTarget(Player player, AttackCache cache) {
			var cond = ConditionalData.HOLDER.get(player);
			var tension = cond.getData(CoPItems.CURSE_OF_TENSION.get().getKey());
			if (tension != null && tension.isTerrorized(cache.getAttackTarget()))
				return;
			if (check(player, getIndexReq())) {
				cache.addDealtModifier(DamageModifier.nonlinearMiddle(71, e -> mapVal(e, cache.getAttackTarget())));
			}
		}

		private float mapVal(float damage, LivingEntity target) {
			float min = (float) (target.getHealth() * getDamageBase());
			if (damage < min && cooldown == 0) {
				cooldown = getCoolDown();
				ItemEffectHandlers.ANGELIC_PUNISHMENT.trigger(target);
				return min;
			}
			return damage;
		}

	}

}
