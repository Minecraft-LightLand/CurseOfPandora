package dev.xkmc.curseofpandora.content.sets.barbaric;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.content.complex.ListTickingToken;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
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

public class BarbaricWrath extends ITokenProviderItem<BarbaricWrath.Data> {

	private static final AttrAdder MAGIC = AttrAdder.of("barbaric_wrath", L2DamageTracker.MAGIC_FACTOR::get,
			AttributeModifier.Operation.ADDITION, BarbaricWrath::getStat);

	private static final AttrAdder CRIT = AttrAdder.of("barbaric_wrath", L2DamageTracker.CRIT_DMG::get,
			AttributeModifier.Operation.ADDITION, BarbaricWrath::getCrit);

	private static final AttrAdder PROT = AttrAdder.of("barbaric_wrath", ()-> Attributes.ARMOR_TOUGHNESS,
			AttributeModifier.Operation.ADDITION, BarbaricWrath::getProt);

	private static double getStat() {
		return -CoPConfig.COMMON.barbaric.magicDamageDebuff.get();
	}

	private static double getCrit() {
		return CoPConfig.COMMON.barbaric.barbaricWrathCritBonus.get();
	}

	private static double getProt() {
		return CoPConfig.COMMON.barbaric.barbaricWrathToughBonus.get();
	}

	private static int getIndexReq() {
		return CoPConfig.COMMON.barbaric.barbaricWrathRealityIndex.get();
	}

	public BarbaricWrath(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(MAGIC.getTooltip())
				.withStyle(pass ? ChatFormatting.RED : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(CRIT.getTooltip())
				.withStyle(pass ? ChatFormatting.BLUE : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(PROT.getTooltip())
				.withStyle(pass ? ChatFormatting.BLUE : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends ListTickingToken {

		public Data() {
			super(List.of(MAGIC, CRIT, PROT));
		}
	}

}
