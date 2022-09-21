package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import net.minecraft.entity.LivingEntity;

public class OrangeOverdriveAbility extends HamonKnowledge {

	public OrangeOverdriveAbility() {
		super(
				EyesOfEnder.id("orange_overdrive"),
				Hamon.MASTER,
				null,
				0xF5CE8C,
				EOEUtils.Identifiers.OVERLAY_OVERDRIVE,
				20 * 75,
				HamonAbilityType.ACTIVE);
	}

	@Override
	public void tickAbility(LivingEntity entity) {
		super.tickAbility(entity);
	}
}
