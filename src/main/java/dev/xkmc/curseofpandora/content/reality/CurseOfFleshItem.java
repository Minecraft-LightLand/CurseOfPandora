package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.ISlotAdderItem;
import dev.xkmc.curseofpandora.content.complex.ListTickingToken;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class CurseOfFleshItem extends ISlotAdderItem<CurseOfFleshItem.Ticker> {

	private static final TokenKey<Ticker> KEY = new TokenKey<>(CurseOfPandora.MODID, "curse_of_flesh");
	private static final AttrAdder R = CursePandoraUtil.reality(KEY), S = CursePandoraUtil.spell(KEY);

	private static int getThreshold() {
		return CoPConfig.COMMON.curseOfFleshThreshold.get();
	}

	private static int getDuration() {
		return CoPConfig.COMMON.curseOfFleshDuration.get();
	}

	private static double getBonus() {
		return CoPConfig.COMMON.curseOfFleshBonus.get();
	}

	public CurseOfFleshItem(Properties properties) {
		super(properties, Ticker::new, R, S);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(CoPLangData.IDS.CURSE_FLESH.get(getThreshold(), getDuration(), Math.round(getBonus() * 100)).withStyle(ChatFormatting.GRAY));
	}

	@SerialClass
	public static class Ticker extends ListTickingToken {

		private final Lim lim = new Lim(this);

		@SerialClass.SerialField
		public int maintain = 0;

		public Ticker() {
			super(List.of(R, S));
		}

		@Override
		protected void removeImpl(Player player) {
			super.removeImpl(player);
			lim.removeImpl(player);
		}

		@Override
		public void tickImpl(Player player) {
			if (player.getFoodData().getFoodLevel() >= getThreshold()) {
				if (maintain < getDuration())
					maintain++;
			} else maintain = 0;
			super.tickImpl(player);
			lim.tickImpl(player);
		}

	}

	public static class Lim extends AttributeLimiter {

		private final Ticker ticker;

		protected Lim(Ticker ticker) {
			super(Attributes.MAX_HEALTH, "flesh");
			this.ticker = ticker;
		}

		@Override
		protected CursePandoraUtil.ValueConsumer curseMult(double finVal, CursePandoraUtil.Mult valMult) {
			return new CursePandoraUtil.BonusMult(ticker.maintain >= getDuration() * 60 * 20 ? 1 + getBonus() : 1);
		}

		public void tickImpl(Player player) {
			doAttributeLimit(player, Set.of(), false);
		}

	}

}

