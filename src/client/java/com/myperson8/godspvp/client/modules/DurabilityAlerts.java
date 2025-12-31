package com.myperson8.godspvp.client.modules;

import com.myperson8.godspvp.client.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;

@Environment(EnvType.CLIENT)
public class DurabilityAlerts implements Module {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private boolean alerted = false;
    private final double THRESHOLD = 0.15; // 15%

    @Override
    public void tick() {
        if (client.player == null) return;

        boolean low = false;
        // check hotbar + armor (simple check)
        for (int i = 0; i < client.player.getInventory().size(); i++) {
            if (i >= client.player.getInventory().size()) break;
            if (i >= 36 && i <= 39) continue; // skip non-armor slots by way of safety
        }

        // Check main hand and armor
        if (client.player.getMainHandStack() != null && !client.player.getMainHandStack().isEmpty()) {
            var stack = client.player.getMainHandStack();
            if (stack.isDamageable()) {
                double pct = (double)(stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage();
                if (pct <= THRESHOLD) low = true;
            }
        }

        if (!alerted && low) {
            alerted = true;
            client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.BLOCK_ANVIL_LAND, 1.0F));
        }

        if (alerted && !low) alerted = false;
    }
}
