package dev.mrsterner.eyesofender;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.interfaces.HamonUser;
import dev.mrsterner.eyesofender.client.EOESpriteIdentifiers;
import dev.mrsterner.eyesofender.client.gui.HamonAbilityClientHandler;
import dev.mrsterner.eyesofender.client.registry.EOEShaders;
import dev.mrsterner.eyesofender.client.renderer.block.CoffinBlockEntityRenderer;
import dev.mrsterner.eyesofender.client.registry.EOEParticleTypes;
import dev.mrsterner.eyesofender.client.registry.EOESoundEvents;
import dev.mrsterner.eyesofender.client.renderer.StoneMaskArmorRenderer;
import dev.mrsterner.eyesofender.client.renderer.StoneMaskItemRenderer;
import dev.mrsterner.eyesofender.common.networking.packet.CyborgAbilityPacket;
import dev.mrsterner.eyesofender.common.networking.packet.HamonAbilityPacket;
import dev.mrsterner.eyesofender.common.networking.packet.SyncHamonUserDataPacket;
import dev.mrsterner.eyesofender.common.registry.EOEBlockEntityTypes;
import dev.mrsterner.eyesofender.common.registry.EOEObjects;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import dev.mrsterner.eyesofender.common.utils.NbtUtils;
import dev.mrsterner.eyesofender.common.utils.RenderUtils;
import ladysnake.satin.api.event.PostWorldRenderCallback;
import ladysnake.satin.api.experimental.ReadableDepthFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.util.GlMatrices;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

import static net.minecraft.client.gui.DrawableHelper.GUI_ICONS_TEXTURE;

public class EyesOfEnderClient implements ClientModInitializer {
	private float hamonFade = 1.0F;
	private int ticks = 0;
	private float prevRadius = 0f;
	private float radius = 0f;
	private boolean renderingEffect = false;
	public @Nullable PlayerEntity shaderPlayer = null;
	public long effectLength = 0;
	private Matrix4f projectionMatrix = new Matrix4f();
	public boolean shouldRender = false;
	private static final Identifier EYES_OF_ENDER_GUI_ICONS_TEXTURE = EyesOfEnder.id("textures/gui/hamonbar.png");
	private ManagedShaderEffect shader = ShaderEffectManager.getInstance().manage(EyesOfEnder.id("shaders/post/za_warudo.json"), shader -> {
		MinecraftClient mc = MinecraftClient.getInstance();
		shader.setSamplerUniform("DepthSampler", ((ReadableDepthFramebuffer) mc.getFramebuffer()).getStillDepthMap());
		shader.setUniformValue("ViewPort", 0, 0, mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight());
	});


