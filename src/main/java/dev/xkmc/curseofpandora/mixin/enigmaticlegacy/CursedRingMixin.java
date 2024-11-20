package dev.xkmc.curseofpandora.mixin.enigmaticlegacy;

import dev.xkmc.curseofpandora.compat.enigmaticlegacy.EnigmaticLegacyCompat;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.SlotContext;

@Pseudo
@Mixin(targets = "com.aizistral.enigmaticlegacy.items.CursedRing")
public class CursedRingMixin {

	@Inject(at = @At("HEAD"), method = "curioTick", cancellable = true)
	public void curseofpandora$engimaticlegacy$suppressor(SlotContext context, ItemStack stack, CallbackInfo ci) {
		if (context.entity() instanceof ServerPlayer player) {
			if (EnigmaticLegacyCompat.suppressAggro(player)) {
				ci.cancel();
			}
		}
	}

}
