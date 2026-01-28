package com.flamiungo.screen;

import com.flamiungo.hud.module.HudModule;
import com.flamiungo.hud.module.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.Color;

public class HudEditorScreen extends Screen {

    public HudEditorScreen() {
        super(Text.literal("HUD Editor"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Draw background shadow
        this.renderBackground(context, mouseX, mouseY, delta);

        // Draw an instructions text at top
        String instr = "HUD Editor - Drag modules to reposition. Press ESC to save.";
        context.drawCenteredTextWithShadow(textRenderer, instr, width / 2, 10, 0xFFFFFF);

        // Draw all modules (even disabled ones but highlighted differently maybe?)
        for (HudModule module : ModuleManager.getModules()) {
            // Draw a border around the module area for visibility
            int x = module.getX();
            int y = module.getY();
            int w = module.getWidth();
            int h = module.getHeight();

            // Highlight border if mouse is over
            boolean hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
            int borderColor = hovered ? 0xFF00FFFF : 0x80FFFFFF;
            
            // Draw dummy background for editor
            context.fill(x, y, x + w, y + h, 0x40000000);
            
            // Draw border
            context.drawHorizontalLine(x, x + w, y, borderColor);
            context.drawHorizontalLine(x, x + w, y + h, borderColor);
            context.drawVerticalLine(x, y, y + h, borderColor);
            context.drawVerticalLine(x + w, y, y + h, borderColor);

            module.render(context);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (HudModule module : ModuleManager.getModules()) {
            module.onMouseClick(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (HudModule module : ModuleManager.getModules()) {
            module.onMouseRelease(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        for (HudModule module : ModuleManager.getModules()) {
            module.onMouseDrag(mouseX, mouseY, button);
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
