package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IndigoOverdriveAbility extends HamonKnowledge {

	public IndigoOverdriveAbility() {
		super(
				EyesOfEnder.id("indigo_overdrive"),
				Hamon.ADVANCED,
				null,
				0xF5CE8C,
				EOEUtils.Identifiers.OVERLAY_OVERDRIVE,
				2,
				HamonAbilityType.CHARGED,
				0);
	}

	@Override
	public void onChargedAbilityUsed(LivingEntity livingEntity, LivingEntity target) {
		super.onChargedAbilityUsed(livingEntity, target);
	}

	@Override
	public void useAbility(World world, LivingEntity user, @Nullable Vec3d pos) {
		EOEComponents.HAMON_COMPONENT.get(user).setStoredChargedHamon(this);
		super.useAbility(world, user, pos);
	}
}
