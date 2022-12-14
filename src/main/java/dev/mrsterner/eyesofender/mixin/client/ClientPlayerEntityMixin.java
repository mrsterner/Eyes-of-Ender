package dev.mrsterner.eyesofender.mixin.client;

import com.mojang.authlib.GameProfile;
import dev.mrsterner.eyesofender.common.utils.TimeStopUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.encryption.PlayerPublicKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld clientWorld, GameProfile gameProfile, @Nullable PlayerPublicKey playerPublicKey) {
        super(clientWorld, gameProfile, playerPublicKey);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    protected void eyesOfEnder$doNotLetPlayersMoveWhenTimeIsStopped(CallbackInfo ci) {
        if (world != null && TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(this)) {//TODO add more conditions to determine if the player can move during timestop
            ci.cancel();
        }
    }

}