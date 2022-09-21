package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import net.minecraft.entity.LivingEntity;

public class HamonAfterimagesAbility extends HamonKnowledge {

	public HamonAfterimagesAbility() {
		super(EyesOfEnder.id("afterimage"), null, 0xF5CE8C, null, 1, true);
	}

	@Override
	public void tickAbility(LivingEntity entity) {
		super.tickAbility(entity);
	}
}
