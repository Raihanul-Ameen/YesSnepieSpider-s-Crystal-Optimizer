package net.yourname.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow public net.minecraft.util.hit.HitResult crosshairTarget;
    @Shadow private int itemUseCooldown;

    @Inject(method = "tick", at = @At("HEAD"))
    private void removeCrystalDelays(CallbackInfo ci) {
        // Zero out the block/item interaction delay if holding/using onto crystals
        if (crosshairTarget instanceof EntityHitResult entityHit && entityHit.getEntity() instanceof EndCrystalEntity) {
            this.itemUseCooldown = 0;
        }
    }
}
