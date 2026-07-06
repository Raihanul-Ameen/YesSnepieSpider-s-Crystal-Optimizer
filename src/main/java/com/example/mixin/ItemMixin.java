package net.yourname.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class ItemMixin {

    @Inject(method = "doItemUse", at = @At("RETURN"))
    private void resetPlaceDelay(CallbackInfoReturnable<?> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            ItemStack mainHand = client.player.getStackInHand(Hand.MAIN_HAND);
            ItemStack offHand = client.player.getStackInHand(Hand.OFF_HAND);

            // If holding an End Crystal in either hand, override the 4-tick delay to 0
            if (mainHand.isOf(Items.END_CRYSTAL) || offHand.isOf(Items.END_CRYSTAL)) {
                // Accessing the private field via an accessor or direct shadow mapping
                ((MinecraftClientAccessor) client).setItemUseCooldown(0);
            }
        }
    }
}
