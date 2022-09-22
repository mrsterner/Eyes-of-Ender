package dev.mrsterner.eyesofender.common.entity.stand.bound;

import dev.mrsterner.eyesofender.common.entity.stand.BoundStandEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class CreamEntity extends BoundStandEntity {
    public CreamEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}
