package dev.xkmc.curseofpandora.content.sets.elemental;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EarthCrush extends ITokenProviderItem<EarthCrush.Data> {

	private static final AttrAdder CRIT_DMG = AttrAdder.of("earth_crush", L2DamageTracker.CRIT_DMG::get,
			AttributeModifier.Operation.MULTIPLY_BASE, EarthCrush::getStat);

	private static double getStat() {
		return CoPConfig.COMMON.elemental.earthCrushBonus.get();
	}

	private static double getThreshold() {
		return CoPConfig.COMMON.elemental.earthCrushThreshold.get();
	}

	private static int getIndexReq() {
		return CoPConfig.COMMON.elemental.earthCrushRealityIndex.get();
	}

	public EarthCrush(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.Elemental.EARTH.get(getThreshold()).withStyle(ChatFormatting.GRAY));
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(CRIT_DMG.getTooltip())
				.withStyle(pass ? ChatFormatting.BLUE : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPMisc.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken {

		@Override
		protected void removeImpl(Player player) {
			CRIT_DMG.removeImpl(player);
		}

		@Override
		protected void tickImpl(Player player) {
			if (player.level().isClientSide()) return;
			if (player.getAttributeValue(Attributes.ATTACK_SPEED) <= getThreshold()) CRIT_DMG.tickImpl(player);
			else CRIT_DMG.removeImpl(player);
		}

	}

}
