package dev.xkmc.curseofpandora.mixin.enigmaticlegacy;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.xkmc.curseofpandora.compat.enigmaticlegacy.EnigmaticLegacyCompat;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "com.aizistral.enigmaticlegacy.handlers.EnigmaticEventHandler")
public class EnigmaticEventHandlerMixin {

	@WrapOperation(method = "onPlayerTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isSleeping()Z"))
	private boolean curseofpandora$isSleeping(Player instance, Operation<Boolean> original) {
		return original.call(instance) && !EnigmaticLegacyCompat.suppressInsomnia(instance);
	}

}
