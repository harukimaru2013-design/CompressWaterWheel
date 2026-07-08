package com.compresswaterwheel.registry;

import com.compresswaterwheel.CompressWaterWheelMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CompressWaterWheelMod.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COMPRESSED_WHEELS_TAB =
            TABS.register("compressed_wheels",
                    () -> CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.compresswaterwheel"))
                            .icon(() -> new ItemStack(ModBlocks.COMPRESSED_WHEELS.get(0).get()))
                            .displayItems((params, output) -> {
                                for (var holder : ModItems.COMPRESSED_WHEEL_ITEMS) {
                                    output.accept(holder.get());
                                }
                                // ★追加: 大型水車も表示する
                                for (var holder : ModItems.COMPRESSED_LARGE_WHEEL_ITEMS) {
                                    output.accept(holder.get());
                                }
                            })
                            .build());
}