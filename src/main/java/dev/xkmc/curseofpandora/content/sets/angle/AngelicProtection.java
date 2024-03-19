package dev.xkmc.curseofpandora.content.sets.angle;

import dev.xkmc.curseofpandora.event.ClientSpellText;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.l2complements.content.item.curios.CurioItem;
import dev.xkmc.l2damagetracker.contents.curios.L2Totem;
import dev.xkmc.l2damagetracker.contents.curios.TotemUseToClient;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class AngelicProtection extends CurioItem implements L2Totem {

	public AngelicProtection(Properties properties) {
		super(properties);
	}

	public static int getIndexReq() {
		return CoPConfig.COMMON.angelic.angelicProtectionRealityIndex.get();
	}

	public static int getCoolDown() {
		return CoPConfig.COMMON.angelic.angelicProtectionCoolDown.get();
	}

	@Override
	public void trigger(LivingEntity self, ItemStack holded, Consumer<ItemStack> second) {
		L2DamageTracker.PACKET_HANDLER.toTrackingPlayers(new TotemUseToClient(self, holded), self);
		self.setHealth(self.getMaxHealth());
		self.removeAllEffects();
		self.hasImpulse = true;
		if (self instanceof Player player) {
			player.getCooldowns().addCooldown(this, getCoolDown());
		}
	}

	@Override
	public boolean allow(LivingEntity self, DamageSource pDamageSource) {
		if (!(self instanceof Player player)) return false;
		if (!AngelicPunishment.check(player, getIndexReq())) return false;
		return !player.getCooldowns().isOnCooldown(this);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(CoPLangData.Angelic.CHECK.get().withStyle(ChatFormatting.GRAY));
		boolean pass = ClientSpellText.getReality(level) >= getIndexReq();
		list.add(CoPLangData.IDS.REALITY_INDEX.get(getIndexReq())
				.withStyle(pass ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
		list.add(CoPLangData.Angelic.PROTECTION.get(Math.round(getCoolDown() / 20f))
				.withStyle(pass ? ChatFormatting.DARK_AQUA : ChatFormatting.DARK_GRAY));
	}

}
