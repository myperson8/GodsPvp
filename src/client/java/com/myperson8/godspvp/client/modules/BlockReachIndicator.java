package com.myperson8.godspvp.client.modules;

import com.myperson8.godspvp.client.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import com.myperson8.godspvp.client.ChatUtils;
import net.minecraft.util.hit.HitResult;

@Environment(EnvType.CLIENT)
public class BlockReachIndicator implements Module {
    private final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void renderHud(DrawContext context, net.minecraft.client.render.RenderTickCounter tickCounter) {
        if (client.player == null || client.world == null) return;

        HitResult target = client.crosshairTarget;
        if (target == null) return;

        TextRenderer tr = client.textRenderer;
        if (target.getType() == HitResult.Type.BLOCK) {
            double dx = target.getPos().x - client.player.getX();
            double dy = target.getPos().y - client.player.getY();
            double dz = target.getPos().z - client.player.getZ();
            double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);

            // Use a safe client-side reach estimate (client reach can vary). This is purely UI.
            double reachThreshold = 6.0;
            boolean reachable = dist <= reachThreshold;
            int color = reachable ? 0xFF66FF66 : 0xFFFF6666; // green / red

            String s = String.format("Reach: %.2f", dist);
            int x = client.getWindow().getScaledWidth() / 2 + 10;
            int y = client.getWindow().getScaledHeight() / 2 - 8;
            // avoid chat overlap: if chat area overlaps our HUD position, shift upward
            int chatH = ChatUtils.getChatHeight(client, tr);
            int screenH = client.getWindow().getScaledHeight();
            if (y + tr.fontHeight > screenH - chatH) {
                y = screenH - chatH - tr.fontHeight - 4;
            }
            context.drawTextWithShadow(tr, Text.literal(s), x, y, color);
        }
    }
}
