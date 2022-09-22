package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class HamonAfterimagesAbility extends HamonKnowledge {

	public HamonAfterimagesAbility() {
		super(
				EyesOfEnder.id("afterimage"),
				Hamon.MASTER,
				null,
				0,
				null,
				20 * 50,
				HamonAbilityType.ACTIVE,
				0);
	}
}
