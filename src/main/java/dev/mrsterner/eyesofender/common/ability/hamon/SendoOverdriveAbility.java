package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class SendoOverdriveAbility extends HamonKnowledge {

	public SendoOverdriveAbility() {
		super(EyesOfEnder.id("sendo_overdrive"), Hamon.INTERMEDIATE, null, 0, null, 10, false);
	}
}
