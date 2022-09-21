package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class PrecognitionAbility extends HamonKnowledge {

	public PrecognitionAbility() {
		super(EyesOfEnder.id("precognition"), Hamon.MASTER, null, 0, null, 2, true);
	}
}
