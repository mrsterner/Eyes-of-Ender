package dev.mrsterner.eyesofender.mixin.client;

import dev.mrsterner.eyesofender.common.utils.TimeStopUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.mrsterner.eyesofender.common.utils.TimeStopUtils.getTimeStopper;

@Mixin(SpriteAtlasTexture.class)
public class SpriteAtlasTextureMixin {
    @Inject(method = "tickAnimatedSprites", at = @At("HEAD"), cancellable = true)
    private void eyesOfEnder$doNotTickAnimatedTextures (CallbackInfo ci) {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (world != null && TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(getTimeStopper(world))){
            ci.cancel();
        }
    }
}