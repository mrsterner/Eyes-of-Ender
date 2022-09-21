package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import net.minecraft.entity.LivingEntity;

public class IndigoOverdriveAbility extends HamonKnowledge {

	public IndigoOverdriveAbility() {
		super(
				EyesOfEnder.id("indigo_overdrive"),
				Hamon.ADVANCED,
				null,
				0xF5CE8C,
				EOEUtils.Identifiers.OVERLAY_OVERDRIVE,
				2,
				HamonAbilityType.CHARGED);
	}

	@Override
	public void tickAbility(LivingEntity entity) {
		super.tickAbility(entity);
	}
}
