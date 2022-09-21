package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class SendoWaveKickAbility extends HamonKnowledge {

	public SendoWaveKickAbility() {
		super(
				EyesOfEnder.id("sendo_wave_kick"),
				Hamon.INTERMEDIATE,
				null,
				0,
				null,
				20 * 35,
				HamonAbilityType.TIMED,
				0);
	}
}
