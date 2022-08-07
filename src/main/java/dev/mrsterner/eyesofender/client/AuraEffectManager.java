package dev.mrsterner.eyesofender.client;

import com.mojang.blaze3d.framebuffer.Framebuffer;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.client.registry.EOERenderLayers;
import dev.mrsterner.eyesofender.mixin.access.FramebufferAccessor;
import ladysnake.satin.api.event.EntitiesPreRenderCallback;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;


import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;

/**
 * Manages the Aura rendering effect. Auras are rendered to a separate framebuffer with a custom renderlayer and then
 * composited into the main framebuffer with a post shader. This uses similar techniques to
 * <a href="https://github.com/CammiePone/Devotion/blob/2b1949546ee197fe83d474da629c9af4b50bfe0b/src/main/java/dev/cammiescorner/devotion/client/AuraEffectManager.java"></a>
 */
public final class AuraEffectManager implements EntitiesPreRenderCallback, ShaderEffectRenderCallback {
	public static final AuraEffectManager INSTANCE = new AuraEffectManager();
	private final MinecraftClient client = MinecraftClient.getInstance();
	public final ManagedCoreShader auraCoreShader = ShaderEffectManager.getInstance().manageCoreShader(EyesOfEnder.id("eoe__hamon"));
	private final ManagedShaderEffect auraPostShader = ShaderEffectManager.getInstance().manage(EyesOfEnder.id("shaders/post/aura.json"), this::assignDepthTexture);//TODO make new shader which is not from devotion
	private final ManagedFramebuffer auraFramebuffer = auraPostShader.getTarget("auras");
	private boolean auraBufferCleared;


	@Override
	public void beforeEntitiesRender(@NotNull Camera camera, @NotNull Frustum frustum, float tickDelta) {
		auraBufferCleared = false;
	}

	@Override
	public void renderShaderEffects(float tickDelta) {
		if(this.auraBufferCleared) {
			auraPostShader.setUniformValue("TransStepGranularity", 3F);
			auraPostShader.setUniformValue("BlobsStepGranularity", 6F);
			auraPostShader.render(tickDelta);
			client.getFramebuffer().beginWrite(true);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GlStateManager.class_4535.SRC_ALPHA, GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA);
			auraFramebuffer.draw();
			RenderSystem.disableBlend();
		}
	}
	public void beginAuraFramebufferUse() {
		Framebuffer auraFramebuffer = this.auraFramebuffer.getFramebuffer();

		if(auraFramebuffer != null) {
			auraFramebuffer.beginWrite(false);

			if(!auraBufferCleared) {
				float[] clearColor = ((FramebufferAccessor) auraFramebuffer).getClearColor();
				RenderSystem.clearColor(clearColor[0], clearColor[1], clearColor[2], clearColor[3]);
				RenderSystem.clear(GL_COLOR_BUFFER_BIT, MinecraftClient.IS_SYSTEM_MAC);

				auraBufferCleared = true;
			}
		}
	}

	public void endAuraFramebufferUse() {
		this.client.getFramebuffer().beginWrite(false);
	}

	private void assignDepthTexture(ManagedShaderEffect managedShaderEffect) {
		client.getFramebuffer().beginWrite(false);
		int depthTexturePtr = client.getFramebuffer().getDepthAttachment();

		if(depthTexturePtr > -1) {
			auraFramebuffer.beginWrite(false);
			GlStateManager._glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthTexturePtr, 0);
		}
	}


	public static RenderLayer getRenderLayer(Identifier texture) {
		return texture == null ? getRenderLayer() : EOERenderLayers.HAMON_LAYER.apply(texture);
	}


	public static RenderLayer getRenderLayer(@NotNull RenderLayer base) {
		return AuraRenderLayers.getRenderLayerWithTextureFrom(base);
	}


	public static RenderLayer getRenderLayer() {
		return AuraRenderLayers.DEFAULT_AURA_LAYER;
	}

	private static final class AuraRenderLayers extends RenderLayer {
		private static final Identifier WHITE_TEXTURE = new Identifier("misc/white.png");
		private static final RenderLayer DEFAULT_AURA_LAYER = EOERenderLayers.HAMON_LAYER.apply(WHITE_TEXTURE);

		private AuraRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
			super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
		}

		private static RenderLayer getRenderLayerWithTextureFrom(RenderLayer base) {
			if(base instanceof RenderLayer.MultiPhase multiPhase)
				return EOERenderLayers.HAMON_LAYER.apply(multiPhase.getPhases().texture.getId().orElse(WHITE_TEXTURE));
			else
				return DEFAULT_AURA_LAYER;
		}
	}
}
