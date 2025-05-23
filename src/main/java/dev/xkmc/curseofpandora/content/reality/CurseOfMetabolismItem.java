package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.ISlotAdderItem;
import dev.xkmc.curseofpandora.content.complex.ISubToken;
import dev.xkmc.curseofpandora.content.complex.ListTickingToken;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2core.capability.conditionals.TokenKey;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Set;

public class CurseOfMetabolismItem extends ISlotAdderItem<CurseOfMetabolismItem.Ticker> {

	private static final TokenKey<Ticker> KEY = new TokenKey<>(CurseOfPandora.MODID, "curse_of_metabolism");
	private static final AttrAdder R = CursePandoraUtil.reality(KEY), S = CursePandoraUtil.spell(KEY);

	private static int getThreshold() {
		return CoPConfig.COMMON.curse.curseOfMetabolismThreshold.get();
	}

	private static double getFactor() {
		return CoPConfig.COMMON.curse.curseOfMetabolismFactor.get();
	}

	private static double getBonus() {
		return CoPConfig.COMMON.curse.curseOfMetabolismBonus.get();
	}

	private static int getIndexReq() {
		return CoPConfig.COMMON.curse.curseOfMetabolismIndexReq.get();
	}

	public CurseOfMetabolismItem(Properties properties) {
		super(properties, Ticker::new, R, S);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		int f = (int) Math.round(getFactor() * 100);
		int b = (int) Math.round(getBonus() * 100);
		int t = getThreshold();
		boolean pass = ClientSpellText.getReality(ctx.level()) >= getIndexReq();
		list.add(CoPLangData.Reality.METABOLISM_2.get(f, t).withStyle(ChatFormatting.RED));
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq()).withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Reality.METABOLISM_1.get(f, t).withStyle(pass ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_GRAY));
		list.add(CoPLangData.Reality.METABOLISM_3.get(b).withStyle(pass ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_GRAY));
	}

	@SerialClass
	public static class Ticker extends ListTickingToken {

		private static final AttrBonus ATK = AttrBonus.of(Attributes.ATTACK_DAMAGE,
				"curse_of_metabolism_attack", true);
		private static final AttrBonus SPEED = AttrBonus.of(Attributes.MOVEMENT_SPEED,
				"curse_of_metabolism_speed", true);

		public Ticker() {
			super(List.of(ATK, SPEED, new Lim(), R, S));
		}

	}

	private static class Lim extends AttributeLimiter {

		protected Lim() {
			super(Attributes.MOVEMENT_SPEED, "metabolism");
		}

		@Override
		public void tickImpl(Player player) {
			if (player.getFoodData().needsFood()) {
				removeImpl(player);
			} else {
				doAttributeLimit(player, Set.of(Ticker.SPEED.name()), true);
			}
		}

		@Override
		protected CursePandoraUtil.ValueConsumer curseMult(double finVal, CursePandoraUtil.Mult valMult) {
			return new CursePandoraUtil.BonusMult(1);
		}

	}

	public record AttrBonus(Holder<Attribute> attr, ResourceLocation name, boolean bonus) implements ISubToken {

		public static AttrBonus of(Holder<Attribute> attr, String name, boolean bonus) {
			return new AttrBonus(attr, CurseOfPandora.loc(name), bonus);
		}

		@Override
		public void removeImpl(Player player) {
			if (player.level().isClientSide) return;
			var ins = player.getAttribute(attr);
			if (ins == null) return;
			ins.removeModifier(name);
		}

		@Override
		public void tickImpl(Player player) {
			if (player.level().isClientSide) return;
			var ins = player.getAttribute(attr);
			if (ins == null) return;
			double val = (player.getFoodData().getFoodLevel() - getThreshold()) * getFactor();
			boolean doBonus = bonus && player.getAttributeValue(CoPAttrs.REALITY) >= getIndexReq();
			if (!doBonus && val > 0) return;
			if (!player.getFoodData().needsFood()) val += getBonus();
			var old = ins.getModifier(name);
			if (old != null && old.amount() == val &&
					old.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
				return;
			ins.removeModifier(name);
			ins.addTransientModifier(new AttributeModifier(name, val,
					AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
		}
	}

}

