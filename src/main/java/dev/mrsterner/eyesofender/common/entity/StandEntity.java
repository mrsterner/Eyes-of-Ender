package dev.mrsterner.eyesofender.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class StandEntity extends PathAwareEntity implements IAnimatable {
	final AnimationFactory factory = new AnimationFactory(this);
	private static final TrackedData<Integer> OWNER_ENTITY_ID = DataTracker.registerData(StandEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private LivingEntity ownerEntity;
	public Vec3d motionCalc = new Vec3d(0,0,0);

	public StandEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	public StandEntity(EntityType<? extends PathAwareEntity> entityType, World world, LivingEntity livingEntity) {
		super(entityType, world);
		this.ownerEntity = livingEntity;
	}

	@Override
	protected void mobTick() {
		super.mobTick();
		if(ownerEntity == null){
			this.discard();
		}
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(OWNER_ENTITY_ID, -1);
	}

	private void setOwnerEntityId(LivingEntity entity) {
		this.ownerEntity = entity;
		this.dataTracker.set(OWNER_ENTITY_ID, ownerEntity == null ? -1 : entity.getId());
	}

	public int getOwnerEntityId() {
		return dataTracker.get(OWNER_ENTITY_ID);
	}

	private <E extends IAnimatable> PlayState basePredicate(AnimationEvent<E> animationEvent) {
		AnimationBuilder builder = new AnimationBuilder();
		builder.addAnimation( "animation.stand.standing.idle", true);
		animationEvent.getController().setAnimation(builder);
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController<>(this, "BasePredicate", 2, this::basePredicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10)
				.add(EntityAttributes.GENERIC_ARMOR, 20)
				.add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 10)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4);
	}
}
