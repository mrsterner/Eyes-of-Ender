package dev.mrsterner.eyesofender.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.client.gui.AbilitySelectionScreen;
import dev.mrsterner.eyesofender.client.registry.EOEKeyBindings;
import dev.mrsterner.eyesofender.common.ability.Ability;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.ChatNarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SelectAbilityWidget extends ClickableWidget {
	public Ability ability;
	public AbilitySelectionScreen screen;
	private int hoverTicks = 0;

	public SelectAbilityWidget(int x, int y, AbilitySelectionScreen screen, Ability ability) {
		super(x, y, 28, 28, ChatNarratorManager.NO_TITLE);
		this.ability = ability;
		this.screen = screen;
		this.active = true;
	}

	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		RenderSystem.setShaderTexture(0, getTexture(ability.hamonLevel));
		this.alpha = (screen.openTicks + MinecraftClient.getInstance().getTickDelta()) / 5F;
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, Math.min(isHoveredOrFocused() || isSelected() ? 1 : 0.5F, this.alpha));
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		drawTexture(matrices, this.x + 1, this.y + 1, 0, 0, this.width - 2, this.height - 2, 26, 26);
		RenderSystem.setShaderTexture(0, ability.hamonAbility.getTextureLocation());
		drawTexture(matrices, this.x + 5, this.y + 5, 0, 0, 18, 18, 18, 18);
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
			EOEKeyBindings.selectedAbility = ability;
		} else {
			EOEKeyBindings.selectedAbility = null;
		}
	}

	private boolean isSelected() {
		return EOEKeyBindings.selectedAbility == ability;
	}

	@Override
	public void appendNarrations(NarrationMessageBuilder builder) {
		super.appendDefaultNarrations(builder);
	}
}
