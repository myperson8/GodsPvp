package com.myperson8.godspvp.client.modules;

import com.myperson8.godspvp.client.Keybinds;
import com.myperson8.godspvp.client.Module;
import com.myperson8.godspvp.client.ChatUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class MinimapModule implements Module {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private boolean big = false;
    private int smallX = 8;
    private int smallY = 8;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;
    private boolean dragging = false;

    @Override
    public void init() {
        // register a key (M) for toggling
        Keybinds.MINIMAP_TOGGLE = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.godspvp.minimap_toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                "category.godspvp.keys"
        ));
    }

    @Override
    public void tick() {
        if (client.player == null) return;
        if (Keybinds.MINIMAP_TOGGLE != null && Keybinds.MINIMAP_TOGGLE.wasPressed()) {
            big = !big;
            dragging = false;
        }
    }

    @Override
    public void renderHud(DrawContext context, net.minecraft.client.render.RenderTickCounter tickCounter) {
        if (client.player == null) return;

        int chatH = ChatUtils.getChatHeight(client, client.textRenderer);
        if (!big) {
            // small circular minimap top-left by default
            int size = 72;
            int cx = smallX + size/2;
            int cy = smallY + size/2;

            // background circle (approx with square)
            context.fill(smallX, smallY, smallX + size, smallY + size, 0x88000000);

            // draw player center dot
            int px = cx;
            int py = cy;
            context.fill(px-2, py-2, px+2, py+2, 0xFFFFFF00);

            // coordinates below
            String coord = String.format("X: %.0f Y: %.0f Z: %.0f", client.player.getX(), client.player.getY(), client.player.getZ());
            int tx = smallX;
            int ty = smallY + size + 2;
            int screenH = client.getWindow().getScaledHeight();
            if (ty + client.textRenderer.fontHeight > screenH - chatH) ty = screenH - chatH - client.textRenderer.fontHeight - 4;
            context.drawTextWithShadow(client.textRenderer, Text.literal(coord), tx, ty, 0xFFFFFFFF);
        } else {
            // big map centered draggable
            int w = Math.min(300, client.getWindow().getScaledWidth() - 40);
            int h = Math.min(200, client.getWindow().getScaledHeight() - 80 - chatH);
            int x = (client.getWindow().getScaledWidth() - w) / 2 + dragOffsetX;
            int y = (client.getWindow().getScaledHeight() - h) / 2 + dragOffsetY;

            // background
            context.fill(x, y, x + w, y + h, 0xAA000000);

            // simple world view placeholder: grid representing loaded chunks
            int cellW = Math.max(6, w / 16);
            int cellH = Math.max(6, h / 8);
            for (int gx = 0; gx < 16; gx++) {
                for (int gy = 0; gy < 8; gy++) {
                    int col = ((gx + gy) % 2 == 0) ? 0xFF336633 : 0xFF2E5B3A;
                    int sx = x + gx * cellW;
                    int sy = y + gy * cellH;
                    context.fill(sx, sy, Math.min(x + w, sx + cellW) - 1, Math.min(y + h, sy + cellH) - 1, col);
                }
            }

            // player marker in center
            int px = x + w/2;
            int py = y + h/2;
            context.fill(px-3, py-3, px+3, py+3, 0xFFFFFF00);

            String coord = String.format("X: %.0f Y: %.0f Z: %.0f", client.player.getX(), client.player.getY(), client.player.getZ());
            context.drawTextWithShadow(client.textRenderer, Text.literal(coord), x + 6, y + h + 6, 0xFFFFFFFF);

            // handle dragging with mouse if pressed inside big map
            try {
                if (client.currentScreen == null) {
                    long window = client.getWindow().getHandle();
                    double[] posX = new double[1];
                    double[] posY = new double[1];
                    org.lwjgl.glfw.GLFW.glfwGetCursorPos(window, posX, posY);
                    double mx = posX[0];
                    double my = posY[0];
                    boolean inside = mx >= x && mx <= x + w && my >= y && my <= y + h;
                    int left = org.lwjgl.glfw.GLFW.glfwGetMouseButton(window, org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT);
                    if (inside && left == org.lwjgl.glfw.GLFW.GLFW_PRESS && !dragging) {
                        dragging = true;
                    }
                    if (dragging && left != org.lwjgl.glfw.GLFW.GLFW_PRESS) {
                        dragging = false;
                    }
                    if (dragging) {
                        // track cursor deltas using previous position stored in dragOffset placeholders
                        // we approximate by moving map towards current cursor relative to center
                        dragOffsetX += (int)(mx - (x + w/2));
                        dragOffsetY += (int)(my - (y + h/2));
                    }
                }
            } catch (Exception ignored) {}
        }
    }
}
