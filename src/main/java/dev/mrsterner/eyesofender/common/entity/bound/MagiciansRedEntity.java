package dev.mrsterner.eyesofender.common.entity.bound;

import dev.mrsterner.eyesofender.common.entity.BoundStandEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class MagiciansRedEntity extends BoundStandEntity {
    public MagiciansRedEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}