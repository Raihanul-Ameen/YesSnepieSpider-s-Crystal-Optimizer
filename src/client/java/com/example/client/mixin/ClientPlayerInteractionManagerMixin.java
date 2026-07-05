package com.example.client.mixin;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiPlayerGameMode.class)
public class ClientPlayerInteractionManagerMixin {

    // --- ZERO-DELAY BREAKING (MOJANG MAPPINGS) ---
    @Inject(method = "attack", at = @At("HEAD"))
    private void onCrystalAttack(Player player, Entity target, CallbackInfo ci) {
        if (target instanceof EndCrystal crystal) {
            crystal.discard();

            if (player.level() != null) {
                player.level().playSound(
                    player, 
                    crystal.getX(), crystal.getY(), crystal.getZ(), 
                    SoundEvents.GENERIC_EXPLODE, 
                    SoundSource.BLOCKS, 
                    4.0F, 
                    (1.0F + (player.level().getRandom().nextFloat() - player.level().getRandom().nextFloat()) * 0.2F) * 0.7F
                );
            }
        }
    }
}
