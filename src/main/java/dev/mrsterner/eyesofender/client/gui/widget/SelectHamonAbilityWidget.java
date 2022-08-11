package dev.mrsterner.eyesofender.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.client.gui.HamonAbilitySelectionScreen;
import dev.mrsterner.eyesofender.client.gui.HamonAbilityClientHandler;
import dev.mrsterner.eyesofender.client.registry.EOEShaders;
import dev.mrsterner.eyesofender.common.ability.HamonAbility;
import dev.mrsterner.eyesofender.common.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.client.util.ChatNarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SelectHamonAbilityWidget extends ClickableWidget {
	public HamonAbility hamonAbility;
	public HamonAbilitySelectionScreen screen;
	private int hoverTicks = 0;

	public SelectHamonAbilityWidget(int x, int y, HamonAbilitySelectionScreen screen, HamonAbility hamonAbility) {
		super(x, y, 28, 28, ChatNarratorManager.NO_TITLE);
		this.hamonAbility = hamonAbility;
		this.screen = screen;
		this.active = true;
	}

	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		RenderSystem.setShaderTexture(0, getTexture(hamonAbility.hamonLevel));
		this.alpha = (screen.openTicks + MinecraftClient.getInstance().getTickDelta()) / 5F;
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, Math.min(isHoveredOrFocused() || isSelected() ? 1 : 0.5F, this.alpha));
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		drawTexture(matrices, this.x + 1, this.y + 1, 0, 0, this.width - 2, this.height - 2, 26, 26);
		RenderSystem.setShaderTexture(0, hamonAbility.hamonKnowledge.getTextureLocation());
		ShaderProgram shader = EOEShaders.DISTORTED_TEXTURE.getInstance().get();
		shader.getUniformOrDefault("FreqX").setFloat(15f);
		shader.getUniformOrDefault("FreqY").setFloat(15f);
		shader.getUniformOrDefault("Speed").setFloat(1500f);
		shader.getUniformOrDefault("Amplitude").setFloat(75f);
		RenderUtils.blit(matrices, EOEShaders.DISTORTED_TEXTURE, x + 5, y + 5, 18, 18, 1, 1, 1, 1, 0, 0, 18f);
		if (isHoveredOrFocused() && hoverTicks < 20) {
			hoverTicks++;
		} else if (hoverTicks > 0) {
			hoverTicks--;
		}
		this.renderBackground(matrices, minecraftClient, mouseX, mouseY);
	}

	public static Identifier getTexture(int level) {
		return EyesOfEnder.id(String.format("textures/gui/ability_widgets/level/level_%d.png", level));
	}

	@Override
	public void onClick(double mouseX, double mouseY) {
		if (!isSelected()) {
			HamonAbilityClientHandler.selectedHamonAbility = hamonAbility;
		} else {
			HamonAbilityClientHandler.selectedHamonAbility = null;
		}
	}

	private boolean isSelected() {
		return HamonAbilityClientHandler.selectedHamonAbility == hamonAbility;
	}

	@Override
	public void appendNarrations(NarrationMessageBuilder builder) {
		super.appendDefaultNarrations(builder);
	}
}
