package com.flamiungo.hud.module;

import net.minecraft.client.gui.DrawContext;

public class ReachDisplayModule extends HudModule {
    
    private static String displayStart = "0.00 blocks";
    private static long lastHitTime = 0;

    public ReachDisplayModule() {
        super("ReachDisplay", 10, 300, 60, 16);
    }

    public static void updateReach(double range) {
        displayStart = String.format("%.2f blocks", range);
        lastHitTime = System.currentTimeMillis();
    }

    @Override
    public void render(DrawContext context) {
        if (!isEnabled()) return;
        
        String prefix = "§bReach: ";
        String value = displayStart;
        String text = prefix + "§f" + value;
        
        int textWidth = mc.textRenderer.getWidth(prefix + value);
        setWidth(textWidth + 10);
        setHeight(18);
        
        int x = getX();
        int y = getY();
        
        context.fill(x, y, x + getWidth(), y + getHeight(), 0x90000000);
        
        // Animated underline or border?
        context.fill(x, y + getHeight() - 1, x + getWidth(), y + getHeight(), 0xFF5555FF);
        
        context.drawText(mc.textRenderer, text, x + 5, y + 5, 0xFFFFFF, true);
    }
}
