package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class ForcedHamonAbility extends HamonKnowledge {

	public ForcedHamonAbility() {
		super(EyesOfEnder.id("forced_hamon"), Hamon.INTERMEDIATE, null, 0xF5CE8C, null, 10, false);
	}
}
