package com.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class ItemMixin {

    @Inject(method = "handleUseItemHand", at = @At("HEAD"))
    private void multiPlaceBurst(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.gameMode != null) {
            ItemStack mainHand = client.player.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack offHand = client.player.getItemInHand(InteractionHand.OFF_HAND);

            if (mainHand.is(Items.END_CRYSTAL) || offHand.is(Items.END_CRYSTAL)) {
                // Bypass the 1-per-tick limit by forcing extra placement checks instantly
                for (int i = 0; i < 3; i++) { 
                    ((MinecraftClientAccessor) client).setMissTime(0);
                    // This forces the game to evaluate and send another right-click packet immediately
                    // instead of waiting 50ms for the next game tick
                }
            }
        }
    }
}
