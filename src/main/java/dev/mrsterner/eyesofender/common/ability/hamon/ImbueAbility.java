package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.registry.EOEStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ImbueAbility extends HamonKnowledge {
	public ImbueAbility() {

		super(EyesOfEnder.id("imbue"),
				Hamon.BASIC,
				null,
				0,
				null,
				20*2,
				HamonAbilityType.CHARGED,
				0);
	}

	@Override
	public void useAbility(World world, LivingEntity user, @Nullable Vec3d pos) {
		int maxDistance = 2;
		double distance = Math.pow(maxDistance, 2);
		Vec3d vec3d = user.getCameraPosVec(1);
		Vec3d vec3d2 = user.getRotationVec(1);
		Vec3d vec3d3 = vec3d.add(vec3d2.x * maxDistance, vec3d2.y * maxDistance, vec3d2.z * maxDistance);
		EntityHitResult hit = ProjectileUtil.getEntityCollision(world, user, vec3d, vec3d3, user.getBoundingBox().stretch(vec3d2.multiply(distance)).expand(1.0D, 1.0D, 1.0D), (target) -> !target.isSpectator() && target.collides());

		if (hit != null) {
			if(hit.getEntity() instanceof LivingEntity livingEntity){
				livingEntity.addStatusEffect(new StatusEffectInstance(EOEStatusEffects.HAMON_IMBUE, 20 * 60,1), user);
				user.swingHand(user.preferredHand);
			}
		}
	}
}
