package dev.xkmc.curseofpandora.content.sets.abyss;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
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

public class AbyssalTreasure extends ITokenProviderItem<AbyssalTreasure.Data> {

	private static int getIndexReq() {
		return CoPConfig.COMMON.abyssal.abyssalTreasureRealityIndex.get();
	}

	private static AttrAdder getAttr(Player player) {
		return AttrAdder.of("abyssal_treasure", () -> Attributes.LUCK,
				AttributeModifier.Operation.ADDITION, () -> AbyssalWill.getStep(player));
	}

	public AbyssalTreasure(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Abyssal.TREASURE.get(ClientSpellText.getDepth(level))
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken {

		@Override
		protected void removeImpl(Player player) {
			getAttr(player).removeImpl(player);
		}

		@Override
		protected void tickImpl(Player player) {
			if (AbyssalWill.getStep(player) <= 0) {
				getAttr(player).removeImpl(player);
			} else {
				getAttr(player).tickImpl(player);
			}
		}

	}

}
