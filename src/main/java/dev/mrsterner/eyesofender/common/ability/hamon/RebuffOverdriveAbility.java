package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class RebuffOverdriveAbility extends HamonKnowledge {

	public RebuffOverdriveAbility() {
		super(EyesOfEnder.id("rebuff_overdrive"), Hamon.ADVANCED,  null, 0, null, 20, false);
	}
}
