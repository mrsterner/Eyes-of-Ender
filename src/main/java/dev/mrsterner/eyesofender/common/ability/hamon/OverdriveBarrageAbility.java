package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class OverdriveBarrageAbility extends HamonKnowledge {
	//TODO ask what this actually does
	public OverdriveBarrageAbility() {
		super(
				EyesOfEnder.id("overdrive_barrage"),
				Hamon.BASIC,
				null,
				0,
				null,
				20 * 100,
				HamonAbilityType.TIMED,
				0);
	}
}
