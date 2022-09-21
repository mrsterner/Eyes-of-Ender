package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class ForcedHamonAbility extends HamonKnowledge {

	public ForcedHamonAbility() {
		super(
				EyesOfEnder.id("forced_hamon"),
				Hamon.INTERMEDIATE,
				null,
				0,
				null,
				20 * 40,
				HamonAbilityType.ACTIVE);
	}
}
