package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.curseofpandora.event.PandoraEvents;
import dev.xkmc.l2damagetracker.contents.curios.L2Totem;
import dev.xkmc.l2damagetracker.contents.curios.TotemHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IPandoraTotem extends L2Totem {

	@Override
	default boolean isValid(LivingEntity self, ItemStack stack, TotemHelper.TotemSlot slot) {
		return slot instanceof PandoraEvents.PandoraSlot || slot instanceof TotemHelper.CurioPred;
	}

}
