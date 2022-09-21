package dev.mrsterner.eyesofender;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.client.EOESpriteIdentifiers;
import dev.mrsterner.eyesofender.client.gui.HamonAbilityClientHandler;
import dev.mrsterner.eyesofender.client.registry.EOEShaders;
import dev.mrsterner.eyesofender.client.renderer.block.CoffinBlockEntityRenderer;
import dev.mrsterner.eyesofender.client.registry.EOEParticleTypes;
import dev.mrsterner.eyesofender.client.registry.EOESoundEvents;
import dev.mrsterner.eyesofender.client.renderer.StoneMaskArmorRenderer;
import dev.mrsterner.eyesofender.client.renderer.StoneMaskItemRenderer;
import dev.mrsterner.eyesofender.client.renderer.entity.HierophantGreenEntityRenderer;
import dev.mrsterner.eyesofender.client.shader.ZaWarudoShader;
import dev.mrsterner.eyesofender.common.networking.packet.HamonAbilityPacket;
import dev.mrsterner.eyesofender.common.registry.EOEBlockEntityTypes;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import dev.mrsterner.eyesofender.common.registry.EOEEntityTypes;
import dev.mrsterner.eyesofender.common.registry.EOEObjects;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import dev.mrsterner.eyesofender.common.utils.RenderUtils;
import ladysnake.satin.api.event.PostWorldRenderCallback;
import ladysnake.satin.api.experimental.ReadableDepthFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.util.GlMatrices;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
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
	ZaWarudoShader zaWarudoShader = new ZaWarudoShader();
	private float hamonFade = 1.0F;
	private int ticks = 0;
	private static final Identifier EYES_OF_ENDER_GUI_ICONS_TEXTURE = EyesOfEnder.id("textures/gui/hamonbar.png");

	@Override
	public void onInitializeClient(ModContainer mod) {
		EOEParticleTypes.init();
		EOESoundEvents.init();
		HamonAbilityClientHandler.init();

		ClientPlayNetworking.registerGlobalReceiver(HamonAbilityPacket.ID, HamonAbilityPacket::handle);

		ClientTickEvents.END.register(ClientTickHandler::clientTickEnd);
		HudRenderCallback.EVENT.register(this::renderHamonHud);

		BlockEntityRendererRegistry.register(EOEBlockEntityTypes.COFFIN_BLOCK_ENTITY, CoffinBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(CoffinBlockEntityRenderer.COFFIN_LAYER, CoffinBlockEntityRenderer::getTexturedModelData);
		EOESpriteIdentifiers.INSTANCE.addIdentifier(EOESpriteIdentifiers.COFFIN_SPRITE);

		GeoArmorRenderer.registerArmorRenderer(new StoneMaskArmorRenderer(), EOEObjects.STONE_MASK);
		GeoItemRenderer.registerItemRenderer(EOEObjects.STONE_MASK, new StoneMaskItemRenderer());

		EntityRendererRegistry.register(EOEEntityTypes.HIEROPHANT_GREEN, HierophantGreenEntityRenderer::new);

		zaWarudoShader.registerCallbacks();
	}

	/**
	 * If the player is a hamon user its hamon breath should render over the hotbar
	 * @param matrixStack
	 * @param tickDelta
	 */
	private void renderHamonHud(MatrixStack matrixStack, float tickDelta) {
		MinecraftClient mc = MinecraftClient.getInstance();
		PlayerEntity player = mc.player;
		int scaledHeight = mc.getWindow().getScaledHeight();
		int scaledWidth = mc.getWindow().getScaledWidth();

		EOEComponents.HAMON_COMPONENT.maybeGet(player).filter(hamonUser -> hamonUser.getHamonLevel() != Hamon.EMPTY).ifPresent(hamonUser -> {
			if(player != null && !player.isSpectator()){
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


	/**
	 * Use a shader to render a texture, for the hamon breath bar
	 * @param matrices
	 * @param hamonBreath
	 * @param x
	 * @param y
	 */
	private void renderHamon(MatrixStack matrices, float hamonBreath, int x, int y) {
		ShaderProgram shader = EOEShaders.DISTORTED_TEXTURE.getInstance().get();
		shader.getUniformOrDefault("FreqX").setFloat(1f);
		shader.getUniformOrDefault("FreqY").setFloat(15f);
		shader.getUniformOrDefault("Speed").setFloat(1500f);
		shader.getUniformOrDefault("Amplitude").setFloat(50f);
		RenderUtils.blit(matrices, EOEShaders.DISTORTED_TEXTURE, x + 100, y - 10, 8 * hamonBreath/(200f), 9, 1, 1, 1, 1, 0, 0, 80, 9);


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
