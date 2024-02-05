package dev.xkmc.curseofpandora.event;

import dev.xkmc.curseofpandora.content.sets.evil.EvilSpiritRitual;
import dev.xkmc.curseofpandora.content.sets.hell.CrownOfDemon;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.loot.LootDataToClient;
import dev.xkmc.curseofpandora.init.loot.MobKillMobLootModifier;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.curseofpandora.mixin.ForgeInternalHandlerAccessor;
import dev.xkmc.l2library.capability.conditionals.ConditionalData;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = CurseOfPandora.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PandoraEvents {

	public static boolean cannotAttack(Mob mob, Player player) {
		return CrownOfDemon.isPeon(mob) && CrownOfDemon.check(player);
	}


	@SubscribeEvent
	public static void onDatapackSync(OnDatapackSyncEvent event) {
		List<MobKillMobLootModifier> list = new ArrayList<>();
		for (var e : ForgeInternalHandlerAccessor.callGetLootModifierManager().getAllLootMods()) {
			if (e instanceof MobKillMobLootModifier loot) {
				list.add(loot);
			}
		}
		LootDataToClient packet = new LootDataToClient(list);
		if (event.getPlayer() == null) {
			CurseOfPandora.HANDLER.toAllClient(packet);
		} else {
			CurseOfPandora.HANDLER.toClientPlayer(packet, event.getPlayer());
		}
	}

	@SubscribeEvent
	public static void onExpDrop(LivingExperienceDropEvent event) {
		if (event.getAttackingPlayer() == null) return;
		if (ConditionalData.HOLDER.get(event.getAttackingPlayer())
				.hasData(CoPItems.EVIL_SPIRIT_RITUAL.get().getKey())) {
			event.setDroppedExperience((int) (event.getDroppedExperience() +
					event.getEntity().getMaxHealth() * EvilSpiritRitual.getFactor()));
		}
	}

	@SubscribeEvent
	public static void onMobDeath(LivingDeathEvent event) {
		if (!(event.getSource().getEntity() instanceof Player player)) return;
		var data = ConditionalData.HOLDER.get(player)
				.getData(CoPItems.EVIL_SPIRIT_AWAKENING.get().getKey());
		if (data != null) {
			data.trigger(player);
		}
	}

}
