package com.compresswaterwheel.client;

import java.util.function.Consumer;

import com.compresswaterwheel.CompressWaterWheelMod;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBehaviour.ValueSettings;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBoard;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsPacket;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsScreen;

import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/**
 * Create本家の回転速度コントローラーのスライダー(ボード)UIの代わりに、
 * 数値を直接キーボード入力できる画面。ValueSettingsScreenを継承しているが、
 * 親クラスのprivateフィールドには一切依存せず、init()/renderWindow()を完全に独自実装している。
 */
public class CompressedSpeedControllerScreen extends ValueSettingsScreen {

    private static final ResourceLocation BG_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CompressWaterWheelMod.MOD_ID, "textures/gui/value_settings.png");

    private final BlockPos myPos;
    private final int myNetId;
    private final int myMaxValue;
    private final ValueSettings myInitial;
    private final Component myTitle;

    private EditBox input;

    public CompressedSpeedControllerScreen(BlockPos pos, ValueSettingsBoard board, ValueSettings valueSettings,
                                           Consumer<ValueSettings> onHover, int netId) {
        super(pos, board, valueSettings, onHover, netId);
        this.myPos = pos;
        this.myNetId = netId;
        this.myMaxValue = board.maxValue();
        this.myInitial = valueSettings;
        this.myTitle = board.title();
    }
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        // 親クラス(ValueSettingsScreen)の「離したら自動保存して閉じる」処理を無効化する。
        // EditBox自体のクリック解除処理だけ通す。
        if (input != null && input.mouseReleased(mouseX, mouseY, button))
            return true;
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        // 同上。keyReleasedでも自動確定させない。
        return false;
    }

    @Override
    protected void init() {
        // ウィンドウの高さを少し広げて、上下の重なりを解消します
        windowWidth = 140;
        windowHeight = 58; // 50 -> 58 に変更
        guiLeft = (width - windowWidth) / 2;
        guiTop = (height - windowHeight) / 2;

        // Y座標を 24 -> 18 に上げ、タイトルや下部テキストとの間隔を空けます
        input = new EditBox(font, guiLeft + 20, guiTop + 18, 100, 16, Component.literal("value"));
        input.setValue(String.valueOf(myInitial.value()));
        input.setFilter(s -> s.isEmpty() || s.matches("-?[0-9]*"));
        input.setMaxLength(10);
        addRenderableWidget(input);
        setInitialFocus(input);
    }

    @Override
    protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // 背景(1x1のBGスウォッチを引き伸ばして塗りつぶす)
        graphics.blit(BG_TEXTURE, guiLeft, guiTop, windowWidth, windowHeight, 80, 9, 1, 1, 256, 256);

        // 四隅の枠
        graphics.blit(BG_TEXTURE, guiLeft - 1, guiTop - 1, 4, 4, 65, 9, 4, 4, 256, 256);
        graphics.blit(BG_TEXTURE, guiLeft + windowWidth - 3, guiTop - 1, 4, 4, 70, 9, 4, 4, 256, 256);
        graphics.blit(BG_TEXTURE, guiLeft - 1, guiTop + windowHeight - 3, 4, 4, 65, 19, 4, 4, 256, 256);
        graphics.blit(BG_TEXTURE, guiLeft + windowWidth - 3, guiTop + windowHeight - 3, 4, 4, 70, 19, 4, 4, 256, 256);

        // タイトルの描画 (Y座標: 6)
        graphics.drawCenteredString(font, myTitle, guiLeft + windowWidth / 2, guiTop + 6, 0xFFFFFF);

        // 下部の数値範囲の描画：
        // 1. Y座標を 36 -> 40 に下げて入力ボックス(Y: 18~34)との被りを解消
        // 2. drawCenteredString を使用し、入力ボックスの中央に綺麗に揃える
        graphics.drawCenteredString(font, "-" + myMaxValue + " ~ " + myMaxValue, guiLeft + windowWidth / 2, guiTop + 40, 0xAAAAAA);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257 || keyCode == 335) { // Enter / Numpad Enter
            confirmAndClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void confirmAndClose() {
        int value;
        try {
            value = Integer.parseInt(input.getValue());
        } catch (NumberFormatException e) {
            value = 0;
        }
        value = Mth.clamp(value, -myMaxValue, myMaxValue);

        CatnipServices.NETWORK.sendToServer(new ValueSettingsPacket(
                myPos, myInitial.row(), value, null, null, Direction.UP, false, myNetId));
        onClose();
    }
}