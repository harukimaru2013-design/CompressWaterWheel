package com.compresswaterwheel.mixin;

import java.util.function.Consumer;

import com.compresswaterwheel.client.CompressedSpeedControllerScreen;
import com.simibubi.create.content.kinetics.speedController.SpeedControllerBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBehaviour.ValueSettings;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBoard;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsClient;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ValueSettingsClient.class)
public class ValueSettingsClientMixin {

    /**
     * Speed Controller(回転速度コントローラー)に対してのみ、
     * 数値入力画面(CompressedSpeedControllerScreen)を代わりに開く。
     * それ以外のブロック(ハンドクランク、レッドストーンリンク等)は
     * 本家のValueSettingsScreen(スライダーボード)のまま。
     */
    @Redirect(method = "tick",
            at = @At(value = "NEW", target = "com/simibubi/create/foundation/blockEntity/behaviour/ValueSettingsScreen"))
    private ValueSettingsScreen compresswaterwheel$maybeCompressedScreen(
            BlockPos pos, ValueSettingsBoard board, ValueSettings settings,
            Consumer<ValueSettings> onHover, int netId) {

        Level level = Minecraft.getInstance().level;
        if (level != null && level.getBlockEntity(pos) instanceof SpeedControllerBlockEntity) {
            return new CompressedSpeedControllerScreen(pos, board, settings, onHover, netId);
        }
        return new ValueSettingsScreen(pos, board, settings, onHover, netId);
    }
}