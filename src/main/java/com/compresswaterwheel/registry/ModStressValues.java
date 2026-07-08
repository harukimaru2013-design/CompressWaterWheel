package com.compresswaterwheel.registry;

import com.compresswaterwheel.CompressWaterWheelMod;
import com.compresswaterwheel.block.CompressedWaterWheelBlock;
import com.compresswaterwheel.block.CompressedLargeWaterWheelBlock;
import com.simibubi.create.api.stress.BlockStressValues;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid = CompressWaterWheelMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModStressValues {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        // ---- 小型水車 ----
        for (DeferredHolder<net.minecraft.world.level.block.Block, CompressedWaterWheelBlock> holder : ModBlocks.COMPRESSED_WHEELS) {
            CompressedWaterWheelBlock block = holder.get();
            int tier = block.tier;
            BlockStressValues.CAPACITIES.register(block, () -> 256.0 * Math.pow(9, tier));
            BlockStressValues.setGeneratorSpeed(8).accept(block); // ツールチップ用
        }

        // ★追加: 大型水車
        for (DeferredHolder<net.minecraft.world.level.block.Block, CompressedLargeWaterWheelBlock> holder : ModBlocks.COMPRESSED_LARGE_WHEELS) {
            CompressedLargeWaterWheelBlock block = holder.get();
            int tier = block.tier;
            BlockStressValues.CAPACITIES.register(block, () -> 128.0 * Math.pow(9, tier));
            BlockStressValues.setGeneratorSpeed(4).accept(block); // 大型はgetSize()=2なので最大4RPM
        }
    }
}