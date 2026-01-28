package com.flamiungo.mixin;

import com.flamiungo.config.FlamiungoConfig;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;"))
    private Object forceGamma(SimpleOption<Double> instance) {
        if (FlamiungoConfig.fullbright) {
            return 10.0; // Force high gamma
        }
        return instance.getValue();
    }
}
