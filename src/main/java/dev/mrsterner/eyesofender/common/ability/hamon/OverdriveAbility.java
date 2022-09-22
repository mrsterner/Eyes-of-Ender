package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;

public class OverdriveAbility extends HamonKnowledge {
	public OverdriveAbility() {
		super(
				EyesOfEnder.id("overdrive"),
				Hamon.BASIC,
				null,
				0xF5CE8C,
				EOEUtils.Identifiers.OVERLAY_OVERDRIVE,
				20 * 5,
				HamonAbilityType.HIT,
				0);
	}
}
