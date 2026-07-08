package com.compresswaterwheel.block;

import com.compresswaterwheel.registry.ModBlockEntities;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CompressedWaterWheelBlockEntity extends WaterWheelBlockEntity {

    public CompressedWaterWheelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COMPRESSED_WHEEL_BE.get(), pos, state);
        this.material = Blocks.SPRUCE_PLANKS.defaultBlockState();
    }
}