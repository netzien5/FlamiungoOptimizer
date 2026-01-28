package com.flamiungo.render;

import com.flamiungo.config.FlamiungoConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

import java.awt.Color;

public class ChinaHatRenderer {

    public static void register() {
        WorldRenderEvents.LAST.register(ChinaHatRenderer::render);
    }

    private static void render(WorldRenderContext context) {
        if (!FlamiungoConfig.chinaHat) return;

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.world == null) return;

        Camera camera = context.camera();
        MatrixStack matrices = context.matrixStack();

        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player.isInvisible() || player.isSpectator()) continue;
            // Don't render on self in first person usually? Or pass if desired
            if (player == mc.player && mc.options.getPerspective().isFirstPerson()) continue;

            double x = MathHelper.lerp(context.tickCounter().getTickDelta(false), player.lastRenderX, player.getX()) - camera.getPos().x;
            double y = MathHelper.lerp(context.tickCounter().getTickDelta(false), player.lastRenderY, player.getY()) - camera.getPos().y + player.getHeight() + 0.3; 
            double z = MathHelper.lerp(context.tickCounter().getTickDelta(false), player.lastRenderZ, player.getZ()) - camera.getPos().z;

            matrices.push();
            matrices.translate(x, y, z);
            
            float time = (player.age + context.tickCounter().getTickDelta(false)) * 2.0f;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(time));
            
            // Draw Main Cone
            drawCone(matrices, 0.5f, 0.25f, new Color(255, 100, 100, 180)); 
            
            // Draw Outer Rim / Line
            drawRing(matrices, 0.5f, 0.05f, new Color(255, 255, 255, 200));

            matrices.pop();
        }
    }

    private static void drawCone(MatrixStack matrices, float radius, float height, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);

        Matrix4f posMat = matrices.peek().getPositionMatrix();
        buffer.vertex(posMat, 0, height, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

        int segments = 32;
        for (int i = 0; i <= segments; i++) {
            double angle = (Math.PI * 2 * i) / segments;
            float px = (float) Math.cos(angle) * radius;
            float pz = (float) Math.sin(angle) * radius;
            buffer.vertex(posMat, px, 0, pz).color(color.getRed(), color.getGreen(), color.getBlue(), 100);
        }

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        RenderSystem.enableCull();
    }

    private static void drawRing(MatrixStack matrices, float radius, float yOff, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);

        Matrix4f posMat = matrices.peek().getPositionMatrix();
        int segments = 32;
        for (int i = 0; i <= segments; i++) {
            double angle = (Math.PI * 2 * i) / segments;
            float px = (float) Math.cos(angle) * radius;
            float pz = (float) Math.sin(angle) * radius;
            buffer.vertex(posMat, px, yOff, pz).color(color.getRed(), color.getGreen(), color.getBlue(), 255);
        }

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }
}
