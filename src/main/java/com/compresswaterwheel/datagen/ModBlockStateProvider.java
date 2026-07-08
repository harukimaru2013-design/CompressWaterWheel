package com.compresswaterwheel.datagen;

import com.compresswaterwheel.CompressWaterWheelMod;
import com.compresswaterwheel.registry.ModBlocks;
import com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CompressWaterWheelMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile smallModel = new ModelFile.UncheckedModelFile(
                ResourceLocation.fromNamespaceAndPath("create", "block/water_wheel/block"));
        ModelFile largeModel = new ModelFile.UncheckedModelFile(
                ResourceLocation.fromNamespaceAndPath("create", "block/large_water_wheel/block"));
        ModelFile largeModelExt = new ModelFile.UncheckedModelFile(
                ResourceLocation.fromNamespaceAndPath("create", "block/large_water_wheel/block_extension"));

        // ---- 小型水車(tier 1〜6) ----
        for (var holder : ModBlocks.COMPRESSED_WHEELS) {
            Block block = holder.get();

            getVariantBuilder(block).forAllStates(state -> {
                Direction facing = state.getValue(BlockStateProperties.FACING);
                int x = 0, y = 0;
                switch (facing) {
                    case DOWN -> x = 180;
                    case UP -> { }
                    case NORTH -> x = 90;
                    case SOUTH -> { x = 90; y = 180; }
                    case EAST -> { x = 90; y = 90; }
                    case WEST -> { x = 90; y = 270; }
                }
                return ConfiguredModel.builder()
                        .modelFile(smallModel)
                        .rotationX(x)
                        .rotationY(y)
                        .build();
            });

            simpleBlockItem(block, smallModel);
        }

        // ---- 大型水車(tier 1〜6) ----
        for (var holder : ModBlocks.COMPRESSED_LARGE_WHEELS) {
            Block block = holder.get();

            getVariantBuilder(block).forAllStates(state -> {
                Direction.Axis axis = state.getValue(LargeWaterWheelBlock.AXIS);
                boolean extension = state.getValue(LargeWaterWheelBlock.EXTENSION);
                ModelFile model = extension ? largeModelExt : largeModel;

                int x = 0, y = 0;
                switch (axis) {
                    case X -> { x = 90; y = 90; }
                    case Y -> { }
                    case Z -> { x = 90; y = 180; }
                }
                return ConfiguredModel.builder()
                        .modelFile(model)
                        .rotationX(x)
                        .rotationY(y)
                        .build();
            });

            simpleBlockItem(block, largeModel);
        }
    }
}