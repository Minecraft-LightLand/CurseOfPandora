package dev.xkmc.curseofpandora.event;

import dev.xkmc.curseofpandora.content.complex.ITokenProviderItem;
import dev.xkmc.curseofpandora.content.sets.evil.EvilSpiritRitual;
import dev.xkmc.curseofpandora.content.sets.hell.CrownOfDemon;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.curseofpandora.init.loot.LootDataToClient;
import dev.xkmc.curseofpandora.init.loot.MobKillMobLootModifier;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.curseofpandora.mixin.NeoForgeEventHandlerAccessor;
import dev.xkmc.l2core.init.L2LibReg;
import dev.xkmc.pandora.content.base.IPandoraHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = CurseOfPandora.MODID, bus = EventBusSubscriber.Bus.GAME)
public class PandoraEvents {

	public static boolean cannotAttack(Mob mob, Player player) {
		return CrownOfDemon.isPeon(mob) && CrownOfDemon.check(player);
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		var player = event.getEntity();
		var inv = CuriosApi.getCuriosInventory(player);
		if (inv.isEmpty()) return;
		for (var e : inv.get().findCurios(e -> true)) {
			if (e.stack().getItem() instanceof ITokenProviderItem<?> item) {
				item.tickToken(player);
			}
			if (e.stack().getItem() instanceof IPandoraHolder) {
				for (var inner : IPandoraHolder.getItems(e.stack())) {
					if (inner.getItem() instanceof ITokenProviderItem<?> item) {
						item.tickToken(player);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onDatapackSync(OnDatapackSyncEvent event) {
		List<MobKillMobLootModifier> list = new ArrayList<>();
		for (var e : NeoForgeEventHandlerAccessor.callGetLootModifierManager().getAllLootMods()) {
			if (e instanceof MobKillMobLootModifier loot) {
				list.add(loot);
			}
		}
		LootDataToClient packet = LootDataToClient.of(list);
		if (event.getPlayer() == null) {
			CurseOfPandora.HANDLER.toAllClient(packet);
		} else {
			CurseOfPandora.HANDLER.toClientPlayer(packet, event.getPlayer());
		}
	}

	@SubscribeEvent
	public static void onExpDrop(LivingExperienceDropEvent event) {
		if (event.getAttackingPlayer() == null) return;
		if (L2LibReg.CONDITIONAL.type().getOrCreate(event.getAttackingPlayer())
				.hasData(CoPItems.EVIL_SPIRIT_RITUAL.get().getKey())) {
			event.setDroppedExperience((int) (event.getDroppedExperience() +
					event.getEntity().getMaxHealth() * EvilSpiritRitual.getFactor()));
		}
	}

	@SubscribeEvent
	public static void onMobDeath(LivingDeathEvent event) {
		if (!(event.getSource().getEntity() instanceof Player player)) return;
		var data = L2LibReg.CONDITIONAL.type().getOrCreate(player)
				.getData(CoPItems.EVIL_SPIRIT_AWAKENING.get().getKey());
		if (data != null) {
			data.trigger(player);
		}
	}

}
