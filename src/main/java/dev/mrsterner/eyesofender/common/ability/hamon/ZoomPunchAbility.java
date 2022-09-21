package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class ZoomPunchAbility extends HamonKnowledge {

	public ZoomPunchAbility() {
		super(
				EyesOfEnder.id("zoom_punch"),
				Hamon.INTERMEDIATE,
				null,
				0,
				null,
				20 * 10,
				HamonAbilityType.CHARGED);
	}
}
