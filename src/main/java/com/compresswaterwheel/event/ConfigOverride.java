package com.compresswaterwheel.event;

import com.simibubi.create.infrastructure.config.AllConfigs;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;

public class ConfigOverride {

    public static void onConfigLoading(ModConfigEvent.Loading event) {
        overrideMaxRotationSpeed(event);
    }

    public static void onConfigReloading(ModConfigEvent.Reloading event) {
        overrideMaxRotationSpeed(event);
    }

    private static void overrideMaxRotationSpeed(ModConfigEvent event) {
        if (!event.getConfig().getModId().equals("create")) return;
        if (event.getConfig().getType() != ModConfig.Type.SERVER) return;

        if (AllConfigs.server() != null) {
            // int 型なので Integer.MAX_VALUE (2147483647) を設定
            AllConfigs.server().kinetics.maxRotationSpeed.set(Integer.MAX_VALUE);
        }
    }
}