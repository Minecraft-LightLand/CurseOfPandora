package dev.xkmc.curseofpandora.event;

import dev.xkmc.curseofpandora.content.reality.CurseOfSpellItem;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2library.capability.conditionals.ConditionalData;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2library.util.raytrace.RayTraceUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CurseOfPandora.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientSpellText {

	public static void addTotal(List<Component> list) {
		Player player = Proxy.getClientPlayer();
		if (player == null) return;
		int load = (int) Math.round(CurseOfSpellItem.getSpellPenalty(player) * 100);
		list.add(CoPLangData.Reality.SPELL_2.get(load).withStyle(ChatFormatting.RED));
	}

	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent event) {
		if (event.getEntity() == null) return;
		if (ConditionalData.HOLDER.get(event.getEntity()).getData(CurseOfSpellItem.KEY) == null) return;
		if (!event.getItemStack().isEnchanted()) return;
		double bonus = event.getEntity().getAttributeValue(CoPAttrs.SPELL.get());
		bonus = Math.max(1, bonus);
		double penalty = CurseOfSpellItem.getItemSpellPenalty(bonus, event.getItemStack());
		int load = (int) Math.round(penalty * 100);
		event.getToolTip().add(CoPLangData.Reality.SPELL_3.get(load).withStyle(load > 100 ? ChatFormatting.RED : ChatFormatting.GRAY));
	}

	public static int getReality(@Nullable Level level) {
		if (level == null) return 0;
		Player player = Proxy.getClientPlayer();
		if (player == null) return 0;
		var ins = player.getAttribute(CoPAttrs.REALITY.get());
		return ins == null ? 0 : (int) Math.round(ins.getValue());
	}


	public static void onClientAutoAttack(Player player) {
		if (!player.isLocalPlayer()) return;
		var mode = Minecraft.getInstance().gameMode;
		if (mode == null) return;
		var cd = player.getAttackStrengthScale(1);
		if (cd < 1) return;
		var hit = RayTraceUtil.rayTraceEntity(player, player.getEntityReach(), e -> true);
		if (hit == null) return;
		var entity = hit.getEntity();
		if (!entity.isAlive()) return;
		if (!(entity instanceof ItemEntity) && !(entity instanceof ExperienceOrb) && !(entity instanceof AbstractArrow)) {
			mode.attack(player, hit.getEntity());
			player.swing(InteractionHand.MAIN_HAND);
		}
	}

	public static Component getDepth(Level level) {
		int def = CoPConfig.COMMON.abyssal.abyssalDepthRequirement.get();
		if (level != null) {
			Player player = Proxy.getClientPlayer();
			if (player != null) {
				if (ConditionalData.HOLDER.get(player).hasData(CoPItems.ABYSSAL_WILL.get().getKey())) {
					int val = CoPConfig.COMMON.abyssal.abyssalWillDepthRequirement.get();
					return Component.literal(val + "").withStyle(ChatFormatting.YELLOW);
				}
			}
		}
		return Component.literal(def + "").withStyle(ChatFormatting.GRAY);
	}

}
