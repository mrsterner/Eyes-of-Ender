package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import net.minecraft.entity.LivingEntity;

public class HamonBeatOverdriveAbility extends HamonKnowledge {
	//TODO requires 2 string
	public HamonBeatOverdriveAbility() {
		super(
				EyesOfEnder.id("hamon_beat_overdrive"),
				Hamon.ADVANCED,
				null,
				0,
				null,
				1,
				HamonAbilityType.ACTIVE,
				0);
	}

	@Override
	public void tickAbility(LivingEntity entity) {
		super.tickAbility(entity);
	}
}
