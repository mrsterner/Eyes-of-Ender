package dev.mrsterner.eyesofender.client.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mrsterner.eyesofender.client.gui.widget.SelectHamonAbilityWidget;
import dev.mrsterner.eyesofender.client.gui.HamonAbilityClientHandler;
import dev.mrsterner.eyesofender.client.registry.EOEShaders;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import dev.mrsterner.eyesofender.common.utils.RenderUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

@Environment(EnvType.CLIENT)
public class HamonAbilityHUD extends DrawableHelper {
	private int shiftTicks;
	private float alpha;

	public void render(MinecraftClient client, MatrixStack matrixStack, float tickDelta, int scaledWidth, int scaledHeight, PlayerEntity player) {
		if (HamonAbilityClientHandler.selectedHamonAbility != null) {
			EOEComponents.HAMON_COMPONENT.maybeGet(player).ifPresent(user -> {
				matrixStack.push();
				matrixStack.translate(scaledWidth - 32, scaledHeight - 32, 0);
				RenderSystem.setShaderTexture(0, SelectHamonAbilityWidget.getTexture());
				this.alpha = 1 - shiftTicks / 20F;
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				RenderSystem.enableDepthTest();
				drawTexture(matrixStack, 0, 0, 0, 0, 26, 26, 26, 26);
				RenderSystem.setShaderTexture(0, HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.getTextureLocation());
				ShaderProgram shader = EOEShaders.DISTORTED_TEXTURE.getInstance().get();
				shader.getUniformOrDefault("FreqX").setFloat(15f);
				shader.getUniformOrDefault("FreqY").setFloat(15f);
				shader.getUniformOrDefault("Speed").setFloat(1500f);
				shader.getUniformOrDefault("Amplitude").setFloat(75f);
				RenderUtils.blit(matrixStack, EOEShaders.DISTORTED_TEXTURE, 4, 4, 18, 18, 1, 1, 1, 1, 0, 0, 18f, 18f);
				if (Screen.hasShiftDown()) {
					if (shiftTicks < 10) {
						this.shiftTicks++;
					}
				} else if (this.shiftTicks > 0) {
					this.shiftTicks--;
				}
				matrixStack.pop();
			});
		}
	}
}
