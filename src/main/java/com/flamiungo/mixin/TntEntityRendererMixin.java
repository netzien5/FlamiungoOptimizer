package com.flamiungo.mixin;

import com.flamiungo.config.FlamiungoConfig;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.TntEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.TntEntity;
import net.minecraft.text.Text;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TntEntityRenderer.class)
public abstract class TntEntityRendererMixin extends EntityRenderer<TntEntity> {

    protected TntEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void onRender(TntEntity tntEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (!FlamiungoConfig.tntTimer) return;

        double distance = this.dispatcher.getSquaredDistanceToCamera(tntEntity);
        if (distance > 4096.0) return; // Don't render if too far

        String time = String.format("%.2f", tntEntity.getFuse() / 20.0f);
        
        matrixStack.push();
        matrixStack.translate(0.0f, 1.5f, 0.0f); // Move above the TNT
        matrixStack.multiply(this.dispatcher.getRotation());
        matrixStack.scale(-0.025f, -0.025f, 0.025f);
        
        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
        float opacity = 0.25f;
        int color = 0xFFFFFFFF;
        
        // Dynamic color based on fuse time
        int fuse = tntEntity.getFuse();
        if (fuse < 20) color = 0xFFFF0000; // Red if < 1s
        else if (fuse < 40) color = 0xFFFFFF00; // Yellow if < 2s
        else color = 0xFF00FF00; // Green otherwise

        TextRenderer tr = this.getTextRenderer();
        float x = -tr.getWidth(time) / 2.0f;
        
        tr.draw(time, x, 0, color, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.SEE_THROUGH, 0, i);
        tr.draw(time, x, 0, color, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL, 0, i);
        
        matrixStack.pop();
    }
}
