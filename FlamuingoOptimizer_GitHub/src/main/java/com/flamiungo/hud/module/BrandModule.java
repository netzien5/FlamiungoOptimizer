package com.flamiungo.hud.module;

import net.minecraft.client.gui.DrawContext;

public class BrandModule extends HudModule {
    public BrandModule() {
        super("Brand", 10, 10, 100, 20);
    }

    @Override
    public void render(DrawContext context) {
        String text = "§b§lFLAMIUNGO §f§lOPTIMIZER §e(ULTIMATE)";
        setWidth(mc.textRenderer.getWidth(text) + 12);
        setHeight(20);

        int x = getX();
        int y = getY();

        context.fill(x, y, x + getWidth(), y + getHeight(), 0x90000000);
        context.fill(x, y, x + 2, y + getHeight(), 0xFF5555FF); // Blue accent on left
        
        context.drawText(mc.textRenderer, text, x + 6, y + 6, 0xFFFFFF, true);
    }
}
