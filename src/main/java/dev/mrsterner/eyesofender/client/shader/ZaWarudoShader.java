package dev.mrsterner.eyesofender.client.shader;

import dev.mrsterner.eyesofender.EyesOfEnder;
import ladysnake.satin.api.event.PostWorldRenderCallback;
import ladysnake.satin.api.experimental.ReadableDepthFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.util.GlMatrices;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;

@Environment(EnvType.CLIENT)
public class ZaWarudoShader implements PostWorldRenderCallback, ClientTickEvents.Start {
    private int ticks = 0;
    private float prevRadius = 0f;
    private float radius = 0f;
    private boolean renderingEffect = false;
    public @Nullable PlayerEntity shaderPlayer = null;
    public long effectLength = 0;
    private Matrix4f projectionMatrix = new Matrix4f();
    public boolean shouldRender = false;

    private ManagedShaderEffect shader = ShaderEffectManager.getInstance().manage(EyesOfEnder.id("shaders/post/za_warudo.json"), shader -> {
        MinecraftClient mc = MinecraftClient.getInstance();
        shader.setSamplerUniform("DepthSampler", ((ReadableDepthFramebuffer) mc.getFramebuffer()).getStillDepthMap());
        shader.setUniformValue("ViewPort", 0, 0, mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight());
    });

    public void registerCallbacks() {
        PostWorldRenderCallback.EVENT.register(this);
        ClientTickEvents.START.register(this);
    }

    private float lerp(double n, double prevN, float tickDelta) {
        return (float) MathHelper.lerp(tickDelta, prevN, n);
    }

    private boolean hasFinishedAnimation() {
        return ticks > effectLength;
    }

    @Override
    public void onWorldRendered(Camera camera, float tickDelta, long nanoTime) {
        if (renderingEffect) {
            shader.setUniformValue("STime", (ticks + tickDelta) / 20f);
            shader.setUniformValue("InverseTransformMatrix", GlMatrices.getInverseTransformMatrix(projectionMatrix));
            Vec3d cameraPos = camera.getPos();
            shader.setUniformValue(
                    "CameraPosition", (float) cameraPos.x, (float) cameraPos.y,
                    (float) cameraPos.z
            );
            if (shaderPlayer != null) {
                shader.setUniformValue(
                        "Center",
                        lerp(shaderPlayer.getX(), shaderPlayer.prevX, tickDelta),
                        lerp(shaderPlayer.getY()+0.5, shaderPlayer.prevY+0.5, tickDelta),
                        lerp(shaderPlayer.getZ(), shaderPlayer.prevZ, tickDelta)
                );
            }
            shader.setUniformValue("Radius", Math.max(0f, lerp(radius, prevRadius, tickDelta)));
            shader.render(tickDelta);
        }
    }

    @Override
    public void startClientTick(MinecraftClient client) {
        if (shouldRender) {
            if (!renderingEffect) {
                shader.setUniformValue("OuterSat", 1f);
                ticks = 0;
                radius = 0f;
                prevRadius = radius;
                renderingEffect = true;
            }
            ticks++;
            prevRadius = radius;
            float expansionRate = 4f;
            int inversion = 100 / (int) expansionRate;
            if (ticks < inversion) {
                radius += expansionRate;
            }
            else if (ticks == inversion) {
                shader.setUniformValue("OuterSat", 0.3f);
            }
            else if (ticks < 2 * inversion) {
                radius -= 2 * expansionRate;
            }
            if (hasFinishedAnimation()) {
                renderingEffect = false;
                shouldRender = false;
            }
        } else {
            renderingEffect = false;
        }
    }
}
