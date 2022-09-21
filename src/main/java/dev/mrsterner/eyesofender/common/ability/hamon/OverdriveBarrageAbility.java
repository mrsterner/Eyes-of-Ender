package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class OverdriveBarrageAbility extends HamonKnowledge {
	public OverdriveBarrageAbility() {
		super(EyesOfEnder.id("overdrive_barrage"), Hamon.BASIC, null, 0xF5CE8C, null, 30, false);
	}
}
