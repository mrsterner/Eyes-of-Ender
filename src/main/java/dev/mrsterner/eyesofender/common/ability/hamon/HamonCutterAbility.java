package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class HamonCutterAbility extends HamonKnowledge {

	public HamonCutterAbility() {
		super(
				EyesOfEnder.id("hamon_cutter"),
				Hamon.MASTER,
				null,
				0,
				null,
				20 * 60,
				HamonAbilityType.ACTIVE,
				0);
	}
}
