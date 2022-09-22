package dev.mrsterner.eyesofender.common.entity.stand;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class AutomaticStandEntity extends BaseStandEntity{
    public AutomaticStandEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}
