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

public interface SwingHandEntityCallback {
    Event<SwingHandEntityCallback> EVENT = EventFactory.createArrayBacked(SwingHandEntityCallback.class,
            (listeners) -> (livingEntity, world, hand) -> {
                for (SwingHandEntityCallback event : listeners) {
                    ActionResult result = event.swing(livingEntity, world, hand);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult swing(LivingEntity livingEntity, World world, Hand hand);
}
