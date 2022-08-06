package dev.mrsterner.eyesofender;

import dev.mrsterner.eyesofender.client.registry.EOEParticleTypes;
import dev.mrsterner.eyesofender.client.registry.EOESounds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class EyesOfEnderClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {
		EOEParticleTypes.init();
		EOESounds.init();
		ClientTickEvents.END_CLIENT_TICK.register(ClientTickHandler::clientTickEnd);
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
