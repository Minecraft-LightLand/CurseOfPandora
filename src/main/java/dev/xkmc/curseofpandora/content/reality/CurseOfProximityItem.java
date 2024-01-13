package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.ISlotAdderItem;
import dev.xkmc.curseofpandora.content.complex.ListTickingToken;
import dev.xkmc.curseofpandora.content.complex.SlotAdder;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class CurseOfProximityItem extends ISlotAdderItem<CurseOfProximityItem.Ticker> {

	private static final SlotAdder ADDER = SlotAdder.of("curse_of_proximity", "bracelet", 1);
	private static final TokenKey<Ticker> KEY = new TokenKey<>(CurseOfPandora.MODID, "curse_of_proximity");

	private static int getCap() {
		return 6;
	}

	private static double getBase() {
		return 2;
	}

	private static double getBonus() {
		return 0.5;
	}

	public CurseOfProximityItem(Properties properties) {
		super(properties, KEY, Ticker::new, ADDER, CursePandoraUtil.reality(KEY), CursePandoraUtil.spell(KEY));
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(CoPLangData.IDS.CURSE_PROXIMITY.get(getCap(), getBase(), Math.round(getBonus() * 100)).withStyle(ChatFormatting.GRAY));
	}

	@SerialClass
	public static class Ticker extends ListTickingToken {

		public Ticker() {
			super(List.of(ADDER, new Lim(), CursePandoraUtil.reality(KEY), CursePandoraUtil.spell(KEY)));
		}

	}

	public static class Lim extends AttributeLimiter {

		protected Lim() {
			super(ForgeMod.ENTITY_REACH.get(), "proximity");
		}

		@Override
		protected CursePandoraUtil.ValueConsumer curseMult(double finVal, CursePandoraUtil.Mult valMult) {
			return new ClipMultiplierData(finVal, valMult, getCap(), getBase(), getBonus());
		}

		public void tickImpl(Player player) {
			var map = player.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND);
			var list = map.get(ForgeMod.ENTITY_REACH.get());
			var set = list.stream().map(AttributeModifier::getId).collect(Collectors.toSet());
			doAttributeLimit(player, set, false);
		}

	}

}

