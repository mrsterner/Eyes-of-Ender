package dev.mrsterner.eyesofender;

import com.williambl.early_features.api.LivingEntityEarlyFeatureRendererRegistrationCallback;
import dev.mrsterner.eyesofender.api.interfaces.AbilityUser;
import dev.mrsterner.eyesofender.client.gui.AbilityClientHandler;
import dev.mrsterner.eyesofender.client.registry.EOEParticleTypes;
import dev.mrsterner.eyesofender.client.registry.EOESounds;
import dev.mrsterner.eyesofender.client.renderer.feature.HamonFeatureRenderer;
import dev.mrsterner.eyesofender.client.AuraEffectManager;
import dev.mrsterner.eyesofender.client.renderer.StoneMaskArmorRenderer;
import dev.mrsterner.eyesofender.client.renderer.StoneMaskItemRenderer;
import dev.mrsterner.eyesofender.common.networking.packet.AbilityPacket;
import dev.mrsterner.eyesofender.common.networking.packet.SyncAbilityUserDataPacket;
import dev.mrsterner.eyesofender.common.registry.EOEObjects;
import dev.mrsterner.eyesofender.common.utils.NbtUtils;
import ladysnake.satin.api.event.EntitiesPreRenderCallback;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.nbt.NbtCompound;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class EyesOfEnderClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {
		EOEParticleTypes.init();
		EOESounds.init();
		AbilityClientHandler.init();

		ClientPlayNetworking.registerGlobalReceiver(AbilityPacket.ID, AbilityPacket::handle);
		ClientPlayNetworking.registerGlobalReceiver(SyncAbilityUserDataPacket.ID, (client, networkHandler, packetByteBuf, sender) -> {
			NbtCompound tag = packetByteBuf.readNbt();
			client.execute(() -> AbilityUser.of(client.player).ifPresent(user -> NbtUtils.readAbilityData(user, tag)));
		});

		EntitiesPreRenderCallback.EVENT.register(AuraEffectManager.INSTANCE);
		ShaderEffectRenderCallback.EVENT.register(AuraEffectManager.INSTANCE);
		ClientTickEvents.END.register(ClientTickHandler::clientTickEnd);

		GeoArmorRenderer.registerArmorRenderer(new StoneMaskArmorRenderer(), EOEObjects.STONE_MASK);
		GeoItemRenderer.registerItemRenderer(EOEObjects.STONE_MASK, new StoneMaskItemRenderer());

		LivingEntityEarlyFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, context) -> {
			if(entityRenderer instanceof PlayerEntityRenderer playerRenderer)
				entityRenderer.addEarlyFeature(new HamonFeatureRenderer<>(playerRenderer, new HeldItemFeatureRenderer<>(playerRenderer, context.getHeldItemRenderer())));
		});
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
