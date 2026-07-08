package com.compresswaterwheel.datagen;

import com.compresswaterwheel.registry.ModBlocks;
import com.simibubi.create.AllBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        // ---- 小型水車 ----
        // tier1: バニラ水車9個 -> 圧縮水車tier1
        buildCompressChain(output,
                AllBlocks.WATER_WHEEL.asItem(),
                ModBlocks.COMPRESSED_WHEELS.stream().map(h -> (ItemLike) h.get()).toList(),
                "compressed_water_wheel");

        // ---- 大型水車 ----
        buildCompressChain(output,
                AllBlocks.LARGE_WATER_WHEEL.asItem(),
                ModBlocks.COMPRESSED_LARGE_WHEELS.stream().map(h -> (ItemLike) h.get()).toList(),
                "compressed_large_water_wheel");
    }

    /**
     * base -> tier1 -> tier2 -> ... と、9個ずつ圧縮/解除するレシピ一式を生成する
     */
    private void buildCompressChain(RecipeOutput output, ItemLike base,
                                    java.util.List<ItemLike> tiers, String nameBase) {
        ItemLike previous = base;

        for (int i = 0; i < tiers.size(); i++) {
            ItemLike current = tiers.get(i);
            int tier = i + 1;

            // 圧縮: 3x3にprevious9個 -> current1個
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, current)
                    .pattern("XXX")
                    .pattern("XXX")
                    .pattern("XXX")
                    .define('X', previous)
                    .unlockedBy("has_" + safeName(previous), has(previous))
                    .save(output, modLoc(nameBase + "_" + tier + "_compress"));

            // 解除: current1個 -> previous9個
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, previous, 9)
                    .requires(current)
                    .unlockedBy("has_" + safeName(current), has(current))
                    .save(output, modLoc(nameBase + "_" + tier + "_decompress"));

            previous = current;
        }
    }

    private static String safeName(ItemLike item) {
        return net.minecraft.core.registries.BuiltInRegistries.ITEM
                .getKey(item.asItem())
                .toString()
                .replace(':', '_');
    }

    private net.minecraft.resources.ResourceLocation modLoc(String path) {
        return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(
                com.compresswaterwheel.CompressWaterWheelMod.MOD_ID, path);
    }
}