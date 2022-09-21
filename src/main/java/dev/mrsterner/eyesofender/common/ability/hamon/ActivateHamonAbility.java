package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.kosmx.playerAnim.core.util.Vec3d;
import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ActivateHamonAbility extends HamonKnowledge {

	public ActivateHamonAbility() {
		super(EyesOfEnder.id("activate"), null, 0xF5CE8C, null, 1, true);
	}

	@Override
	public void tickAbility(LivingEntity entity) {
		super.tickAbility(entity);
	}

}
