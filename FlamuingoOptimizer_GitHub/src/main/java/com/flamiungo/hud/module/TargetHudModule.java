package com.flamiungo.hud.module;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import com.flamiungo.config.FlamiungoConfig;

public class TargetHudModule extends HudModule {

    private float animatedHealth = 20.0f;

    public TargetHudModule() {
        super("TargetHUD", 100, 100, 150, 50);
        this.setEnabled(FlamiungoConfig.targetHud);
    }

    @Override
    public void render(DrawContext context) {
        if (!isEnabled())
            return;

        LivingEntity target = null;
        if (mc.targetedEntity instanceof LivingEntity) {
            target = (LivingEntity) mc.targetedEntity;
        } else if (mc.currentScreen != null && mc.currentScreen.getClass().getSimpleName().equals("HudEditorScreen")) {
            // Dummy target for editor
            target = mc.player;
        }

        if (target == null)
            return;

        // Update Width/Height based on content if needed, but fixed size is easier for
        // now
        setWidth(150);
        setHeight(50);

        int x = getX();
        int y = getY();

        // 1. Background (Premium Rounded feel)
        context.fill(x, y, x + getWidth(), y + getHeight(), 0xD0101010); // Darker semi-transparent
        
        // Border
        int borderColor = 0xFF5555FF; // Premium blue border
        context.drawHorizontalLine(x, x + getWidth(), y, borderColor);
        context.drawHorizontalLine(x, x + getWidth(), y + getHeight(), borderColor);
        context.drawVerticalLine(x, y, y + getHeight(), borderColor);
        context.drawVerticalLine(x + getWidth(), y, y + getHeight(), borderColor);
        
        // 2. Head/Face
        if (target instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) target;
            context.drawTexture(player.getSkinTextures().texture(), x + 6, y + 6, 38, 38, 8, 8, 8, 8, 64, 64);
        } else {
            context.fill(x + 6, y + 6, x + 44, y + 44, 0xFF333333);
        }

        // 3. Name & Info
        String name = target.getName().getString();
        context.drawText(mc.textRenderer, "§l" + name, x + 50, y + 8, 0xFFFFFF, true);
        
        // Distance
        double dist = mc.player.distanceTo(target);
        String distText = String.format("%.1fm", dist);
        context.drawText(mc.textRenderer, "§7Dist: §f" + distText, x + getWidth() - mc.textRenderer.getWidth(distText) - 55, y + 8, 0xAAAAAA, true);

        // 4. Health Bar (Premium look)
        float maxHealth = target.getMaxHealth();
        float health = target.getHealth();
        
        if (Math.abs(health - animatedHealth) > 0.05) {
            animatedHealth += (health - animatedHealth) * 0.15f;
        }
        
        float healthPerc = Math.min(1.0f, Math.max(0.0f, animatedHealth / maxHealth));
        int healthPercentage = (int) ( (health / maxHealth) * 100 );
        
        int barX = x + 50;
        int barY = y + 25;
        int barWidth = 90;
        int barHeight = 12;
        
        // Bar Background
        context.fill(barX, barY, barX + barWidth, barY + barHeight, 0x60000000);
        
        // Bar Foreground (Gradient logic)
        int color = healthPerc < 0.25 ? 0xFFFF4444 : (healthPerc < 0.6 ? 0xFFFFFF44 : 0xFF44FF44);
        context.fill(barX, barY, barX + (int)(barWidth * healthPerc), barY + barHeight, color);

        // Health Text (Centered in bar)
        String hpText = String.format("%.1f HP (%d%%)", health, healthPercentage);
        int hpTx = barX + (barWidth - mc.textRenderer.getWidth(hpText)) / 2;
        context.drawText(mc.textRenderer, hpText, hpTx, barY + 2, 0xFFFFFF, true);
        
        // 5. Target Armor Display
        int armorX = x + 50;
        int armorY = y + 40;
        int armorCount = 0;
        
        if (target instanceof PlayerEntity) {
            PlayerEntity targetPlayer = (PlayerEntity) target;
            for (int i = 3; i >= 0; i--) {
                ItemStack stack = targetPlayer.getInventory().armor.get(i);
                if (!stack.isEmpty()) {
                    context.drawItem(stack, armorX + (armorCount * 17), armorY);
                    armorCount++;
                }
            }
            // Helds
            if (!targetPlayer.getMainHandStack().isEmpty()) {
                context.drawItem(targetPlayer.getMainHandStack(), armorX + (armorCount * 17), armorY);
                armorCount++;
            }
        }
        
        setWidth(155);
        setHeight(62);

        // 6. Status Text (Winning/Losing)
        String status = "§eEven";
        if (health < mc.player.getHealth() - 2) status = "§aWinning";
        else if (health > mc.player.getHealth() + 2) status = "§cLosing";
        
        context.drawText(mc.textRenderer, status, x + getWidth() - mc.textRenderer.getWidth(status) - 5, y + 48, 0xFFFFFF, true);
    }
}
