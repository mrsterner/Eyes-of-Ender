package dev.mrsterner.eyesofender.common.entity.stand.bound;

import dev.mrsterner.eyesofender.common.entity.stand.BoundStandEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class TheWorldEntity extends BoundStandEntity {
    public TheWorldEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}
