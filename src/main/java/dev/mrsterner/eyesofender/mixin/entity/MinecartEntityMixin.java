package dev.mrsterner.eyesofender.mixin.entity;

import dev.mrsterner.eyesofender.common.utils.TimeStopUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(MinecartEntity.class)
public abstract class MinecartEntityMixin {

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void eyesOfEnder$noMinecartForYou(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if(player.world != null && TimeStopUtils.getTimeStoppedTicks(player.world) > 0 && TimeStopUtils.isInRangeOfTimeStop(player)){
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}