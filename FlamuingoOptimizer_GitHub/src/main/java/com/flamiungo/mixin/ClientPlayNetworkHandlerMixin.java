package com.flamiungo.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    
    @Inject(method = "onEntitiesDestroy", at = @At("HEAD"))
    private void onEntitiesDestroy(EntitiesDestroyS2CPacket packet, CallbackInfo ci) {
        // This mixin handles the server confirmation of entity destruction.
        // Since FlamiungoOptimizer instantly removes crystals client-side,
        // this packet serves as a synchronization point.
        // The optimization here is implicit: we don't need to wait for this to hide the crystal.
    }
}
