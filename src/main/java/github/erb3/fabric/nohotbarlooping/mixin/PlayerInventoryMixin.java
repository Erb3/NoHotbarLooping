package github.erb3.fabric.nohotbarlooping.mixin;

import github.erb3.fabric.nohotbarlooping.NoHotbarLooping;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Shadow
    public int selectedSlot;

    @Shadow
    @Final
    private static int HOTBAR_SIZE;

    @Inject(method="scrollInHotbar", at = @At("HEAD"), cancellable = true)
    public void onScrollInHotbar(double scrollAmount, CallbackInfo ci) {
        if (NoHotbarLooping.shouldLoopHotbar) {
            return;
        }

        int newSlot = this.selectedSlot - (int) Math.signum(scrollAmount);
        if (newSlot < 0 || newSlot >= HOTBAR_SIZE) {
            ci.cancel();
        }
    }
}
