package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.registry.AbilityEffect;

public class HealingAbility extends AbilityEffect {
	public HealingAbility() {
		super(EyesOfEnder.id("healing"), null, 0xF5CE8C);
	}
}
