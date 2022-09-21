package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import net.minecraft.entity.LivingEntity;

public class HamonAttractionAbility extends HamonKnowledge {
	//Wall climbing
	public HamonAttractionAbility() {
		super(
				EyesOfEnder.id("hamon_attraction"),
				Hamon.INTERMEDIATE,
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
