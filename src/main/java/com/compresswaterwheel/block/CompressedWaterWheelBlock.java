package com.compresswaterwheel.block;

import com.compresswaterwheel.registry.ModBlockEntities;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelBlock;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CompressedWaterWheelBlock extends WaterWheelBlock {
    public final int tier;

    public CompressedWaterWheelBlock(BlockBehaviour.Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.COMPRESSED_WHEEL_BE.get().create(pos, state);
    }

    @Override
    public BlockEntityType<? extends WaterWheelBlockEntity> getBlockEntityType() {
        return ModBlockEntities.COMPRESSED_WHEEL_BE.get();
    }
}