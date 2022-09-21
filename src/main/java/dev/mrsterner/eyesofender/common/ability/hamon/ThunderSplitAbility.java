package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class ThunderSplitAbility extends HamonKnowledge {

	public ThunderSplitAbility() {
		super(EyesOfEnder.id("thunder_split"), Hamon.ADVANCED, null, 0, null, 50, false);
	}
}
