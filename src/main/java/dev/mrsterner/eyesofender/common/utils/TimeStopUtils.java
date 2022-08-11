package dev.mrsterner.eyesofender.common.utils;

import com.mojang.datafixers.util.Pair;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class TimeStopUtils {
    public static long getTimeStoppedTicks(World world) {
        return EOEComponents.TIME_STOP_COMPONENT.get(world).getTimeStoppedTicks();
    }

    public static void setTimeStoppedTicks(World world, long ticks) {
        EOEComponents.TIME_STOP_COMPONENT.get(world).setTimeStoppedTicks(ticks);
    }

    public static PlayerEntity getTimeStopper(World world) {
        return EOEComponents.TIME_STOP_COMPONENT.get(world).getTimeStopper();
    }

    public static void setTimeStopper(World world, PlayerEntity timeStopper) {
        EOEComponents.TIME_STOP_COMPONENT.get(world).setTimeStopper(timeStopper);
    }

    public static boolean isInRangeOfTimeStop(Entity entity) {
        PlayerEntity stopper = getTimeStopper(entity.world);
        if (stopper != null) {
            return entity.squaredDistanceTo(stopper) < 4096;
        }
        return true;
    }

    public static boolean isInRangeOfTimeStop(BlockPos pos, World world) {
        PlayerEntity stopper = getTimeStopper(world);
        if (stopper != null) {
            return stopper.squaredDistanceTo(Vec3d.ofCenter(pos)) < 4096;
        }
        return true;
    }


}
