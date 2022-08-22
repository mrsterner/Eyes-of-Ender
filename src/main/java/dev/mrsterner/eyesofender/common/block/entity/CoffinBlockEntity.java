package dev.mrsterner.eyesofender.common.block.entity;

import dev.mrsterner.eyesofender.common.registry.EOEBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class CoffinBlockEntity extends BlockEntity {
    public CoffinBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EOEBlockEntityTypes.COFFIN_BLOCK_ENTITY, blockPos, blockState);
    }
}
