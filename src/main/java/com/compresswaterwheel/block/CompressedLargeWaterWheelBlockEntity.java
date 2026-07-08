package com.compresswaterwheel.block;

import com.compresswaterwheel.registry.ModBlockEntities;
import com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CompressedLargeWaterWheelBlockEntity extends LargeWaterWheelBlockEntity {

    public CompressedLargeWaterWheelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COMPRESSED_LARGE_WHEEL_BE.get(), pos, state);
    }
    // getSize()=2 は LargeWaterWheelBlockEntity から継承済みなのでオーバーライド不要
}