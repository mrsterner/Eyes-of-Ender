package dev.mrsterner.eyesofender.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.interfaces.HamonUser;
import dev.mrsterner.eyesofender.client.registry.EOEShaders;
import dev.mrsterner.eyesofender.common.utils.RenderUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {

    @Unique private static float hamonFade = 0.0F;

    @Unique
    private static final Identifier EYES_OF_ENDER_GUI_ICONS_TEXTURE = EyesOfEnder.id("textures/gui/hamon.png");

    @Shadow
    private int scaledHeight;

    @Shadow
    private int scaledWidth;

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Shadow private int ticks;

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", shift = At.Shift.AFTER, ordinal = 0, target = "Lnet/minecraft/client/MinecraftClient;getProfiler()Lnet/minecraft/util/profiler/Profiler;"))
    private void renderRage(MatrixStack matrices, CallbackInfo callbackInfo) {
        PlayerEntity player = getCameraPlayer();
        int y = player.getMaxAir();
        int z = Math.min(player.getAir(), y);

        if(player instanceof HamonUser hamonUser && hamonUser.getHamonLevel() != Hamon.EMPTY) {
            if(this.ticks % 4 == 0 && hamonFade < 1.0F && !(player.isSubmergedIn(FluidTags.WATER) || z < y)){//TODO breathing condition instead of just submerged
                hamonFade = hamonFade + 0.1F;
            }
            if(this.ticks % 4 == 0 && hamonFade > 0.0F && (player.isSubmergedIn(FluidTags.WATER) || z < y)){
                hamonFade = hamonFade - 0.1F;
            }
            renderHamon(matrices, player, scaledWidth / 2 - 91, scaledHeight - 39);
        }
    }





    private void renderHamon(MatrixStack matrices, LivingEntity entity, int x, int y) {
        if (entity instanceof HamonUser hamonUser && hamonUser.getHamonLevel() != Hamon.EMPTY && !entity.isSubmergedInWater()) {
            matrices.push();
            RenderSystem.setShaderTexture(0, EYES_OF_ENDER_GUI_ICONS_TEXTURE);
            RenderSystem.setShaderColor(1f, 1f, 1f, hamonFade - 0.2F);
            for (int i = 0; i < hamonUser.getHamonBreath(); i++) {
                ShaderProgram shader = EOEShaders.DISTORTED_TEXTURE.getInstance().get();
                shader.getUniformOrDefault("FreqX").setFloat(15f);
                shader.getUniformOrDefault("FreqY").setFloat(15f);
                shader.getUniformOrDefault("Speed").setFloat(1500f);
                shader.getUniformOrDefault("Amplitude").setFloat(75f);
                RenderUtils.blit(matrices, EOEShaders.DISTORTED_TEXTURE, (x - i * 8) + 9 * 19 + 2, y - 10, 9, 9, 1, 1, 1, 1, 0, 0, 9);
            }
            RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            matrices.pop();
        }
    }
}
