package com.example.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ExampleClientMixin {
    
    @Shadow protected int rightClickDelay;

    // --- ZERO-DELAY PLACEMENT (MOJANG MAPPINGS) ---
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTickUpdate(CallbackInfo ci) {
        Minecraft client = (Minecraft) (Object) this;
        
        if (client.player != null) {
            boolean holdingCrystalOrObsidian = 
                client.player.getMainHandItem().is(Items.END_CRYSTAL) || 
                client.player.getOffhandItem().is(Items.END_CRYSTAL) || // wait, let's make it look clean
                client.player.getMainHandItem().is(Items.END_CRYSTAL) || 
                client.player.getOffhandItem().is(Items.END_CRYSTAL) ||
                client.player.getMainHandItem().is(Items.OBSIDIAN) || 
                client.player.getOffhandItem().is(Items.OBSIDIAN);

            if (holdingCrystalOrObsidian) {
                this.rightClickDelay = 0;
            }
        }
    }
}
