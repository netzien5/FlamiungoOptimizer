package com.flamiungo.mixin;

import com.flamiungo.config.FlamiungoConfig;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("HEAD"), cancellable = true)
    private void onAddParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> cir) {
        if (!FlamiungoConfig.noExplosions) return;

        if (parameters.getType() == ParticleTypes.EXPLOSION || 
            parameters.getType() == ParticleTypes.EXPLOSION_EMITTER ||
            parameters.getType() == ParticleTypes.FLAME ||
            parameters.getType() == ParticleTypes.LAVA) {
            
            cir.setReturnValue(null);
        }
    }
}
