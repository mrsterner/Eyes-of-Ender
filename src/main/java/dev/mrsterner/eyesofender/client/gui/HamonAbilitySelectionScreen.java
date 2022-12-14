package dev.mrsterner.eyesofender.client.gui;

import com.mojang.blaze3d.platform.InputUtil;
import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.client.gui.widget.SelectHamonAbilityWidget;
import dev.mrsterner.eyesofender.common.ability.HamonAbility;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

public class HamonAbilitySelectionScreen extends Screen {
	private static int totalRadius = 32;
	public int openTicks;

	public HamonAbilitySelectionScreen() {
		super(Text.translatable(EyesOfEnder.MODID + ".gui.ability_select"));
	}

	@Override
	protected void init() {
		super.init();
		if (client != null) {
			EOEComponents.HAMON_COMPONENT.maybeGet(client.player).ifPresent(user -> {
				List<HamonAbility> abilities = user.getHamonAbilities().stream().filter(Objects::nonNull).toList();
				double angleSize = (Math.PI * 2) / abilities.size();
				totalRadius = totalRadius + (abilities.size() * 2);
				int centerX = width / 2 - totalRadius / 2 + 2;
				int centerY = height / 2 - totalRadius / 2 + 2;
				for (int i = 0; i < abilities.size(); i++) {
					double angle = angleSize * i + Math.PI;
					double x = centerX + (Math.sin(angle) * totalRadius);
					double y = centerY + (Math.cos(angle) * totalRadius);
					addDrawableChild(new SelectHamonAbilityWidget((int) Math.round(x), (int) Math.round(y), this, abilities.get(i)));
				}
			});
		}
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
		return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), KeyBindingHelper.getBoundKeyOf(HamonAbilityClientHandler.abilitySelectionKey).getKeyCode());
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}


}
