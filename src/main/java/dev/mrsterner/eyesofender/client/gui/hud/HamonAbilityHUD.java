package dev.mrsterner.eyesofender.client.gui.hud;

import dev.mrsterner.eyesofender.client.registry.EOEKeyBindings;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

@Environment(EnvType.CLIENT)
public class HamonAbilityHUD extends DrawableHelper {
	private int shiftTicks;
	private float alpha;

	public void render(MinecraftClient client, MatrixStack matrixStack, float tickDelta, int scaledWidth, int scaledHeight, PlayerEntity player) {
		if (EOEKeyBindings.selectedAbility != null) {
			if(EOEComponents.HAMON_COMPONENT.maybeGet(player).isPresent()){

			}
		}
	}
}
