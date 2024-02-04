package dev.xkmc.curseofpandora.content.sets.barbaric;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PrimalForce extends ITokenProviderItem<PrimalForce.Data> {

	private static final AttrAdder MAGIC = AttrAdder.of("primal_force", L2DamageTracker.MAGIC_FACTOR::get,
			AttributeModifier.Operation.ADDITION, PrimalForce::getStat);

	private static double getStat() {
		return -CoPConfig.COMMON.barbaric.magicDamageDebuff.get();
	}

	private static int getIndexReq() {
		return CoPConfig.COMMON.barbaric.primalForceRealityIndex.get();
	}

	private static double selfArmor() {
		return CoPConfig.COMMON.barbaric.primalForceSelfArmor.get();
	}

	private static double targetArmor() {
		return CoPConfig.COMMON.barbaric.primalForceTargetArmor.get();
	}

	private static AttrAdder getAdder(LivingEntity le, double factor) {
		return AttrAdder.of("primal_force", () -> Attributes.ARMOR, AttributeModifier.Operation.ADDITION,
				() -> le.getMaxHealth() * factor);
	}

	public PrimalForce(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(MAGIC.getTooltip())
				.withStyle(pass ? ChatFormatting.RED : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Barbaric.PRIMAL_1.get(
				(int) Math.round(selfArmor() * 100)
		)).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Barbaric.PRIMAL_2.get(
				(int) Math.round(targetArmor() * 100)
		)).withStyle(pass ? ChatFormatting.RED : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		@Override
		protected void removeImpl(Player player) {
			MAGIC.removeImpl(player);
			getAdder(player, selfArmor()).removeImpl(player);
		}

		@Override
		protected void tickImpl(Player player) {
			if (player.level().isClientSide()) return;
			MAGIC.tickImpl(player);
			getAdder(player, selfArmor()).tickImpl(player);
		}

		@Override
		public void onPlayerAttacked(Player player, AttackCache cache) {
			if (cache.getAttacker() instanceof Mob mob) {
				getAdder(mob, targetArmor()).addAttr(mob);
			}
		}
	}

}
