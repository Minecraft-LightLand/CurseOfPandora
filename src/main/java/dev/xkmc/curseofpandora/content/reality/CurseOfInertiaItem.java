package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.ISlotAdderItem;
import dev.xkmc.curseofpandora.content.complex.ListTickingToken;
import dev.xkmc.curseofpandora.content.complex.SlotAdder;
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
import java.util.UUID;

public class CurseOfInertiaItem extends ISlotAdderItem<CurseOfInertiaItem.Ticker> {

	private static final SlotAdder ADDER = SlotAdder.of("curse_of_inertia", "necklace", 1);
	private static final TokenKey<CurseOfInertiaItem.Ticker> KEY = new TokenKey<>(CurseOfPandora.MODID, "curse_of_inertia");
	private static final AttrAdder R = CursePandoraUtil.reality(KEY), S = CursePandoraUtil.spell(KEY);

	private static double getCap() {
		return CoPConfig.COMMON.curse.curseOfInertiaCap.get();
	}

	private static double getBase() {
		return CoPConfig.COMMON.curse.curseOfInertiaBase.get();
	}

	private static double getBonus() {
		return CoPConfig.COMMON.curse.curseOfFleshBonus.get();
	}

	public CurseOfInertiaItem(Properties properties) {
		super(properties, Ticker::new, ADDER, R, S);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(CoPLangData.Reality.INERTIA.get(getCap(), getBase(), Math.round(getBonus() * 100)).withStyle(ChatFormatting.GRAY));
	}

	@SerialClass
	public static class Ticker extends ListTickingToken {

		public Ticker() {
			super(List.of(ADDER, new Lim(), R, S));
		}

	}

	public static class Lim extends AttributeLimiter {

		private static final UUID WEAPON_SPEED = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

		protected Lim() {
			super(Attributes.ATTACK_SPEED, "inertia");
		}

		public void tickImpl(Player player) {
			doAttributeLimit(player, Set.of(WEAPON_SPEED), false);
		}

		@Override
		protected CursePandoraUtil.ValueConsumer curseMult(double finVal, CursePandoraUtil.Mult valMult) {
			return new ClipMultiplierData(finVal, valMult, getCap(), getBase(), getBonus());
		}

	}

}

