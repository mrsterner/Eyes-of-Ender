package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class HamonCutterAbility extends HamonKnowledge {

	public HamonCutterAbility() {
		super(EyesOfEnder.id("hamon_cutter"), Hamon.MASTER, null, 0xF5CE8C, null, 20, false);
	}
}
