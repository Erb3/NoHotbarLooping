package github.erb3.fabric.nohotbarlooping.mixin;

import github.erb3.fabric.nohotbarlooping.NoHotbarLooping;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.MathHelper;
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
        inv.selectedSlot = MathHelper.clamp(
                inv.selectedSlot - (int) Math.signum(scrollAmount), 0, 8);

        ci.cancel();
    }
}
