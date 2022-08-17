package dev.mrsterner.eyesofender.api.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.quiltmc.qsl.base.api.event.Event;
import org.quiltmc.qsl.base.api.event.client.ClientEventAwareListener;

public class InGameHudEvents {

    public static final Event<AfterArmor> AFTER_ARMOR = Event.create(AfterArmor.class, callbacks -> (matrices, cameraPlayer, ticks, scaledWidth, scaledHeight) -> {
        for (var callback : callbacks) {
            callback.afterArmor(matrices, cameraPlayer, ticks, scaledWidth, scaledHeight);
        }
    });

    public static final Event<AfterHealth> AFTER_HEALTH = Event.create(AfterHealth.class, callbacks -> (matrices, cameraPlayer, ticks, scaledWidth, scaledHeight) -> {
        for (var callback : callbacks) {
            callback.afterHealth(matrices, cameraPlayer, ticks, scaledWidth, scaledHeight);
        }
    });

    public static final Event<AfterFood> AFTER_FOOD = Event.create(AfterFood.class, callbacks -> (matrices, cameraPlayer, ticks, scaledWidth, scaledHeight) -> {
        for (var callback : callbacks) {
            callback.afterFood(matrices, cameraPlayer, ticks, scaledWidth, scaledHeight);
        }
    });

    public static final Event<AfterAir> AFTER_AIR = Event.create(AfterAir.class, callbacks -> (matrices, cameraPlayer, ticks, scaledWidth, scaledHeight) -> {
        for (var callback : callbacks) {
            callback.afterAir(matrices, cameraPlayer, ticks, scaledWidth, scaledHeight);
        }
    });

    @Environment(EnvType.CLIENT)
    @FunctionalInterface
    public interface AfterArmor extends ClientEventAwareListener {
        void afterArmor(MatrixStack matrices, PlayerEntity cameraPlayer, int ticks, int scaledWidth, int scaledHeight);
    }

    @Environment(EnvType.CLIENT)
    @FunctionalInterface
    public interface AfterHealth extends ClientEventAwareListener {
        void afterHealth(MatrixStack matrices, PlayerEntity cameraPlayer, int ticks, int scaledWidth, int scaledHeight);
    }

    @Environment(EnvType.CLIENT)
    @FunctionalInterface
    public interface AfterFood extends ClientEventAwareListener {
        void afterFood(MatrixStack matrices, PlayerEntity cameraPlayer, int ticks, int scaledWidth, int scaledHeight);
    }

    @Environment(EnvType.CLIENT)
    @FunctionalInterface
    public interface AfterAir extends ClientEventAwareListener {
        void afterAir(MatrixStack matrices, PlayerEntity cameraPlayer, int ticks, int scaledWidth, int scaledHeight);
    }
}