	@Override
	public void onInitializeClient(ModContainer mod) {
		EOEParticleTypes.init();
		EOESoundEvents.init();
		HamonAbilityClientHandler.init();

		ClientPlayNetworking.registerGlobalReceiver(HamonAbilityPacket.ID, HamonAbilityPacket::handle);
		ClientPlayNetworking.registerGlobalReceiver(CyborgAbilityPacket.ID, CyborgAbilityPacket::handle);
		ClientPlayNetworking.registerGlobalReceiver(SyncHamonUserDataPacket.ID, (client, networkHandler, packetByteBuf, sender) -> {
			NbtCompound tag = packetByteBuf.readNbt();
			client.execute(() -> HamonUser.of(client.player).ifPresent(user -> NbtUtils.readAbilityData(user, tag)));
		});

		ClientTickEvents.END.register(ClientTickHandler::clientTickEnd);
		PostWorldRenderCallback.EVENT.register(this::zaWarudo);
		ClientTickEvents.START.register(this::zaWarduo);
		HudRenderCallback.EVENT.register(this::renderHamonHud);


		BlockEntityRendererRegistry.register(EOEBlockEntityTypes.COFFIN_BLOCK_ENTITY, CoffinBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(CoffinBlockEntityRenderer.COFFIN_LAYER, CoffinBlockEntityRenderer::getTexturedModelData);
		EOESpriteIdentifiers.INSTANCE.addIdentifier(EOESpriteIdentifiers.COFFIN_SPRITE);

		GeoArmorRenderer.registerArmorRenderer(new StoneMaskArmorRenderer(), EOEObjects.STONE_MASK);
		GeoItemRenderer.registerItemRenderer(EOEObjects.STONE_MASK, new StoneMaskItemRenderer());
	}

	private void renderHamonHud(MatrixStack matrixStack, float tickDelta) {
		MinecraftClient mc = MinecraftClient.getInstance();
		PlayerEntity player = mc.player;
		int scaledHeight = mc.getWindow().getScaledHeight();
		int scaledWidth = mc.getWindow().getScaledWidth();

		HamonUser.of(player).filter(hamonUser -> hamonUser.getHamonLevel() != Hamon.EMPTY).ifPresent(hamonUser -> {
			if(player != null){
				if(this.ticks % 4 == 0 && hamonFade < 1.0F && EOEUtils.canHamonBreath(player)){
					hamonFade = hamonFade + 0.1F;
				}
				if(hamonFade > 0.0F && !EOEUtils.canHamonBreath(player)){
					hamonFade = hamonFade - 0.4F;
				}

				matrixStack.push();
				RenderSystem.setShaderTexture(0, EYES_OF_ENDER_GUI_ICONS_TEXTURE);
				RenderSystem.setShaderColor(1f, 1f, 1f, hamonFade - 0.2F);
				int isCreative = player.isCreative() ? 18 : 0;
				renderHamon(matrixStack, hamonUser.getHamonBreath(), scaledWidth / 2 - 91, scaledHeight - 39 + isCreative);
				RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
				RenderSystem.depthMask(true);
				RenderSystem.disableBlend();
				matrixStack.pop();
			}
		});
	}



	private void renderHamon(MatrixStack matrices, int hamonBreath, int x, int y) {
		ShaderProgram shader = EOEShaders.DISTORTED_TEXTURE.getInstance().get();
		shader.getUniformOrDefault("FreqX").setFloat(1f);
		shader.getUniformOrDefault("FreqY").setFloat(15f);
		shader.getUniformOrDefault("Speed").setFloat(1500f);
		shader.getUniformOrDefault("Amplitude").setFloat(50f);
		RenderUtils.blit(matrices, EOEShaders.DISTORTED_TEXTURE, x + 100, y - 10, 8 * hamonBreath, 9, 1, 1, 1, 1, 0, 0, 80, 9);


/*
		for (int i = 0; i < hamonBreath; i++) {
			ShaderProgram shader = EOEShaders.DISTORTED_TEXTURE.getInstance().get();
			shader.getUniformOrDefault("FreqX").setFloat(15f);
			shader.getUniformOrDefault("FreqY").setFloat(15f);
			shader.getUniformOrDefault("Speed").setFloat(1500f);
			shader.getUniformOrDefault("Amplitude").setFloat(75f);
			RenderUtils.blit(matrices, EOEShaders.DISTORTED_TEXTURE, (x - i * 8) + 9 * 19 + 2, y - 10, 9, 9, 1, 1, 1, 1, 0, 0, 9, 9);
		}

 */


	}

	private float lerp(double n, double prevN, float tickDelta) {
		return (float) MathHelper.lerp(tickDelta, prevN, n);
	}

	private boolean hasFinishedAnimation() {
		return ticks > effectLength;
	}

	public void zaWarduo(MinecraftClient minecraftClient) {
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

	public void zaWarudo(Camera camera, float tickDelta, long nanoTime) {
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

	public static final class ClientTickHandler {
		private ClientTickHandler() {
		}

		public static int ticksInGame = 0;
		public static float partialTicks = 0;
		public static float delta = 0;
		public static float total = 0;

		public static void calcDelta() {
			float oldTotal = total;
			total = ticksInGame + partialTicks;
			delta = total - oldTotal;
		}

		public static void renderTick(float renderTickTime) {
			partialTicks = renderTickTime;
		}

		public static void clientTickEnd(MinecraftClient mc) {
			if (!mc.isPaused()) {
				ticksInGame++;
				partialTicks = 0;
			}
			calcDelta();
		}
	}


}
