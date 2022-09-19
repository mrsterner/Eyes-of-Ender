package dev.mrsterner.eyesofender.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BaseStandEntity extends PathAwareEntity implements IAnimatable {
	private final MoveControl airMoveControl;
	private final MoveControl groundMoveControl;
	private final EntityNavigation airNavigation;
	private final EntityNavigation groundNavigation;

	private static final TrackedData<Integer> OWNER_ENTITY_ID = DataTracker.registerData(BaseStandEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Boolean> IS_FLYING = DataTracker.registerData(BaseStandEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	private LivingEntity ownerEntity;

	public Vec3d motionCalc = new Vec3d(0,0,0);
	final AnimationFactory factory = new AnimationFactory(this);

	public BaseStandEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
		this.airMoveControl = new FlightMoveControl(this, 12, true);
		this.groundMoveControl = new MoveControl(this);
		this.airNavigation = new BirdNavigation(this, world);
		this.groundNavigation = new MobNavigation(this, world);
		this.moveControl = groundMoveControl;
		this.navigation = groundNavigation;
	}


	@Override
	protected void mobTick() {
		super.mobTick();
		/*TODO
		if(ownerEntity == null){
			this.discard();
		}

		 */
		if (!world.isClient) {

		}
	}

	@Override
	protected void initGoals() {
		super.initGoals();
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(OWNER_ENTITY_ID, -1);
		this.dataTracker.startTracking(IS_FLYING, false);
	}
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("IS_FLYING", dataTracker.get(IS_FLYING));
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		setFlying(nbt.getBoolean("IS_FLYING"));
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
		builder.addAnimation( "idle", true);
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

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	public boolean canFreeze() {
		return false;
	}

	public boolean isFlying() {
		return dataTracker.get(IS_FLYING);
	}

	public void setFlying(boolean flying) {
		dataTracker.set(IS_FLYING, flying);
		changeNavigation();
	}


	private void changeNavigation() {
		if (isFlying()) {
			this.moveControl = airMoveControl;
			this.navigation = airNavigation;
		} else {
			this.moveControl = groundMoveControl;
			this.navigation = groundNavigation;
		}
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
