package dev.mrsterner.eyesofender.mixin.client;

import dev.mrsterner.eyesofender.api.events.InGameHudEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {

    @Shadow
    private int scaledHeight;

    @Shadow
    private int scaledWidth;

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Shadow private int ticks;

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", shift = At.Shift.AFTER, ordinal = 0, target = "Lnet/minecraft/client/MinecraftClient;getProfiler()Lnet/minecraft/util/profiler/Profiler;"))
    private void eyesOfEnder$inGameHudEventsAfterArmor(MatrixStack matrices, CallbackInfo callbackInfo) {
        InGameHudEvents.AFTER_ARMOR.invoker().afterArmor(matrices, getCameraPlayer(), ticks, scaledWidth, scaledHeight);
    }

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", shift = At.Shift.AFTER, ordinal = 1, target = "Lnet/minecraft/client/MinecraftClient;getProfiler()Lnet/minecraft/util/profiler/Profiler;"))
    private void eyesOfEnder$inGameHudEventsAfterHealth(MatrixStack matrices, CallbackInfo callbackInfo) {
        InGameHudEvents.AFTER_HEALTH.invoker().afterHealth(matrices, getCameraPlayer(), ticks, scaledWidth, scaledHeight);
    }

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", shift = At.Shift.AFTER, ordinal = 2, target = "Lnet/minecraft/client/MinecraftClient;getProfiler()Lnet/minecraft/util/profiler/Profiler;"))
    private void eyesOfEnder$inGameHudEventsAfterFood(MatrixStack matrices, CallbackInfo callbackInfo) {
        InGameHudEvents.AFTER_FOOD.invoker().afterFood(matrices, getCameraPlayer(), ticks, scaledWidth, scaledHeight);
    }

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", shift = At.Shift.AFTER, ordinal = 3, target = "Lnet/minecraft/client/MinecraftClient;getProfiler()Lnet/minecraft/util/profiler/Profiler;"))
    private void eyesOfEnder$inGameHudEventsAfterAir(MatrixStack matrices, CallbackInfo callbackInfo) {
        InGameHudEvents.AFTER_AIR.invoker().afterAir(matrices, getCameraPlayer(), ticks, scaledWidth, scaledHeight);
    }
}
