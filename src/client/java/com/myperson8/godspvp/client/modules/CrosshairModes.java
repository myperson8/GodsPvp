package com.myperson8.godspvp.client.modules;

import com.myperson8.godspvp.client.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import com.myperson8.godspvp.client.ChatUtils;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class CrosshairModes implements Module {
    private final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void renderHud(DrawContext context, net.minecraft.client.render.RenderTickCounter tickCounter) {
        if (client.player == null) return;
        Item item = client.player.getMainHandStack().getItem();
        TextRenderer tr = client.textRenderer;
        String mode = "Default";
        int color = 0xFFFFFFFF;

        if (item instanceof SwordItem) {
            mode = "Melee";
            color = 0xFF66FF66;
        } else if (item instanceof BowItem) {
            mode = "Ranged";
            color = 0xFF66CCFF;
        } else {
            mode = "Build";
            color = 0xFFFFFFFF;
        }

        int x = client.getWindow().getScaledWidth() / 2 - 30;
        int y = client.getWindow().getScaledHeight() / 2 + 10;
        int chatH = ChatUtils.getChatHeight(client, tr);
        int screenH = client.getWindow().getScaledHeight();
        if (y + tr.fontHeight > screenH - chatH) {
            y = screenH - chatH - tr.fontHeight - 4;
        }
        context.drawTextWithShadow(tr, Text.literal("Crosshair: " + mode), x, y, color);
    }
}
