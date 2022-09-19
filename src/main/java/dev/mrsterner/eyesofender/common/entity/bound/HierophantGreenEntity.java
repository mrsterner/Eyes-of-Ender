package dev.mrsterner.eyesofender.common.entity.bound;

import dev.mrsterner.eyesofender.common.entity.BoundStandEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class HierophantGreenEntity extends BoundStandEntity {
    public HierophantGreenEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}
