package com.flamiungo.hud;

import com.flamiungo.config.FlamiungoConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import java.util.Collection;
import java.util.List;

public class HudOverlay implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;
        if (client.options.hudHidden) return;

        // Render all enabled modules
        for (com.flamiungo.hud.module.HudModule module : com.flamiungo.hud.module.ModuleManager.getEnabledModules()) {
            module.render(context);
        }

        // Keep legacy legacy armor/potion for now or merge them?
        // Let's keep them here but check config.
        renderLegacyHud(context, client);
    }

    private void renderLegacyHud(DrawContext context, MinecraftClient client) {
        if (!FlamiungoConfig.inventoryHud) return;
        
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();
        TextRenderer tr = client.textRenderer;

        // Armor & Hands (Bottom Right)
        int x = width - 20;
        int y = height - 20;

        // Render Offhand
        ItemStack offhand = client.player.getOffHandStack();
        if (!offhand.isEmpty()) {
            context.drawItem(offhand, x, y);
            y -= 20;
        }

        // Render Armor
        for (ItemStack stack : client.player.getInventory().armor) {
            if (stack.isEmpty()) continue;
            context.drawItem(stack, x, y);

            if (stack.isDamageable()) {
                int max = stack.getMaxDamage();
                int current = stack.getDamage();
                int remaining = max - current;
                
                String durText = String.valueOf(remaining);
                int color = remaining < max * 0.1 ? 0xFFFF5555 : (remaining < max * 0.4 ? 0xFFFFFF55 : 0xFFFFFFFF);
                
                // Draw numeric durability centered or next to icon
                context.drawText(tr, durText, x - tr.getWidth(durText) - 4, y + 4, color, true);
            }
            y -= 20;
        }

        // Render Main Hand
        ItemStack mainHand = client.player.getMainHandStack();
        if (!mainHand.isEmpty()) {
            context.drawItem(mainHand, x, y);
             y -= 20;
        }

        // Totem Counter (Above XP bar)
        int totemCount = 0;
        for (int i = 0; i < client.player.getInventory().size(); i++) {
            if (client.player.getInventory().getStack(i).getItem() == Items.TOTEM_OF_UNDYING) {
                totemCount += client.player.getInventory().getStack(i).getCount();
            }
        }
        
        if (totemCount > 0) {
           int tx = width / 2 - 8;
           int ty = height - 60; // Slightly higher
           ItemStack totemStack = new ItemStack(Items.TOTEM_OF_UNDYING);
           context.drawItem(totemStack, tx, ty);
           
           String countStr = "§6" + totemCount;
           context.drawText(tr, countStr, tx + 18, ty + 5, 0xFFFFFF, true);
        }

        // Potion Effects (Top Right)
        java.util.Collection<StatusEffectInstance> effects = client.player.getStatusEffects();
        int py = 10;
        for (StatusEffectInstance effect : effects) {
             Text name = effect.getEffectType().value().getName();
             String duration = StatusEffectUtil.getDurationText(effect, 1.0F, client.world.getTickManager().getTickRate()).getString();
             String text = name.getString() + " (" + duration + ")";
             
             int px = width - tr.getWidth(text) - 5;
             context.drawText(tr, text, px, py, 0xFFFFFF, true);
             py += 12;
        }
    }
}
