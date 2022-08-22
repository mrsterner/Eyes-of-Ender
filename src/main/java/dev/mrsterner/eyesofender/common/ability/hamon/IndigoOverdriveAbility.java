package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;

public class IndigoOverdriveAbility extends HamonKnowledge {

	public IndigoOverdriveAbility() {
		super(EyesOfEnder.id("indigo_overdrive"), null, 0xF5CE8C, EOEUtils.Identifiers.OVERLAY_OVERDRIVE);
	}
}
