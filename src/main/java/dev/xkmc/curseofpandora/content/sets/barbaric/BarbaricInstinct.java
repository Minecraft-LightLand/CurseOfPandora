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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BarbaricInstinct extends ITokenProviderItem<BarbaricInstinct.Data> {

	private static final AttrAdder MAGIC = AttrAdder.of("barbaric_instinct", L2DamageTracker.MAGIC_FACTOR::get,
			AttributeModifier.Operation.ADDITION, BarbaricInstinct::getStat);

	private static double getStat() {
		return -CoPConfig.COMMON.barbaric.magicDamageDebuff.get();
	}

	private static double getHeal() {
		return CoPConfig.COMMON.barbaric.barbaricInstinctHeal.get();
	}

	private static int getCD() {
		return CoPConfig.COMMON.barbaric.barbaricInstinctCoolDown.get();
	}

	private static int getIndexReq() {
		return CoPConfig.COMMON.barbaric.barbaricInstinctRealityIndex.get();
	}

	public BarbaricInstinct(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(MAGIC.getTooltip())
				.withStyle(pass ? ChatFormatting.RED : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Barbaric.INSTINCT.get(
				(int) Math.round(getHeal() * 100),
				(int) Math.round(getCD() / 20d)
		)).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		private int cooldown = 0;

		@Override
		protected void removeImpl(Player player) {
			MAGIC.removeImpl(player);
		}

		@Override
		protected void tickImpl(Player player) {
			if (player.level().isClientSide()) return;
			MAGIC.tickImpl(player);
			if (cooldown > 0) cooldown--;
		}

		@Override
		public void onPlayerAttackTarget(Player player, AttackCache cache) {
			var event = cache.getLivingAttackEvent();
			assert event != null;
			if (cooldown > 0) return;
			if (event.getSource().getMsgId().equals("player")) {
				if (cache.getStrength() < 0.9f) return;
				if (isWeapon(player.getMainHandItem())) {
					player.heal((float) (getHeal() * player.getMaxHealth()));
					cooldown = getCD();
				}
			}
		}

		private static boolean isWeapon(ItemStack stack) {
			return stack.getAttributeModifiers(EquipmentSlot.MAINHAND)
					.containsKey(Attributes.ATTACK_DAMAGE);
		}
	}

}
