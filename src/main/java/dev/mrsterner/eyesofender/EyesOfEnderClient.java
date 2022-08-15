package dev.mrsterner.eyesofender;

import com.williambl.early_features.api.LivingEntityEarlyFeatureRendererRegistrationCallback;
import dev.mrsterner.eyesofender.api.interfaces.HamonUser;
import dev.mrsterner.eyesofender.client.EOESpriteIdentifiers;
import dev.mrsterner.eyesofender.client.gui.HamonAbilityClientHandler;
import dev.mrsterner.eyesofender.client.renderer.block.CoffinBlockEntityRenderer;
import dev.mrsterner.eyesofender.client.registry.EOEParticleTypes;
import dev.mrsterner.eyesofender.client.registry.EOESoundEvents;
import dev.mrsterner.eyesofender.client.renderer.feature.HamonFeatureRenderer;
import dev.mrsterner.eyesofender.client.renderer.StoneMaskArmorRenderer;
import dev.mrsterner.eyesofender.client.renderer.StoneMaskItemRenderer;
import dev.mrsterner.eyesofender.common.networking.packet.HamonAbilityPacket;
import dev.mrsterner.eyesofender.common.networking.packet.SyncHamonUserDataPacket;
import dev.mrsterner.eyesofender.common.registry.EOEBlockEntityTypes;
import dev.mrsterner.eyesofender.common.registry.EOEObjects;
import dev.mrsterner.eyesofender.common.utils.NbtUtils;
import ladysnake.satin.api.event.PostWorldRenderCallback;
import ladysnake.satin.api.experimental.ReadableDepthFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.util.GlMatrices;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
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

public class EyesOfEnderClient implements ClientModInitializer {
	private int ticks = 0;
	private float prevRadius = 0f;
	private float radius = 0f;
	private boolean renderingEffect = false;
	public @Nullable PlayerEntity player = null;
	public long effectLength = 0;
	private Matrix4f projectionMatrix = new Matrix4f();
	public boolean shouldRender = false;
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
		ClientPlayNetworking.registerGlobalReceiver(SyncHamonUserDataPacket.ID, (client, networkHandler, packetByteBuf, sender) -> {
			NbtCompound tag = packetByteBuf.readNbt();
			client.execute(() -> HamonUser.of(client.player).ifPresent(user -> NbtUtils.readAbilityData(user, tag)));
		});

		ClientTickEvents.END.register(ClientTickHandler::clientTickEnd);
		PostWorldRenderCallback.EVENT.register(this::zaWarudo);
		ClientTickEvents.START.register(this::zaWarduo);

		BlockEntityRendererRegistry.register(EOEBlockEntityTypes.COFFIN_BLOCK_ENTITY, CoffinBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(CoffinBlockEntityRenderer.COFFIN_LAYER, CoffinBlockEntityRenderer::getTexturedModelData);
		EOESpriteIdentifiers.INSTANCE.addIdentifier(EOESpriteIdentifiers.COFFIN_SPRITE);

		GeoArmorRenderer.registerArmorRenderer(new StoneMaskArmorRenderer(), EOEObjects.STONE_MASK);
		GeoItemRenderer.registerItemRenderer(EOEObjects.STONE_MASK, new StoneMaskItemRenderer());

		LivingEntityEarlyFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, context) -> {
			if(entityRenderer instanceof PlayerEntityRenderer playerRenderer)
				entityRenderer.addEarlyFeature(new HamonFeatureRenderer<>(playerRenderer));
		});
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
			if (player != null) {
				shader.setUniformValue(
						"Center",
						lerp(player.getX(), player.prevX, tickDelta),
						lerp(player.getY()+0.5, player.prevY+0.5, tickDelta),
						lerp(player.getZ(), player.prevZ, tickDelta)
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
