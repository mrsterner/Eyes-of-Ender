package dev.mrsterner.eyesofender.api.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface AttackLivingEntityCallback {
    Event<AttackLivingEntityCallback> EVENT = EventFactory.createArrayBacked(AttackLivingEntityCallback.class,
            (listeners) -> (livingEntity, world, hand, entity, hitResult) -> {
                for (AttackLivingEntityCallback event : listeners) {
                    ActionResult result = event.interact(livingEntity, world, hand, entity, hitResult);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult interact(LivingEntity livingEntity, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult);
}
