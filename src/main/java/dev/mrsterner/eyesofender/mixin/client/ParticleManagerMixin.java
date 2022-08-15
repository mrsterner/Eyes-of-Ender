package dev.mrsterner.eyesofender.mixin.client;

import dev.mrsterner.eyesofender.common.utils.TimeStopUtils;
import dev.mrsterner.eyesofender.mixin.access.ParticleAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Queue;

@Environment(EnvType.CLIENT)
@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    @Shadow protected ClientWorld world;

    @Shadow @Final private Map<ParticleTextureSheet, Queue<Particle>> particles;

    @Inject(method = "tickParticle", at = @At("HEAD"), cancellable = true)
    private void eyesOfEnder$doNotTickParticleWhenTimeStopped(Particle particle, CallbackInfo ci) {
        if (world != null && TimeStopUtils.getTimeStoppedTicks(world) > 0) {
            PlayerEntity timeStopper = TimeStopUtils.getTimeStopper(world);
            ParticleAccessor particle1 = (ParticleAccessor) particle;
            if (timeStopper == null || timeStopper.squaredDistanceTo(particle1.getX(), particle1.getY(), particle1.getZ()) < 4096) {
                particle1.setPrevX(particle1.getX());
                particle1.setPrevY(particle1.getY());
                particle1.setPrevZ(particle1.getZ());
                ci.cancel();
            }
        }
    }
}