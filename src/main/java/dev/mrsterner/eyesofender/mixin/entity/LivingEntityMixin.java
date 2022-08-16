package dev.mrsterner.eyesofender.mixin.entity;


import dev.mrsterner.eyesofender.client.registry.EOESoundEvents;
import dev.mrsterner.eyesofender.common.block.CoffinBlock;
import dev.mrsterner.eyesofender.common.utils.TimeStopUtils;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

import static dev.mrsterner.eyesofender.common.block.CoffinBlock.OPEN;

@SuppressWarnings("ConstantConditions")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    protected abstract float getSoundVolume();

    @Shadow
    public abstract float getSoundPitch();

    @Shadow public abstract Optional<BlockPos> getSleepingPosition();

    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void eyesOfEnder$tick(CallbackInfo callbackInfo) {
        if (world.isClient) {
            if (world != null && TimeStopUtils.getTimeStoppedTicks(world) == 1) {
                this.playSound(EOESoundEvents.THE_WORLD_END, this.getSoundVolume(), this.getSoundPitch());
            }
        }
    }

    @Inject(method = "wakeUp", at = @At(value = "HEAD"))
    private void eyesOfEnder$wakeUpCoffin(CallbackInfo ci){
        this.getSleepingPosition().filter(this.world::isChunkLoaded).ifPresent(pos -> {
            BlockState blockState = this.world.getBlockState(pos);
            if (blockState.getBlock() instanceof CoffinBlock) {
                blockState = blockState.cycle(OPEN);
                world.setBlockState(pos, blockState, 10);
            }
        });
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getDepthStrider(Lnet/minecraft/entity/LivingEntity;)I"))
    private int eyesOfEnder$doNotGetPushedAroundByWater(LivingEntity entity) {
        return world != null && TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(TimeStopUtils.getTimeStopper(world)) ? 3 : EnchantmentHelper.getDepthStrider(entity);
    }
}
