package com.flamiungo.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "attackEntity", at = @At("HEAD"))
    private void onAttackEntityReach(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (player.getWorld().isClient) {
            net.minecraft.client.MinecraftClient mc = net.minecraft.client.MinecraftClient.getInstance();
            double dist = 0;
            if (mc.crosshairTarget != null && mc.crosshairTarget.getType() == net.minecraft.util.hit.HitResult.Type.ENTITY) {
                 dist = player.getEyePos().distanceTo(mc.crosshairTarget.getPos());
            } else {
                 dist = player.distanceTo(target);
            }
            com.flamiungo.hud.module.ReachDisplayModule.updateReach(dist);
        }
    }

    @Inject(method = "attackEntity", at = @At("TAIL"))
    private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (target instanceof EndCrystalEntity) {
            // Instant remove client-side to bypass server latency
            // This makes the crystal disappear immediately when hit
            target.kill();
            target.setRemoved(Entity.RemovalReason.KILLED);
        }
    }
}
