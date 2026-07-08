package com.compresswaterwheel.block;

import com.compresswaterwheel.registry.ModBlockEntities;
import com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlock;
import com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CompressedLargeWaterWheelBlock extends LargeWaterWheelBlock {
    public final int tier;

    public CompressedLargeWaterWheelBlock(BlockBehaviour.Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public BlockEntityType<? extends LargeWaterWheelBlockEntity> getBlockEntityType() {
        return ModBlockEntities.COMPRESSED_LARGE_WHEEL_BE.get();
    }
    // getBlockEntityClass()はLargeWaterWheelBlockから継承(LargeWaterWheelBlockEntity.class)でOK
}