package dev.xkmc.curseofpandora.content.sets.elemental;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2core.init.L2LibReg;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class WindThrust extends ITokenProviderItem<WindThrust.Data> {

	private static final AttrAdder SPEED = AttrAdder.of("wind_thrust", Attributes.MOVEMENT_SPEED,
			AttributeModifier.Operation.ADD_MULTIPLIED_BASE, WindThrust::getSpeed);
	private static final AttrAdder DAMAGE = AttrAdder.of("wind_thrust", Attributes.ATTACK_DAMAGE,
			AttributeModifier.Operation.ADD_MULTIPLIED_BASE, WindThrust::getDamage);

	public static boolean check(Player player) {
		return L2LibReg.CONDITIONAL.type().getOrCreate(player).hasData(CoPItems.WIND_THRUST.get().getKey());
	}

	private static double getSpeed() {
		return CoPConfig.COMMON.elemental.windThrustSpeed.get();
	}

	private static double getDamage() {
		return CoPConfig.COMMON.elemental.windThrustDamage.get();
	}

	private static int getIndexReq() {
		return CoPConfig.COMMON.elemental.windThrustRealityIndex.get();
	}

	public WindThrust(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(ctx.level()) >= getIndexReq();
		list.add(CoPLangData.Elemental.WIND_1.get().withStyle(ChatFormatting.GRAY));
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Elemental.WIND_2.get())
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(SPEED.getTooltip())
				.withStyle(pass ? ChatFormatting.BLUE : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(DAMAGE.getTooltip())
				.withStyle(pass ? ChatFormatting.BLUE : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken {

		@Override
		protected void removeImpl(Player player) {
			SPEED.removeImpl(player);
			DAMAGE.removeImpl(player);
		}

		@Override
		protected void tickImpl(Player player) {
			if (player.level().isClientSide()) {
				if (player.isSprinting()) {
					ClientSpellText.onClientAutoAttack(player);
				}
				return;
			}
			if (player.isSprinting()) {
				SPEED.tickImpl(player);
				DAMAGE.tickImpl(player);
			} else {
				SPEED.removeImpl(player);
				DAMAGE.removeImpl(player);
			}
		}

	}

}
