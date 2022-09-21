package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class ImbueAbility extends HamonKnowledge {
	public ImbueAbility() {
		super(EyesOfEnder.id("imbue"), Hamon.BASIC, null, 0xF5CE8C, null, 50, false);
	}
}
