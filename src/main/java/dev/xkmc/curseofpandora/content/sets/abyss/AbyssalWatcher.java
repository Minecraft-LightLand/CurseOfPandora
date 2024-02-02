package dev.xkmc.curseofpandora.content.sets.abyss;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbyssalWatcher extends ITokenProviderItem<AbyssalWatcher.Data> {

	private static int getIndexReq() {
		return CoPConfig.COMMON.abyssal.abyssalWatcherRealityIndex.get();
	}

	private static double getRegen() {
		return CoPConfig.COMMON.abyssal.abyssalWatcherRegen.get();
	}

	public AbyssalWatcher(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Abyssal.WATCHER.get(ClientSpellText.getDepth(level), Math.round(getRegen() * 100))
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
		}

		@Override
		protected void tickImpl(Player player) {
			if (player.tickCount % 20 == 0) {
				int step = AbyssalWill.getStep(player);
				if (step > 0) {
					player.heal((float) (step * getRegen() * player.getMaxHealth()));
				}
			}
		}

	}

}
