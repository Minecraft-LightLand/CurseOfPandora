package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.ISlotAdderItem;
import dev.xkmc.curseofpandora.content.complex.ListTickingToken;
import dev.xkmc.curseofpandora.content.complex.SlotAdder;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.l2core.capability.conditionals.TokenKey;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.stream.Collectors;

public class CurseOfProximityItem extends ISlotAdderItem<CurseOfProximityItem.Ticker> {

	private static final SlotAdder ADDER = SlotAdder.of("curse_of_proximity", CoPConfig.SERVER.curse.curseOfProximitySlot);
	private static final TokenKey<Ticker> KEY = new TokenKey<>(CurseOfPandora.MODID, "curse_of_proximity");
	private static final AttrAdder R = CursePandoraUtil.reality(KEY), S = CursePandoraUtil.spell(KEY);

	private static int getCap() {
		return (int) (double) CoPConfig.SERVER.curse.curseOfProximityCap.get();
	}

	private static double getBase() {
		return CoPConfig.SERVER.curse.curseOfProximityBase.get();
	}

	private static double getBonus() {
		return CoPConfig.SERVER.curse.curseOfProximityBonus.get();
	}

	public CurseOfProximityItem(Properties properties) {
		super(properties, Ticker::new, ADDER, R, S);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		list.add(CoPLangData.Reality.PROXIMITY.get(getCap(), getBase(), Math.round(getBonus() * 100)).withStyle(ChatFormatting.GRAY));
	}

	@SerialClass
	public static class Ticker extends ListTickingToken {

		public Ticker() {
			super(List.of(ADDER, new Lim(), R, S));
		}

	}

	public static class Lim extends AttributeLimiter {

		protected Lim() {
			super(Attributes.ENTITY_INTERACTION_RANGE, "proximity");
		}

		@Override
		protected CursePandoraUtil.ValueConsumer curseMult(double finVal, CursePandoraUtil.Mult valMult) {
			return new ClipMultiplierData(finVal, valMult, getCap(), getBase(), getBonus());
		}

		public void tickImpl(Player player) {
			var set = player.getMainHandItem().getAttributeModifiers().modifiers().stream()
					.filter(e -> e.slot().test(EquipmentSlot.MAINHAND))
					.filter(e -> e.attribute().is(Attributes.ENTITY_INTERACTION_RANGE))
					.map(e -> e.modifier().id()).collect(Collectors.toSet());
			doAttributeLimit(player, set, false);
		}

	}

}

