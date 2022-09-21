package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import net.minecraft.entity.LivingEntity;

public class OverdriveTurquoiseAbility extends HamonKnowledge {
	public OverdriveTurquoiseAbility() {
		super(EyesOfEnder.id("overdrive_turquoise"), Hamon.BASIC, null, 0xF5CE8C, EOEUtils.Identifiers.OVERLAY_OVERDRIVE, 2, true);
	}

	@Override
	public void tickAbility(LivingEntity entity) {
		super.tickAbility(entity);
	}
}
