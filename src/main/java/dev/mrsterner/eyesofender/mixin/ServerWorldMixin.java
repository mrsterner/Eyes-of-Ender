package dev.mrsterner.eyesofender.mixin;

import dev.mrsterner.eyesofender.common.utils.TimeStopUtils;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Inject(method = "tickEntity", at = @At("HEAD"), cancellable = true)
    void doNotTickEntityWhenTimeIsStopped(Entity entity, CallbackInfo ci) {
        if (TimeStopUtils.getTimeStoppedTicks(entity.world) > 0 && TimeStopUtils.isInRangeOfTimeStop(entity)) {
            entity.prevHorizontalSpeed = entity.horizontalSpeed;
            entity.prevPitch = entity.getPitch();
            entity.prevYaw = entity.getYaw();
            entity.prevX = entity.getX();
            entity.prevY = entity.getY();
            entity.prevZ = entity.getZ();
            entity.lastRenderX = entity.getX();
            entity.lastRenderY = entity.getY();
            entity.lastRenderZ = entity.getZ();
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.prevBodyYaw = livingEntity.bodyYaw;
                livingEntity.prevHeadYaw = livingEntity.headYaw;
                livingEntity.lastHandSwingProgress = livingEntity.handSwingProgress;
                livingEntity.lastLimbDistance = livingEntity.limbDistance;
            }
            ci.cancel();
        }
    }

    @Inject(method = "tickBlock", at = @At("HEAD"), cancellable = true)
    private void doNotTickBlock (BlockPos pos, Block block, CallbackInfo ci) {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(TimeStopUtils.getTimeStopper(world))){
            ci.cancel();
        }
    }

    @Inject(method = "tickChunk", at = @At("HEAD"), cancellable = true)
    private void tickChunk (WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(TimeStopUtils.getTimeStopper(world))){
            ci.cancel();
        }
    }



    @Inject(method = "tickPassenger", at = @At("HEAD"), cancellable = true)
    private void doNotTickPassenger (Entity vehicle, Entity passenger, CallbackInfo ci) {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(TimeStopUtils.getTimeStopper(world))){
            ci.cancel();
        }
    }

    @Inject(method = "tickSpawners", at = @At("HEAD"), cancellable = true)
    private void doNotTickSpawners (boolean spawnMonsters, boolean spawnAnimals, CallbackInfo ci) {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(TimeStopUtils.getTimeStopper(world))){
            ci.cancel();
        }
    }
}