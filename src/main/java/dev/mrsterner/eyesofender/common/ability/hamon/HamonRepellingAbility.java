package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class HamonRepellingAbility extends HamonKnowledge {

	public HamonRepellingAbility() {
		super(EyesOfEnder.id("hamon_repelling"), Hamon.INTERMEDIATE, null, 0, null, 20, false);
	}
}
