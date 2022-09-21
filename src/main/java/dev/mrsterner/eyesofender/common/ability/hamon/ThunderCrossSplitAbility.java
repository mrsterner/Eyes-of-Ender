package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class ThunderCrossSplitAbility extends HamonKnowledge {

	public ThunderCrossSplitAbility() {
		super(EyesOfEnder.id("thunder_cross_split"), Hamon.ADVANCED, null, 0, null, 30, false);
	}
}
