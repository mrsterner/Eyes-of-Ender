package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import net.minecraft.entity.LivingEntity;

public class ActivateHamonAbility extends HamonKnowledge {

	public ActivateHamonAbility() {
		super(
				EyesOfEnder.id("activate"),
				Hamon.BASIC,
				null,
				0xF5CE8C,
				null,
				1,
				HamonAbilityType.PASSIVE,
				0);
	}

	@Override
	public void tickPassiveAbility(LivingEntity entity) {
		super.tickPassiveAbility(entity);
	}

}
