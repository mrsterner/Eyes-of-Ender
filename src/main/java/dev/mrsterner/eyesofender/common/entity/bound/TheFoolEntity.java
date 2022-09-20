package dev.mrsterner.eyesofender.common.entity.bound;

import dev.mrsterner.eyesofender.common.entity.BoundStandEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class TheFoolEntity extends BoundStandEntity {
    public TheFoolEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}
