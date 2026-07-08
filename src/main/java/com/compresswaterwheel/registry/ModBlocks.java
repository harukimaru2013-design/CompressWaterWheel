package com.compresswaterwheel.registry;

import com.compresswaterwheel.CompressWaterWheelMod;
import com.compresswaterwheel.block.CompressedWaterWheelBlock;
import com.compresswaterwheel.block.CompressedLargeWaterWheelBlock;
import com.simibubi.create.api.stress.BlockStressValues;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, CompressWaterWheelMod.MOD_ID);

    public static final List<DeferredHolder<Block, CompressedWaterWheelBlock>> COMPRESSED_WHEELS = new ArrayList<>();
    public static final List<DeferredHolder<Block, CompressedLargeWaterWheelBlock>> COMPRESSED_LARGE_WHEELS = new ArrayList<>();

    static {
        BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
                .strength(2.0f)
                .noOcclusion()
                .sound(SoundType.WOOD);

        for (int tier = 1; tier <= 6; tier++) {
            final int t = tier;

            DeferredHolder<Block, CompressedWaterWheelBlock> blockHolder =
                    BLOCKS.register("compressed_water_wheel_" + t,
                            () -> new CompressedWaterWheelBlock(props, t));
            COMPRESSED_WHEELS.add(blockHolder);

            DeferredHolder<Block, CompressedLargeWaterWheelBlock> largeHolder =
                    BLOCKS.register("compressed_large_water_wheel_" + t,
                            () -> new CompressedLargeWaterWheelBlock(props, t));
            COMPRESSED_LARGE_WHEELS.add(largeHolder);
        }
    }
}