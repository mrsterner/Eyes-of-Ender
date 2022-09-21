package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import net.minecraft.entity.LivingEntity;

public class SlowedAgeingAbility extends HamonKnowledge {

	public SlowedAgeingAbility() {
		super(
				EyesOfEnder.id("slowed_ageing"),
				Hamon.MASTER,
				null,
				0,
				null,
				0,
				HamonAbilityType.PASSIVE);
	}

	@Override
	public void tickAbility(LivingEntity entity) {
		super.tickAbility(entity);
	}
}
