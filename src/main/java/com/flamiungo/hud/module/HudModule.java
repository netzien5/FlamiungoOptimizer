package com.flamiungo.hud.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.font.TextRenderer;

public abstract class HudModule {
    protected final MinecraftClient mc = MinecraftClient.getInstance();
    private String name;
    private int x, y;
    private int width, height;
    private boolean enabled;
    private boolean dragging;
    private int dragX, dragY;

    public HudModule(String name, int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.enabled = true;
    }

    public abstract void render(DrawContext context);

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    protected void setWidth(int w) {
        this.width = w;
    }

    public int getHeight() {
        return height;
    }

    protected void setHeight(int h) {
        this.height = h;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void onMouseClick(double mouseX, double mouseY, int button) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            this.dragging = true;
            this.dragX = (int) (mouseX - x);
            this.dragY = (int) (mouseY - y);
        }
    }

    public void onMouseRelease(double mouseX, double mouseY, int button) {
        this.dragging = false;
    }

    public void onMouseDrag(double mouseX, double mouseY, int button) {
        if (this.dragging) {
            this.x = (int) (mouseX - this.dragX);
            this.y = (int) (mouseY - this.dragY);
        }
    }
}
