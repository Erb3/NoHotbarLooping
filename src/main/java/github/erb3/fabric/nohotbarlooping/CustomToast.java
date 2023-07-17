package github.erb3.fabric.nohotbarlooping;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class CustomToast implements Toast {

    // Inspired by Meteor-client/MeteorToast

    public static final int titleColor = 0xFFFFFF;
    public static final int textColor = 0xD3D3D3;

    private final ItemStack icon;
    private final Text title;
    private final Text text;
    private final long duration;
    private boolean timeStarted = false;
    private long start;

    public CustomToast(@NotNull Item item, @NotNull String title, @NotNull String text) {
        this.icon = item.getDefaultStack();
        this.title = Text.literal(title).setStyle(Style.EMPTY.withColor(titleColor));
        this.text = Text.literal(text).setStyle(Style.EMPTY.withColor(textColor));
        this.duration = 4500;
    }

    @Override
    public Visibility draw(DrawContext context, ToastManager manager, long currentTime) {
        if (!timeStarted) {
            start = currentTime;
            timeStarted = true;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        context.drawTexture(TEXTURE, 0, 0, 0, 0, getWidth(), getHeight());

        int x = 28;
        MinecraftClient mc = MinecraftClient.getInstance();

        context.drawText(mc.textRenderer, title, x, 7, titleColor, false);
        context.drawText(mc.textRenderer, text, x, 18, textColor, false);
        context.drawItem(icon, 8, 8);

        return currentTime - start >= duration ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }
}
