package dev.xkmc.curseofpandora.content.sets.angle;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AngelicDescent extends ITokenProviderItem<AngelicDescent.Data> {

	private static final AttrAdder ATTACK = AttrAdder.of("angelic_descent", () -> Attributes.ATTACK_DAMAGE,
			AttributeModifier.Operation.MULTIPLY_BASE, AngelicDescent::getStat);

	private static double getStat() {
		return CoPConfig.COMMON.angelicDescentMeleeBonus.get();
	}

	private static int getIndexReq() {
		return CoPConfig.COMMON.angelicDescentRealityIndex.get();
	}

	private static double getBonus() {
		return CoPConfig.COMMON.angelicDescentUndeadBonus.get();
	}

	public AngelicDescent(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(CoPLangData.Angelic.CHECK.get().withStyle(ChatFormatting.GRAY));
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Angelic.DESCENT.get(Math.round(getBonus() * 100)))
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(ATTACK.getTooltip())
				.withStyle(pass ? ChatFormatting.BLUE : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPMisc.REALITY.get()) >= getIndexReq())
			super.tick(player);
	}

	@SerialClass
	public static class Data extends BaseTickingToken implements IAttackListenerToken {

		@Override
		public void onPlayerHurtTarget(Player player, AttackCache cache) {
			float bonus = 1 + (float) getBonus();
			if (cache.getAttackTarget().getMobType() == MobType.UNDEAD && check(player)) {
				cache.addHurtModifier(DamageModifier.multTotal(bonus));
			}
		}

		@Override
		protected void removeImpl(Player player) {
			ATTACK.removeImpl(player);
		}

		@Override
		protected void tickImpl(Player player) {
			if (player.level().isClientSide()) return;
			if (check(player)) {
				ATTACK.tickImpl(player);
			} else {
				ATTACK.removeImpl(player);
			}
		}

		private boolean check(Player player) {
			return AngelicPunishment.check(player, getIndexReq());
		}

	}

}
