package dev.mrsterner.eyesofender.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class StandEntity extends PathAwareEntity implements IAnimatable {
	private AnimationFactory factory = new AnimationFactory(this);
	public Vec3d motionCalc = new Vec3d(0,0,0);

	protected StandEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void registerControllers(AnimationData animationData) {

	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
