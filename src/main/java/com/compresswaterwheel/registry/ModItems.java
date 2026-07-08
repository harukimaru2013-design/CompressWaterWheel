package com.compresswaterwheel.registry;

import com.compresswaterwheel.CompressWaterWheelMod;
import com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlockItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, CompressWaterWheelMod.MOD_ID);

    public static final List<DeferredHolder<Item, ? extends Item>> COMPRESSED_WHEEL_ITEMS = new ArrayList<>();
    public static final List<DeferredHolder<Item, ? extends Item>> COMPRESSED_LARGE_WHEEL_ITEMS = new ArrayList<>();

    static {
        for (DeferredHolder<Block, ? extends Block> blockHolder : ModBlocks.COMPRESSED_WHEELS) {
            COMPRESSED_WHEEL_ITEMS.add(ITEMS.register(blockHolder.getId().getPath(),
                    () -> new BlockItem(blockHolder.get(), new Item.Properties())));
        }

        // ★追加: 大型水車は専用の設置ロジックを持つ LargeWaterWheelBlockItem を使う
        for (DeferredHolder<Block, ? extends Block> blockHolder : ModBlocks.COMPRESSED_LARGE_WHEELS) {
            COMPRESSED_LARGE_WHEEL_ITEMS.add(ITEMS.register(blockHolder.getId().getPath(),
                    () -> new LargeWaterWheelBlockItem(blockHolder.get(), new Item.Properties())));
        }
    }
}