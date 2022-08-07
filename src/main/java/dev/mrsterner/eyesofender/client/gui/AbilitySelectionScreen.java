package dev.mrsterner.eyesofender.client.gui;

import com.mojang.blaze3d.platform.InputUtil;
import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.interfaces.AbilityUser;
import dev.mrsterner.eyesofender.client.gui.widget.SelectAbilityWidget;
import dev.mrsterner.eyesofender.client.registry.EOEKeyBindings;
import dev.mrsterner.eyesofender.common.ability.Ability;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AbilitySelectionScreen extends Screen {
	private static final int totalRadius = 32;
	public int openTicks;

	public AbilitySelectionScreen() {
		super(Text.translatable(EyesOfEnder.MODID + ".gui.ability_select"));
	}

	@Override
	protected void init() {
		super.init();
		AbilityUser.of(client.player).ifPresent(user -> {
			List<Ability> abilities = user.getAbilities().stream().filter(Objects::nonNull).collect(Collectors.toList());
			double angleSize = (Math.PI * 2) / abilities.size();
			int centerX = width / 2 - totalRadius / 2 + 2;
			int centerY = height / 2 - totalRadius / 2 + 2;
			for (int i = 0; i < abilities.size(); i++) {
				double angle = angleSize * i + Math.PI;
				double x = centerX + (Math.sin(angle) * totalRadius);
				double y = centerY + (Math.cos(angle) * totalRadius);
				addDrawableChild(new SelectAbilityWidget((int) Math.round(x), (int) Math.round(y), this, abilities.get(i)));
			}
		});
	}


	@Override
	public void tick() {
		super.tick();
		if (isAbilitySelectionKeyPressed()) {
			if (openTicks < 5) {
				openTicks++;
			}
		} else {
			closeScreen();
		}
	}

	private boolean isAbilitySelectionKeyPressed() {
		return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), KeyBindingHelper.getBoundKeyOf(EOEKeyBindings.abilitySelectionKey).getKeyCode());
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}


}
