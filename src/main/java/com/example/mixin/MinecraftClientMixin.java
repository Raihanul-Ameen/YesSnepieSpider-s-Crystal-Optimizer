package com.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.world.entity.decoration.EndCrystal;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow public net.minecraft.world.phys.HitResult hitResult;
    @Shadow private int missTime;

    @Inject(method = "tick", at = @At("HEAD"))
    private void multiAttackBurst(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.gameMode != null) {
            // Check if looking at a crystal
            if (hitResult instanceof EntityHitResult entityHit && entityHit.getEntity() instanceof EndCrystal) {
                this.missTime = 0;
                
                // If holding left-click, spam extra attack packets directly to the server 
                // to break the crystal without waiting for the next client swing animation
                if (client.options.keyAttack.isDown()) {
                    client.gameMode.attack(client.player, entityHit.getEntity());
                    client.player.swing(InteractionHand.MAIN_HAND);
                }
            }
        }
    }
}
