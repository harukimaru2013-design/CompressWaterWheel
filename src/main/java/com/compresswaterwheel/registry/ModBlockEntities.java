package com.compresswaterwheel.registry;

import com.compresswaterwheel.CompressWaterWheelMod;
import com.compresswaterwheel.block.CompressedLargeWaterWheelBlockEntity;
import com.compresswaterwheel.block.CompressedWaterWheelBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CompressWaterWheelMod.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CompressedWaterWheelBlockEntity>> COMPRESSED_WHEEL_BE =
            BLOCK_ENTITIES.register("compressed_water_wheel", () -> {
                Block[] blocks = ModBlocks.COMPRESSED_WHEELS.stream()
                        .map(DeferredHolder::get)
                        .toArray(Block[]::new);


                // ここで型を明示した変数に代入することで、
                // of() 呼び出し時に明示的型引数を使わずに型推論させる
                BlockEntityType.BlockEntitySupplier<CompressedWaterWheelBlockEntity> supplier =
                        CompressedWaterWheelBlockEntity::new;

                return BlockEntityType.Builder
                        .of(supplier, blocks)
                        .build(null);
            });
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CompressedLargeWaterWheelBlockEntity>> COMPRESSED_LARGE_WHEEL_BE =
            BLOCK_ENTITIES.register("compressed_large_water_wheel", () -> {
                Block[] blocks = ModBlocks.COMPRESSED_LARGE_WHEELS.stream()
                        .map(DeferredHolder::get)
                        .toArray(Block[]::new);

                return BlockEntityType.Builder
                        .of(CompressedLargeWaterWheelBlockEntity::new, blocks)
                        .build(null);
            });
}