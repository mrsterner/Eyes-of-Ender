package dev.mrsterner.eyesofender.client.gui;

import com.mojang.blaze3d.platform.InputUtil;
import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.interfaces.AbilityUser;
import dev.mrsterner.eyesofender.client.gui.hud.HamonAbilityHUD;
import dev.mrsterner.eyesofender.common.ability.Ability;
import dev.mrsterner.eyesofender.common.networking.packet.AbilityPacket;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import org.lwjgl.glfw.GLFW;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import net.minecraft.client.option.KeyBind;
import org.quiltmc.qsl.networking.api.client.ClientLoginConnectionEvents;

public class AbilityClientHandler {
	public static KeyBind abilitySelectionKey;
	public static KeyBind useKey;
	public static Ability selectedAbility;
	public static HamonAbilityHUD currentAbilityHUD;


	public static void init() {
		currentAbilityHUD = new HamonAbilityHUD();
		abilitySelectionKey = KeyBindingHelper.registerKeyBinding(new KeyBind(
				"key." + EyesOfEnder.MODID + ".select_ability",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_R,
				"category." + EyesOfEnder.MODID
		));

		useKey = KeyBindingHelper.registerKeyBinding(new KeyBind(
				"key." + EyesOfEnder.MODID + ".use",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_F,
				"category." + EyesOfEnder.MODID
		));

		ClientLoginConnectionEvents.INIT.register((handler, client) -> AbilityClientHandler.selectedAbility = null);
		ClientTickEvents.START.register(AbilityClientHandler::selectAbility);
		HudRenderCallback.EVENT.register(AbilityClientHandler::renderHud);
	}

	private static void renderHud(MatrixStack matrixStack, float tickDelta) {
		MinecraftClient client = MinecraftClient.getInstance();
		currentAbilityHUD.render(client, matrixStack, tickDelta, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight(), client.player);
	}

	private static void selectAbility(MinecraftClient minecraftClient) {
		if (abilitySelectionKey.isPressed()){
			System.out.println(AbilityUser.of(minecraftClient.player).map(abilityUser -> !abilityUser.getAbilities().isEmpty()));
		}
		if (abilitySelectionKey.isPressed() && minecraftClient.currentScreen == null && AbilityUser.of(minecraftClient.player).map(abilityUser -> !abilityUser.getAbilities().isEmpty()).orElse(false)) {
			System.out.println("ScreenOpwen");
			minecraftClient.setScreen(new AbilitySelectionScreen());
		} else if (minecraftClient.player != null && selectedAbility != null) {
			if (useKey.wasPressed() && AbilityUser.of(minecraftClient.player).map(AbilityUser::getAbilityCooldown).orElse(0) <= 0) {
				AbilityPacket.sendFromClientPlayer(minecraftClient.player, selectedAbility.toTag(new NbtCompound()));
			} else if (minecraftClient.player.isDead()) {
				selectedAbility = null;
			}
		}
	}
}
