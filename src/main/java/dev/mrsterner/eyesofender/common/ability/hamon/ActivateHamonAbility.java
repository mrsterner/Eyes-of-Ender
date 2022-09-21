package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import net.minecraft.entity.LivingEntity;

public class ActivateHamonAbility extends HamonKnowledge {

	public ActivateHamonAbility() {
		super(EyesOfEnder.id("activate"), null, 0xF5CE8C, null, 1, true);
	}

	@Override
	public void tickAbility(LivingEntity entity) {
		super.tickAbility(entity);
	}
}
