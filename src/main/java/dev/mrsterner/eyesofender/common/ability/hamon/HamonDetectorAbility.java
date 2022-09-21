package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class HamonDetectorAbility extends HamonKnowledge {

	public HamonDetectorAbility() {
		super(EyesOfEnder.id("hamon_detector"), Hamon.MASTER, null, 0, null, 20, false);
	}
}
