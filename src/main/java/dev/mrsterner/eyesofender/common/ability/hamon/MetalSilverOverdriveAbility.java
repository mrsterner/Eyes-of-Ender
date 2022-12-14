package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;

public class MetalSilverOverdriveAbility extends HamonKnowledge {

	public MetalSilverOverdriveAbility() {
		super(
				EyesOfEnder.id("metal_silver_overdrive"),
				Hamon.INTERMEDIATE,
				null,
				0xF5CE8C,
				null,
				20 * 10,
				HamonAbilityType.ACTIVE,
				0);
	}
}
