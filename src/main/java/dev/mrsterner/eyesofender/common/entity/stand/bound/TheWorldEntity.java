package dev.mrsterner.eyesofender.common.entity.stand.bound;

import dev.mrsterner.eyesofender.api.enums.StandType;
import dev.mrsterner.eyesofender.api.registry.StandAbility;
import dev.mrsterner.eyesofender.common.entity.stand.BaseStandEntity;
import dev.mrsterner.eyesofender.common.registry.EOERegistries;
import dev.mrsterner.eyesofender.common.registry.EOEStandAbilities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TheWorldEntity extends BaseStandEntity {
    public static final List<StandAbility> standAbilities = List.of(
            EOEStandAbilities.TIME_STOP
    );
    public TheWorldEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}
