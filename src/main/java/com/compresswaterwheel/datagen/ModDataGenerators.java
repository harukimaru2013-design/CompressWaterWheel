package com.compresswaterwheel.datagen;

import com.compresswaterwheel.CompressWaterWheelMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = CompressWaterWheelMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var existingFileHelper = event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(),
                new ModBlockStateProvider(packOutput, existingFileHelper));

        generator.addProvider(event.includeClient(),
                new ModLangProvider(packOutput, "en_us"));
        generator.addProvider(event.includeClient(),
                new ModLangProvider(packOutput, "ja_jp"));

        generator.addProvider(event.includeServer(),
                new ModLootTableProvider(packOutput, lookupProvider));

        // ★追加
        generator.addProvider(event.includeServer(),
                new ModRecipeProvider(packOutput, lookupProvider));
    }
}