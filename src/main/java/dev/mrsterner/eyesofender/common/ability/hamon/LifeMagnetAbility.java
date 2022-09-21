package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class LifeMagnetAbility extends HamonKnowledge {

	public LifeMagnetAbility() {
		super(
				EyesOfEnder.id("life_magnet"),
				Hamon.ADVANCED,
				null,
				0xF5CE8C,
				null,
				20 * 40,
				HamonAbilityType.ACTIVE);
	}
}
