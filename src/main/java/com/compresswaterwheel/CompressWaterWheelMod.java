package com.compresswaterwheel;

import com.compresswaterwheel.event.ConfigOverride;
import com.compresswaterwheel.registry.ModBlockEntities;  // ← インポート追加
import com.compresswaterwheel.registry.ModBlocks;
import com.compresswaterwheel.registry.ModCreativeTab;
import com.compresswaterwheel.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(CompressWaterWheelMod.MOD_ID)
public class CompressWaterWheelMod {
    public static final String MOD_ID = "compresswaterwheel";

    public CompressWaterWheelMod(IEventBus modEventBus) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);  // ★ これが必須！
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTab.TABS.register(modEventBus);

        modEventBus.addListener(ConfigOverride::onConfigLoading);
        modEventBus.addListener(ConfigOverride::onConfigReloading);
    }
}