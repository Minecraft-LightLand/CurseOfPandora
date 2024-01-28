package dev.xkmc.curseofpandora.event;

import dev.xkmc.curseofpandora.content.sets.hell.CrownOfDemon;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CurseOfPandora.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PandoraEvents {

	public static boolean cannotAttack(Mob mob, Player player) {
		return CrownOfDemon.isPeon(mob) && CrownOfDemon.check(player);
	}

}
