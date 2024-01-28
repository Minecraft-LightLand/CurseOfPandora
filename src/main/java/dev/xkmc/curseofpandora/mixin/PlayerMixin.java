package dev.xkmc.curseofpandora.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {

	@WrapOperation(at = @At(value = "INVOKE", target="Lnet/minecraft/world/entity/player/Player;setSprinting(Z)V"), method = "attack")
	public void curseOfPandora$attack$setSprint(Player player, boolean sprint, Operation<Void> op) {

	}

}
