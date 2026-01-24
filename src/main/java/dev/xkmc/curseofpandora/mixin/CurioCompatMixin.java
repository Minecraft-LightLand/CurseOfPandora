package dev.xkmc.curseofpandora.mixin;

import dev.xkmc.l2complements.compat.CurioCompat;
import dev.xkmc.l2complements.content.item.curios.EffectValidItem;
import dev.xkmc.pandora.content.base.IPandoraHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CurioCompat.class)
public class CurioCompatMixin {

	@Inject(method = "lambda$testEffectImpl$0", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"))
	private static void curseOfPandora$effectValid(MobEffectInstance ins, LivingEntity entity, ItemStack e, CallbackInfoReturnable<Boolean> cir) {
		if (e.getItem() instanceof IPandoraHolder holder) {
			for (var stack : IPandoraHolder.getItems(e)) {
				if (stack.getItem() instanceof EffectValidItem item) {
					if (item.isEffectValid(ins, e, entity)) {
						cir.setReturnValue(true);
						return;
					}
				}
			}
		}
	}

}
