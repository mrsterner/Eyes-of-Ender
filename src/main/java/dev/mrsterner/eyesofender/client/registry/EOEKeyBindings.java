package dev.mrsterner.eyesofender.client.registry;

import com.mojang.blaze3d.platform.InputUtil;
import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.interfaces.AbilityUser;
import dev.mrsterner.eyesofender.client.gui.AbilitySelectionScreen;
import dev.mrsterner.eyesofender.common.ability.Ability;
import dev.mrsterner.eyesofender.common.networking.packet.AbilityPacket;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import org.lwjgl.glfw.GLFW;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import net.minecraft.client.option.KeyBind;

public class EOEKeyBindings {
	public static KeyBind abilitySelectionKey;
	public static KeyBind useKey;
	public static Ability selectedAbility;


	public static void init() {
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


		ClientTickEvents.START.register(EOEKeyBindings::selectAbility);
	}

	private static void selectAbility(MinecraftClient minecraftClient) {
		if (abilitySelectionKey.isPressed() && minecraftClient.currentScreen == null && AbilityUser.of(minecraftClient.player).map(caster -> !caster.getAbilities().isEmpty()).orElse(false)) {
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
