package com.flamiungo.hud.module;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;

public class KeystrokesModule extends HudModule {

    public KeystrokesModule() {
        super("Keystrokes", 20, 200, 74, 50);
    }

    @Override
    public void render(DrawContext context) {
        // Show only if module is valid? We enforce enabled check in Manager, but safe
        // to check here
        // For editor, even if disabled usually we might want to show it? Nah, user
        // toggles.

        int size = 24; // Box size
        int gap = 2;

        int startX = getX();
        int startY = getY();

        // W
        drawKey(context, mc.options.forwardKey, startX + size + gap, startY, size, size, "W");

        // A
        drawKey(context, mc.options.leftKey, startX, startY + size + gap, size, size, "A");

        // S
        drawKey(context, mc.options.backKey, startX + size + gap, startY + size + gap, size, size, "S");

        // D
        drawKey(context, mc.options.rightKey, startX + (size + gap) * 2, startY + size + gap, size, size, "D");

        // Jump (Space)
        int jumpWidth = (size * 3) + (gap * 2);
        drawKey(context, mc.options.jumpKey, startX, startY + (size + gap) * 2, jumpWidth, 15, "SPACE"); // Smaller
                                                                                                         // height for
                                                                                                         // space

        setWidth(jumpWidth);
        setHeight((size + gap) * 2 + 15 + gap);
    }

    private void drawKey(DrawContext context, KeyBinding key, int x, int y, int w, int h, String name) {
        boolean pressed = key.isPressed();
        int color = pressed ? 0xAAFFFFFF : 0x90000000;
        int txtColor = pressed ? 0xFF000000 : 0xFFFFFFFF;

        context.fill(x, y, x + w, y + h, color);

        int textX = x + (w - mc.textRenderer.getWidth(name)) / 2;
        int textY = y + (h - 8) / 2;
        context.drawText(mc.textRenderer, name, textX, textY, txtColor, true);
    }
}
