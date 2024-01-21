package dev.xkmc.curseofpandora.content.sets.angle;

import dev.xkmc.curseofpandora.content.complex.AttrAdder;
import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.event.ClientSpellText;
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

	private static final AttrAdder ATTACK = AttrAdder.of("angelic_descent", () -> Attributes.ATTACK_DAMAGE, AttributeModifier.Operation.MULTIPLY_BASE, 0.5);

	private static int getIndexReq() {
		return 5;
	}

	private static double getBonus() {
		return 1;
	}

	public AngelicDescent(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(CoPLangData.IDS.ANGELIC_CHECK.get().withStyle(ChatFormatting.GRAY));
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(CoPLangData.IDS.ANGELIC_DESCENT.get(Math.round(getBonus() * 100)))
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(ATTACK.getTooltip())
				.withStyle(pass ? ChatFormatting.BLUE : ChatFormatting.DARK_GRAY));
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
			int reality = getIndexReq();
			return player.level().canSeeSky(player.blockPosition()) && player.getAttributeValue(CoPMisc.REALITY.get()) >= reality;
		}

	}

}
