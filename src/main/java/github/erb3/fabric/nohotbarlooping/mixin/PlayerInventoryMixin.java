package github.erb3.fabric.nohotbarlooping.mixin;

import github.erb3.fabric.nohotbarlooping.NoHotbarLooping;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Inject(method="scrollInHotbar", at = @At("HEAD"), cancellable = true)
    public void onScrollInHotbar(double scrollAmount, CallbackInfo ci) {
        if (NoHotbarLooping.shouldLoopHotbar) {
            return;
        }

        assert NoHotbarLooping.client.player != null;
        PlayerInventory inv = NoHotbarLooping.client.player.getInventory();
        int newSlot = inv.selectedSlot - (int) Math.signum(scrollAmount);

        while (newSlot < 0) {
            newSlot += 1;
        }

        while (newSlot >= 9) {
            newSlot -= 1;
        }

        inv.selectedSlot = newSlot;
        ci.cancel();
    }
}
