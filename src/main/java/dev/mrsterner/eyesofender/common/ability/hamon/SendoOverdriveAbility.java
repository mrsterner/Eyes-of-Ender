package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SendoOverdriveAbility extends HamonKnowledge {

	public SendoOverdriveAbility() {
		super(
				EyesOfEnder.id("sendo_overdrive"),
				Hamon.INTERMEDIATE,
				null,
				0,
				null,
				20 * 8,
				HamonAbilityType.CHARGED,
				0);
	}

	@Override
	public void onChargedAbilityUsed(LivingEntity livingEntity) {
		super.onChargedAbilityUsed(livingEntity);
	}

	@Override
	public void useAbility(World world, LivingEntity user, @Nullable Vec3d pos) {
		EOEComponents.HAMON_COMPONENT.get(user).setStoredChargedHamon(this);
		super.useAbility(world, user, pos);
	}
}
