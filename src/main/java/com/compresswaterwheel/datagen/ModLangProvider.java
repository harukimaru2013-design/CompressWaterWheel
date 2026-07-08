package com.compresswaterwheel.datagen;

import com.compresswaterwheel.CompressWaterWheelMod;
import com.compresswaterwheel.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.text.NumberFormat;
import java.util.Locale;

public class ModLangProvider extends LanguageProvider {

    private final String locale;

    public ModLangProvider(PackOutput output, String locale) {
        super(output, CompressWaterWheelMod.MOD_ID, locale);
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        boolean ja = locale.equals("ja_jp");

        // itemGroup(クリエイティブタブ)名
        add("itemGroup.compresswaterwheel", ja ? "圧縮水車" : "Compressed Water Wheels");

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US); // 3桁カンマ区切り

        for (int tier = 1; tier <= 6; tier++) {
            long multiplier = (long) Math.pow(9, tier);
            String formatted = numberFormat.format(multiplier);

            String smallName = ja
                    ? (formatted + "倍圧縮水車")
                    : (formatted + "x Compressed Water Wheel");
            String largeName = ja
                    ? (formatted + "倍圧縮大型水車")
                    : (formatted + "x Compressed Large Water Wheel");

            add(ModBlocks.COMPRESSED_WHEELS.get(tier - 1).get(), smallName);
            add(ModBlocks.COMPRESSED_LARGE_WHEELS.get(tier - 1).get(), largeName);
        }
    }
}