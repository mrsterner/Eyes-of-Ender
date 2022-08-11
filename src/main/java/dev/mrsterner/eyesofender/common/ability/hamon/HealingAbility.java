package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class HealingAbility extends HamonKnowledge {
	public HealingAbility() {
		super(EyesOfEnder.id("healing"), null, 0xF5CE8C);
	}
}
