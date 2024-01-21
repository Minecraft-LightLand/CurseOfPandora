package dev.xkmc.curseofpandora.event;

import dev.xkmc.curseofpandora.content.reality.CurseOfSpellItem;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.data.CoPLangData;
import dev.xkmc.curseofpandora.init.registrate.CoPMisc;
import dev.xkmc.l2library.capability.conditionals.ConditionalData;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
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
		list.add(CoPLangData.IDS.CURSE_OF_SPELL_2.get(load).withStyle(ChatFormatting.RED));
	}

	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent event) {
		if (event.getEntity() == null) return;
		if (ConditionalData.HOLDER.get(event.getEntity()).getData(CurseOfSpellItem.KEY) == null) return;
		if (!event.getItemStack().isEnchanted()) return;
		double bonus = event.getEntity().getAttributeValue(CoPMisc.SPELL.get());
		bonus = Math.max(1, bonus);
		double penalty = CurseOfSpellItem.getItemSpellPenalty(bonus, event.getItemStack());
		int load = (int) Math.round(penalty * 100);
		event.getToolTip().add(CoPLangData.IDS.CURSE_OF_SPELL_3.get(load).withStyle(load > 100 ? ChatFormatting.RED : ChatFormatting.GRAY));
	}

	public static int getReality(@Nullable Level level) {
		if (level == null) return 0;
		Player player = Proxy.getClientPlayer();
		if (player == null) return 0;
		var ins = player.getAttribute(CoPMisc.REALITY.get());
		return ins == null ? 0 : (int) Math.round(ins.getValue());
	}
}
