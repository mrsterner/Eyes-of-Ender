package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class HealingAbility extends HamonKnowledge {
	public HealingAbility() {
		super(EyesOfEnder.id("healing"), null, 0xF5CE8C, null, 50, false);
	}

	@Override
	public void useAbility(World world, LivingEntity user, @Nullable Vec3d pos) {
		user.world.createExplosion(user, user.getX(), user.getY(), user.getZ(), 1, Explosion.DestructionType.BREAK);
		//TODO
	}
}
