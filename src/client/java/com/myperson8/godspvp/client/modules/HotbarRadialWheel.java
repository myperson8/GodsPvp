package com.myperson8.godspvp.client.modules;

import com.myperson8.godspvp.client.Keybinds;
import com.myperson8.godspvp.client.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

@Environment(EnvType.CLIENT)
public class HotbarRadialWheel implements Module {
    private final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void init() {
        Keybinds.register();
    }

    @Override
    public void renderHud(DrawContext context, net.minecraft.client.render.RenderTickCounter tickCounter) {
        if (client.player == null) return;

        if (Keybinds.OPEN_HOTBAR_WHEEL.wasPressed() || Keybinds.OPEN_HOTBAR_WHEEL.isPressed()) {
            int w = client.getWindow().getScaledWidth();
            int h = client.getWindow().getScaledHeight();
            int cx = w / 2;
            int cy = h / 2;

            // background ring (approx)
            context.fill(cx - 48, cy - 48, cx + 48, cy + 48, 0x88000000);

            // draw 9 segments as small boxes around center
            for (int i = 0; i < 9; i++) {
                double angle = Math.toRadians((i / 9.0) * 360 - 90);
                int tx = cx + (int)(Math.cos(angle) * 36) - 10;
                int ty = cy + (int)(Math.sin(angle) * 36) - 10;
                int color = (client.player.getInventory().selectedSlot == i) ? 0xFFAAAA00 : 0xFFFFFFFF;
                context.fill(tx, ty, tx + 20, ty + 20, color);
            }
        }
    }
}
