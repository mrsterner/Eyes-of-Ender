package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class HamonHypnosisAbility extends HamonKnowledge {

	public HamonHypnosisAbility() {
		super(EyesOfEnder.id("hamon_hypnosis"), Hamon.MASTER, null, 0, null, 50, false);
	}
}
