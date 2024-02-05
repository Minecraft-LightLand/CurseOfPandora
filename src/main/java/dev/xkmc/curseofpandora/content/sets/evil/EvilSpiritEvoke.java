package dev.xkmc.curseofpandora.content.sets.evil;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.content.entity.EvilSpirit;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2damagetracker.contents.attack.AttackCache;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EvilSpiritEvoke extends ITokenProviderItem<EvilSpiritEvoke.Data> {

	private static int getIndexReq() {
		return CoPConfig.COMMON.evil.evilSpiritEvokeRealityIndex.get();
	}

	private static int getLife() {
		return CoPConfig.COMMON.evil.evilSpiritEvokeDuration.get();
	}

	private static int getCD() {
		return CoPConfig.COMMON.evil.evilSpiritEvokeCoolDown.get();
	}

	public EvilSpiritEvoke(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Evil.EVOKE.get(
				(int) Math.round(getLife() / 20d),
				(int) Math.round(getCD() / 20d)
		).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
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

		}

		@Override
		protected void tickImpl(Player player) {

		}

		@Override
		public void onPlayerDamaged(Player player, AttackCache cache) {
			var item = CoPItems.EVIL_SPIRIT_EVOKE.get();
			if (cache.getAttacker() != null && !player.getCooldowns().isOnCooldown(item)) {
				player.getCooldowns().addCooldown(item, getCD());
				EvilSpirit vex = new EvilSpirit(player);
				vex.setLimitedLife(getLife());
				vex.setItemInHand(InteractionHand.MAIN_HAND, Items.IRON_SWORD.getDefaultInstance());
				vex.setTarget(cache.getAttacker());
				player.level().addFreshEntity(vex);
			}
		}
	}

}
