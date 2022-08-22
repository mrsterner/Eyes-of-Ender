package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class OverdriveAbility extends HamonKnowledge {
	public OverdriveAbility() {
		super(EyesOfEnder.id("overdrive"), null, 0xF5CE8C, EyesOfEnder.id("textures/gui/ability_widgets/ability/overlay_overdrive.png"));
	}
}
