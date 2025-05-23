package dev.xkmc.curseofpandora.event;

import dev.xkmc.curseofpandora.content.reality.CurseOfSpellItem;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPConfig;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2core.init.L2LibReg;
import dev.xkmc.l2library.content.raytrace.RayTraceUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@EventBusSubscriber(value = Dist.CLIENT, modid = CurseOfPandora.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ClientSpellText {

	public static void addTotal(List<Component> list) {
		Player player = Minecraft.getInstance().player;
		if (player == null) return;
		int load = (int) Math.round(CurseOfSpellItem.getSpellPenalty(player) * 100);
		list.add(CoPLangData.Reality.SPELL_2.get(load).withStyle(ChatFormatting.RED));
	}

	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent event) {
		if (event.getEntity() == null) return;
		if (L2LibReg.CONDITIONAL.type().getOrCreate(event.getEntity()).getData(CurseOfSpellItem.KEY) == null) return;
		if (!event.getItemStack().isEnchanted()) return;
		double bonus = event.getEntity().getAttributeValue(CoPAttrs.SPELL);
		bonus = Math.max(1, bonus);
		var access = event.getEntity().registryAccess();
		double penalty = CurseOfSpellItem.getItemSpellPenalty(bonus, event.getItemStack(), access);
		int load = (int) Math.round(penalty * 100);
		if (Screen.hasShiftDown()) {
			var reg = access.lookupOrThrow(Registries.ENCHANTMENT);
			double level = 0;
			for (var ent : event.getItemStack().getAllEnchantments(reg).entrySet()) {
				int i = ent.getIntValue();
				if (i > 0) {
					level += Math.pow(2, i - 1);
				}
			}
			var clevel = Component.literal("" + (int) level).withStyle(ChatFormatting.RED);
			var base = Component.literal("" + (int) bonus).withStyle(ChatFormatting.BLUE);
			var ench = Component.literal("" + event.getItemStack().getEnchantmentValue()).withStyle(ChatFormatting.GOLD);
			var perc = Component.literal("" + (int) (CoPConfig.SERVER.curse.curseOfSpellLoadFactor.get() * 100)).withStyle(ChatFormatting.GRAY);
			var cload = Component.literal("" + load).withStyle(load > 100 ? ChatFormatting.RED : ChatFormatting.GREEN);
			event.getToolTip().add(CoPLangData.Reality.SPELL_4.get(clevel, base, ench, perc, cload)
					.withStyle(ChatFormatting.GRAY));
		} else {
			event.getToolTip().add(CoPLangData.Reality.SPELL_3.get(load).withStyle(load > 100 ? ChatFormatting.RED : ChatFormatting.GRAY));
		}

	}

	public static int getReality(@Nullable Level level) {
		if (level == null) return 0;
		Player player = Minecraft.getInstance().player;
		if (player == null) return 0;
		var ins = player.getAttribute(CoPAttrs.REALITY);
		return ins == null ? 0 : (int) Math.round(ins.getValue());
	}

	public static void onClientAutoAttack(Player player) {
		if (!player.isLocalPlayer()) return;
		var mode = Minecraft.getInstance().gameMode;
		if (mode == null) return;
		var cd = player.getAttackStrengthScale(1);
		if (cd < 1) return;
		var hit = RayTraceUtil.rayTraceEntity(player, player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE), e -> true);
		if (hit == null) return;
		var entity = hit.getEntity();
		if (!entity.isAlive()) return;
		if (!(entity instanceof ItemEntity) && !(entity instanceof ExperienceOrb) && !(entity instanceof AbstractArrow)) {
			mode.attack(player, hit.getEntity());
			player.swing(InteractionHand.MAIN_HAND);
		}
	}

	public static Component getDepth(@Nullable Level level) {
		int def = CoPConfig.SERVER.abyssal.abyssalDepthStep.get();
		if (level != null) {
			Player player = Minecraft.getInstance().player;
			if (player != null) {
				if (L2LibReg.CONDITIONAL.type().getOrCreate(player).hasData(CoPItems.ABYSSAL_WILL.get().getKey())) {
					int val = CoPConfig.SERVER.abyssal.abyssalWillDepthStep.get();
					return Component.literal(val + "").withStyle(ChatFormatting.YELLOW);
				}
			}
		}
		return Component.literal(def + "").withStyle(ChatFormatting.GRAY);
	}

	@Nullable
	public static Player getPlayer(@Nullable Level level) {
		if (level == null || !level.isClientSide) return null;
		return Minecraft.getInstance().player;
	}
}
