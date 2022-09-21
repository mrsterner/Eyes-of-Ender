package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class TornadoOverdiveAbility extends HamonKnowledge {

	public TornadoOverdiveAbility() {
		super(EyesOfEnder.id("tornado_overdrive"), Hamon.ADVANCED, null, 0xF5CE8C, null, 30, false);
	}
}
