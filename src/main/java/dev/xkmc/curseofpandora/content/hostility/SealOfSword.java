package dev.xkmc.curseofpandora.content.hostility;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2hostility.content.capability.player.PlayerDifficulty;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SealOfSword extends ITokenProviderItem<SealOfSword.Data> {

	public SealOfSword(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		var reality = Component.translatable(CoPAttrs.REALITY.get().getDescriptionId()).withStyle(ChatFormatting.BLUE);
		var step = Component.literal("" + CoPConfig.COMMON.compat.sealOfSwordDifficultyPerBonus.get()).withStyle(ChatFormatting.AQUA);
		var max =  Component.literal("" + CoPConfig.COMMON.compat.sealOfSwordMaxRealityBonus.get()).withStyle(ChatFormatting.GOLD);
		list.add(CoPLangData.Compat.SEAL_OF_SWORDS.get(reality, step, max, reality).withStyle(ChatFormatting.GRAY));
	}

	@SerialClass
	public static class Data extends BaseTickingToken {

		private AttrAdder getAttr(Player player) {
			int lv = PlayerDifficulty.HOLDER.get(player).getLevel().getLevel();
			int step = CoPConfig.COMMON.compat.sealOfSwordDifficultyPerBonus.get();
			int max = CoPConfig.COMMON.compat.sealOfSwordMaxRealityBonus.get();
			int add = Math.min(lv / step, max);
			return AttrAdder.of("seal_of_sword", CoPAttrs.REALITY,
					AttributeModifier.Operation.ADDITION, () -> add);
		}

		@Override
		protected void removeImpl(Player player) {
			getAttr(player).removeImpl(player);
		}

		@Override
		protected void tickImpl(Player player) {
			getAttr(player).tickImpl(player);
		}

	}

}
