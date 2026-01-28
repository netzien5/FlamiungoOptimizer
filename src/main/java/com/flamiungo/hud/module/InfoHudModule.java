package com.flamiungo.hud.module;

import net.minecraft.client.gui.DrawContext;
import com.flamiungo.config.FlamiungoConfig;

public class InfoHudModule extends HudModule {

    public InfoHudModule() {
        super("InfoHUD", 4, 4, 100, 25);
    }

    @Override
    public void render(DrawContext context) {
        if (!FlamiungoConfig.showInfoHud || !isEnabled()) return;

        String fps = "§bFPS: §f" + mc.getCurrentFps();
        String x = String.format("§bX: §f%.1f", mc.player.getX());
        String y = String.format("§bY: §f%.1f", mc.player.getY());
        String z = String.format("§bZ: §f%.1f", mc.player.getZ());
        
        int w = 80;
        setWidth(w);
        setHeight(42);
        
        int curX = getX();
        int curY = getY();
        
        context.fill(curX, curY, curX + w, curY + 42, 0x90000000);
        context.drawText(mc.textRenderer, fps, curX + 4, curY + 4, 0xFFFFFF, true);
        context.drawText(mc.textRenderer, x, curX + 4, curY + 14, 0xFFFFFF, true);
        context.drawText(mc.textRenderer, y, curX + 4, curY + 24, 0xFFFFFF, true);
        context.drawText(mc.textRenderer, z, curX + 4, curY + 34, 0xFFFFFF, true);
    }
}
