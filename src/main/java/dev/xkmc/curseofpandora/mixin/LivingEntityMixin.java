package dev.xkmc.curseofpandora.mixin;

import dev.xkmc.curseofpandora.event.PandoraEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Inject(at = @At("HEAD"), cancellable = true, method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z")
	public void curseOfPandora$canAttack$crownOfDemon(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
		Entity self = this;
		if (target instanceof Player player && self instanceof Mob mob) {
			if (PandoraEvents.cannotAttack(mob, player)) {
				cir.setReturnValue(false);
			}
		}
	}

}
