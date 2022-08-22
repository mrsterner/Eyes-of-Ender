package dev.mrsterner.eyesofender.mixin;

import dev.mrsterner.eyesofender.common.utils.TimeStopUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Holder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {
    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, Holder<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Shadow
    public abstract void setTimeOfDay(long timeOfDay);

    @Inject(method = "tickEntity", at = @At("HEAD"), cancellable = true)
    private void eyesOfEnder$doNotTickEntityWhenTimeIsStopped(Entity entity, CallbackInfo ci) {
        if (entity.world != null && TimeStopUtils.getTimeStoppedTicks(entity.world) > 0 && TimeStopUtils.isInRangeOfTimeStop(entity)) {
            entity.prevHorizontalSpeed = entity.horizontalSpeed;
            entity.prevPitch = entity.getPitch();
            entity.prevYaw = entity.getYaw();
            entity.prevX = entity.getX();
            entity.prevY = entity.getY();
            entity.prevZ = entity.getZ();
            entity.lastRenderX = entity.getX();
            entity.lastRenderY = entity.getY();
            entity.lastRenderZ = entity.getZ();
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.prevBodyYaw = livingEntity.bodyYaw;
                livingEntity.prevHeadYaw = livingEntity.headYaw;
                livingEntity.lastHandSwingProgress = livingEntity.handSwingProgress;
                livingEntity.lastLimbDistance = livingEntity.limbDistance;
            }
            ci.cancel();
        }
    }

    @Inject(method = "tickBlock", at = @At("HEAD"), cancellable = true)
    private void eyesOfEnder$doNotTickBlock (BlockPos pos, Block block, CallbackInfo ci) {
        ServerWorld world = (ServerWorld) (Object) this;
        if (world != null && TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(TimeStopUtils.getTimeStopper(world))){
            ci.cancel();
        }
    }

    @Inject(method = "tickChunk", at = @At("HEAD"), cancellable = true)
    private void eyesOfEnder$tickChunk (WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        ServerWorld world = (ServerWorld) (Object) this;
        if (world != null && TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(TimeStopUtils.getTimeStopper(world))){
            ci.cancel();
        }
    }



    @Inject(method = "tickPassenger", at = @At("HEAD"), cancellable = true)
    private void eyesOfEnder$doNotTickPassenger (Entity vehicle, Entity passenger, CallbackInfo ci) {
        ServerWorld world = (ServerWorld) (Object) this;
        if (world != null && TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(TimeStopUtils.getTimeStopper(world))){
            ci.cancel();
        }
    }

    @Inject(method = "tickSpawners", at = @At("HEAD"), cancellable = true)
    private void eyesOfEnder$doNotTickSpawners (boolean spawnMonsters, boolean spawnAnimals, CallbackInfo ci) {
        ServerWorld world = (ServerWorld) (Object) this;
        if (world != null && TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(TimeStopUtils.getTimeStopper(world))){
            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setTimeOfDay(J)V", shift = At.Shift.AFTER))
    public void eyesOfEnder$changeCoffinWakeTime(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if (isDay()) {
            long time = getTimeOfDay() - 24000;
            while (time % 24000 < 13000) {
                time++;
            }
            setTimeOfDay(time);
        }
    }
}