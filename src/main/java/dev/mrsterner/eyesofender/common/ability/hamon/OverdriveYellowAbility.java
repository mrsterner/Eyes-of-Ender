package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import net.minecraft.entity.LivingEntity;

public class OverdriveYellowAbility extends HamonKnowledge {
	public OverdriveYellowAbility() {
		super(EyesOfEnder.id("overdrive_yellow"), Hamon.BASIC, null, 0xF5CE8C, EOEUtils.Identifiers.OVERLAY_OVERDRIVE, 2, true);
	}

	@Override
	public void tickAbility(LivingEntity entity) {
		super.tickAbility(entity);
	}
}
