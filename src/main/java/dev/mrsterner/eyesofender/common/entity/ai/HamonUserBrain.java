package dev.mrsterner.eyesofender.common.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Dynamic;
import dev.mrsterner.eyesofender.common.entity.hamon.HamonUserEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.VisibleLivingEntitiesCache;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.List;
import java.util.Optional;

public class HamonUserBrain {
    private static final List<SensorType<? extends Sensor<? super HamonUserEntity>>> SENSORS =List.of(
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.HURT_BY);
    private static final List<MemoryModuleType<?>> MEMORIES = List.of(
            MemoryModuleType.MOBS,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.HOME,
            MemoryModuleType.PACIFIED,
            MemoryModuleType.NEAREST_REPELLENT,
            MemoryModuleType.AVOID_TARGET
    );

    public HamonUserBrain(){

    }

    public static Brain<?> create(HamonUserEntity hamonUserEntity, Dynamic<?> dynamic) {
        Brain.Profile<HamonUserEntity> profile = Brain.createProfile(MEMORIES, SENSORS);
        Brain<HamonUserEntity> brain = profile.deserialize(dynamic);
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(hamonUserEntity, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<HamonUserEntity> brain) {
        brain.setTaskList(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new StayAboveWaterTask(0.6f), //Make the user not sink and die
                        new LookAroundTask(45, 90), //Look around is nice
                        new WanderAroundTask(), //Wandering
                        new UpdateAttackTargetTask<>(HamonUserBrain::getAttackTarget)//Update targeted entity(player) for the fightingActivities
                )
        );
    }

    private static void addIdleActivities(Brain<HamonUserEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0 , GoToRememberedPositionTask.toBlock(MemoryModuleType.NEAREST_REPELLENT, 1.0F, 8, true)),
                        Pair.of(1, new RandomTask<>(
                                ImmutableList.of(
                                        Pair.of(new StrollTask(0.6F), 2),
                                        Pair.of(new ConditionalTask<>(livingEntity -> true, new GoTowardsLookTarget(0.6F, 3)), 2),
                                        Pair.of(new WaitTask(30, 60), 1)
                                )))
                )
        );
    }
    private static void addFightActivities(HamonUserEntity hamonUserEntity, Brain<HamonUserEntity> brain) {
        brain.setTaskList(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                        new RangedApproachTask(1.0F),
                        new FollowMobTask(mob -> isTarget(hamonUserEntity, mob), (float)hamonUserEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                        new MeleeAttackTask(18)
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    private static Optional<? extends LivingEntity> getAttackTarget(HamonUserEntity hamonUserEntity) {
        Brain<HamonUserEntity> brain = hamonUserEntity.getBrain();
        Optional<LivingEntity> optional = LookTargetUtil.getEntity(hamonUserEntity, MemoryModuleType.ANGRY_AT);
        if (brain.hasMemoryModule(MemoryModuleType.VISIBLE_MOBS)) {
            Optional<VisibleLivingEntitiesCache> visibleLivingEntitiesCache = hamonUserEntity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS);
            if(visibleLivingEntitiesCache.isPresent()){
               //TODO return visibleLivingEntitiesCache.get().method_38975(entity -> entity.getType() == EntityType.PLAYER && !entity.isSubmergedInWater());
            }
        }
        return Optional.empty();
    }

    public static void updateActivities(HamonUserEntity hamonUserEntity) {
        hamonUserEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        hamonUserEntity.setAttacking(hamonUserEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
    }

    private static boolean isTarget(HamonUserEntity hamonUserEntity, LivingEntity entity) {
        return hamonUserEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter(targetedEntity -> targetedEntity == entity).isPresent();
    }
}
