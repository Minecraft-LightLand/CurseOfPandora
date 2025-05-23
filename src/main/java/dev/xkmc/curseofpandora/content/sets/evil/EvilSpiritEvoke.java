package dev.xkmc.curseofpandora.content.sets.evil;

import dev.xkmc.curseofpandora.content.complex.BaseTickingToken;
import dev.xkmc.curseofpandora.content.complex.IAttackListenerToken;
import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.content.entity.EvilSpirit;
import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.curseofpandora.init.registrate.CoPEntities;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.EventHooks;

import java.util.List;

public class EvilSpiritEvoke extends ITokenProviderItem<EvilSpiritEvoke.Data> {

	private static int getIndexReq() {
		return CoPConfig.SERVER.evil.evilSpiritEvokeRealityIndex.get();
	}

	private static int getLife() {
		return CoPConfig.SERVER.evil.evilSpiritEvokeDuration.get();
	}

	private static int getCD() {
		return CoPConfig.SERVER.evil.evilSpiritEvokeCoolDown.get();
	}

	public EvilSpiritEvoke(Properties properties) {
		super(properties, Data::new);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		boolean pass = ClientSpellText.getReality(ctx.level()) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Evil.EVOKE.get(
				(int) Math.round(getLife() / 20d),
				(int) Math.round(getCD() / 20d)
		)).withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
		list.add(Component.literal("- ").append(CoPLangData.Evil.EVOKE_CONVERT.get())
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

	@Override
	public void tick(Player player) {
		if (player.getAttributeValue(CoPAttrs.REALITY) >= getIndexReq())
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
		public boolean onPlayerAttacked(Player player, DamageData.Attack data) {
			if (data.getAttacker() instanceof Vex vex && player instanceof ServerPlayer sp) {
				var sl = sp.serverLevel();
				var spirit = vex.convertTo(CoPEntities.EVIL_SPIRIT.get(), false);
				if (spirit == null) return false;
				for (EquipmentSlot slot : EquipmentSlot.values()) {
					ItemStack stack = vex.getItemBySlot(slot);
					if (!stack.isEmpty()) {
						spirit.setItemSlot(slot, stack);
					}
				}
				spirit.finalizeSpawn(sl, sl.getCurrentDifficultyAt(spirit.blockPosition()), MobSpawnType.CONVERSION, null);
				spirit.setLimitedLife(getLife());
				spirit.setOwner(player);
				if (spirit.getMainHandItem().isEmpty())
					spirit.setItemInHand(InteractionHand.MAIN_HAND, Items.IRON_SWORD.getDefaultInstance());
				if (vex.getOwner() != null) spirit.setTarget(vex.getOwner());
				EventHooks.onLivingConvert(vex, spirit);
				spirit.level().addFreshEntity(spirit);
				return true;
			}
			return false;
		}

		@Override
		public void onPlayerDamaged(Player player, DamageData.Defence data) {
			var item = CoPItems.EVIL_SPIRIT_EVOKE.get();
			if (data.getAttacker() != null && !player.getCooldowns().isOnCooldown(item)) {
				player.getCooldowns().addCooldown(item, getCD());
				EvilSpirit vex = new EvilSpirit(player);
				vex.setOwner(player);
				vex.setLimitedLife(getLife());
				vex.setItemInHand(InteractionHand.MAIN_HAND, Items.IRON_SWORD.getDefaultInstance());
				vex.setTarget(data.getAttacker());
				player.level().addFreshEntity(vex);
			}
		}

	}

}
