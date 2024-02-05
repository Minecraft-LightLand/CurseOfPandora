package dev.xkmc.curseofpandora.content.sets.evil;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EvilSpiritCurse extends ITokenProviderItem<EvilSpiritCurse.Data> {

	private static int getIndexReq() {
		return CoPConfig.COMMON.evil.evilSpiritCurseRealityIndex.get();
	}

	private static double getThreshold() {
		return CoPConfig.COMMON.evil.evilSpiritCurseThreshold.get();
	}

	private static double getBonus() {
		return CoPConfig.COMMON.evil.evilSpiritCurseBonus.get();
	}

	public EvilSpiritCurse(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Evil.CURSE.get(
				(int) Math.round(getThreshold() * 100),
				(int) Math.round(getBonus() * 100)
		).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		@Override
		protected void removeImpl(Player player) {

		}

		@Override
		protected void tickImpl(Player player) {

		}

		@Override
		public void onPlayerHurtTarget(Player player, AttackCache cache) {
			var event = cache.getLivingHurtEvent();
			assert event != null;
			if (event.getSource().is(L2DamageTypes.MAGIC)) {
				if (cache.getAttackTarget().getHealth() < cache.getAttackTarget().getMaxHealth() * getThreshold()) {
					cache.addHurtModifier(DamageModifier.multTotal((float) (1 + getBonus())));
				}
			}
		}
	}

}
