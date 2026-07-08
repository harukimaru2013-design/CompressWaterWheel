package com.compresswaterwheel.client;

import com.compresswaterwheel.CompressWaterWheelMod;
import com.compresswaterwheel.block.CompressedLargeWaterWheelBlockEntity;
import com.compresswaterwheel.block.CompressedWaterWheelBlockEntity;
import com.compresswaterwheel.registry.ModBlockEntities;
import com.compresswaterwheel.registry.ModBlocks;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelRenderer;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelVisual;
import dev.engine_room.flywheel.api.visual.BlockEntityVisual;
import dev.engine_room.flywheel.api.visualization.BlockEntityVisualizer;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.api.visualization.VisualizerRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = CompressWaterWheelMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    // TESR(静的レンダラー)の登録
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                ModBlockEntities.COMPRESSED_WHEEL_BE.get(),
                WaterWheelRenderer::standard
        );

        // ★追加: 大型水車は large ファクトリを使う
        event.registerBlockEntityRenderer(
                ModBlockEntities.COMPRESSED_LARGE_WHEEL_BE.get(),
                WaterWheelRenderer::large
        );
    }

    // Flywheel Visualizerの登録は FMLClientSetupEvent で行う
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            VisualizerRegistry.setVisualizer(
                    ModBlockEntities.COMPRESSED_WHEEL_BE.get(),
                    new BlockEntityVisualizer<CompressedWaterWheelBlockEntity>() {
                        @Override
                        public BlockEntityVisual<? super CompressedWaterWheelBlockEntity> createVisual(
                                VisualizationContext ctx,
                                CompressedWaterWheelBlockEntity be,
                                float partialTick) {
                            return WaterWheelVisual.standard(ctx, be, partialTick);
                        }

                        @Override
                        public boolean skipVanillaRender(CompressedWaterWheelBlockEntity be) {
                            return true;
                        }
                    }
            );

            // ★追加: 大型水車用のVisualizer登録 (large ファクトリを使う)
            VisualizerRegistry.setVisualizer(
                    ModBlockEntities.COMPRESSED_LARGE_WHEEL_BE.get(),
                    new BlockEntityVisualizer<CompressedLargeWaterWheelBlockEntity>() {
                        @Override
                        public BlockEntityVisual<? super CompressedLargeWaterWheelBlockEntity> createVisual(
                                VisualizationContext ctx,
                                CompressedLargeWaterWheelBlockEntity be,
                                float partialTick) {
                            return WaterWheelVisual.large(ctx, be, partialTick);
                        }

                        @Override
                        public boolean skipVanillaRender(CompressedLargeWaterWheelBlockEntity be) {
                            return true;
                        }
                    }
            );

            // 小型水車のcutoutMipped設定
            ModBlocks.COMPRESSED_WHEELS.forEach(holder ->
                    ItemBlockRenderTypes.setRenderLayer(holder.get(), RenderType.cutoutMipped())
            );

            // ★追加: 大型水車のcutoutMipped設定(同じくgearbox/andesite frame部分の黒つぶれ対策)
            ModBlocks.COMPRESSED_LARGE_WHEELS.forEach(holder ->
                    ItemBlockRenderTypes.setRenderLayer(holder.get(), RenderType.cutoutMipped())
            );
        });
    }
}