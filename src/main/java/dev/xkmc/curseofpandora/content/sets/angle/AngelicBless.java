package dev.xkmc.curseofpandora.content.sets.angle;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
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

public class AngelicBless extends ITokenProviderItem<AngelicBless.Data> {

	private static final AttrAdder ABSORPTION = AttrAdder.of("angelic_bless", L2DamageTracker.ABSORB::get,
			AttributeModifier.Operation.ADDITION, AngelicBless::getStat);
	private static final AttrAdder REDUCTION = AttrAdder.of("angelic_bless", L2DamageTracker.REDUCTION::get,
			AttributeModifier.Operation.MULTIPLY_TOTAL, AngelicBless::getFactor);

	private static double getStat() {
		return CoPConfig.COMMON.angelic.angelicBlessAbsorption.get();
	}

	private static int getIndexReq() {
		return CoPConfig.COMMON.angelic.angelicBlessRealityIndex.get();
	}

	private static double getFactor() {
		return -CoPConfig.COMMON.angelic.angelicBlessDamageReduction.get();
	}

	public AngelicBless(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(CoPLangData.Angelic.CHECK.get().withStyle(ChatFormatting.GRAY));
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(ABSORPTION.getTooltip())
				.withStyle(pass ? ChatFormatting.BLUE : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(REDUCTION.getTooltip())
				.withStyle(pass ? ChatFormatting.BLUE : ChatFormatting.DARK_GRAY));
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
			ABSORPTION.removeImpl(player);
			REDUCTION.removeImpl(player);
		}

		private static boolean check(Player player) {
			return AngelicPunishment.check(player, getIndexReq());
		}

		@Override
		protected void tickImpl(Player player) {
			if (player.level().isClientSide()) return;
			if (check(player)) {
				ABSORPTION.tickImpl(player);
				REDUCTION.tickImpl(player);
			} else {
				ABSORPTION.removeImpl(player);
				REDUCTION.removeImpl(player);
			}
		}

	}

}
