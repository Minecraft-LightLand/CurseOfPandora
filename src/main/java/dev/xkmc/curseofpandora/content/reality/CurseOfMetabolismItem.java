package dev.xkmc.curseofpandora.content.reality;

import dev.xkmc.curseofpandora.content.complex.ISlotAdderItem;
import dev.xkmc.curseofpandora.content.complex.ISubToken;
import dev.xkmc.curseofpandora.content.complex.ListTickingToken;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2library.capability.conditionals.TokenKey;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

public class CurseOfMetabolismItem extends ISlotAdderItem<CurseOfMetabolismItem.Ticker> {

	private static final TokenKey<Ticker> KEY = new TokenKey<>(CurseOfPandora.MODID, "curse_of_metabolism");

	private static int getThreshold() {
		return 16;
	}

	private static double getFactor() {
		return 0.05;
	}

	private static double getBonus() {
		return 0.3;
	}

	private static int getIndexReq() {
		return 5;
	}

	public CurseOfMetabolismItem(Properties properties) {
		super(properties, KEY, Ticker::new, CursePandoraUtil.reality(KEY), CursePandoraUtil.spell(KEY));
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		int f = (int) Math.round(getFactor() * 100);
		int b = (int) Math.round(getBonus() * 100);
		int t = getThreshold();
		boolean pass = (level == null ? 0 : ClientSpellText.getReality(level)) >= getIndexReq();
		list.add(CoPLangData.IDS.CURSE_METABOLISM_2.get(f, t).withStyle(ChatFormatting.RED));
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq()).withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.IDS.CURSE_METABOLISM_1.get(f, t).withStyle(pass ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_GRAY));
		list.add(CoPLangData.IDS.CURSE_METABOLISM_3.get(b).withStyle(pass ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_GRAY));
	}

	@SerialClass
	public static class Ticker extends ListTickingToken {

		private static final AttrBonus ATK = AttrBonus.of(() -> Attributes.ATTACK_DAMAGE,
				"curse_of_metabolism_attack", true);
		private static final AttrBonus SPEED = AttrBonus.of(() -> Attributes.MOVEMENT_SPEED,
				"curse_of_metabolism_speed", true);

		public Ticker() {
			super(List.of(ATK, SPEED, new Lim(), CursePandoraUtil.reality(KEY), CursePandoraUtil.spell(KEY)));
		}

	}

	private static class Lim extends AttributeLimiter {

		protected Lim() {
			super("metabolism");
		}

		@Override
		public void tickImpl(Player player) {
			var ins = player.getAttribute(Attributes.MOVEMENT_SPEED);
			if (ins == null) return;
			if (player.getFoodData().needsFood()) {
				removeImpl(player);
			} else {
				doAttributeLimit(player, ins, Set.of(Ticker.SPEED.id()), true);
			}
		}

		@Override
		protected CursePandoraUtil.ValueConsumer curseMult(double finVal, CursePandoraUtil.Mult valMult) {
			return new CursePandoraUtil.BonusMult(1);
		}

	}

	public record AttrBonus(Supplier<Attribute> attr, String name, UUID id, boolean bonus) implements ISubToken {

		public static AttrBonus of(Supplier<Attribute> attr, String name, boolean bonus) {
			return new AttrBonus(attr, name, MathHelper.getUUIDFromString(name), bonus);
		}

		@Override
		public void removeImpl(Player player) {
			if (player.level().isClientSide) return;
			var ins = player.getAttribute(attr.get());
			if (ins == null) return;
			ins.removeModifier(id);
		}

		@Override
		public void tickImpl(Player player) {
			if (player.level().isClientSide) return;
			var ins = player.getAttribute(attr.get());
			if (ins == null) return;
			double val = (player.getFoodData().getFoodLevel() - getThreshold()) * getFactor();
			boolean doBonus = bonus && player.getAttributeValue(CoPMisc.REALITY.get()) >= getIndexReq();
			if (!doBonus && val > 0) return;
			if (!player.getFoodData().needsFood()) val += getBonus();
			var old = ins.getModifier(id);
			if (old != null && old.getAmount() == val &&
					old.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE)
				return;
			ins.removeModifier(id);
			ins.addTransientModifier(new AttributeModifier(id, name, val,
					AttributeModifier.Operation.MULTIPLY_BASE));
		}
	}

}

