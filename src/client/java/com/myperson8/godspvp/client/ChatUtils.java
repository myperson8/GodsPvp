package com.myperson8.godspvp.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public final class ChatUtils {
    private ChatUtils() {}

    /**
     * Estimate the current chat area height in screen pixels.
     * Uses reflection to work across mappings changes.
     */
    public static int getChatHeight(MinecraftClient client, TextRenderer tr) {
        if (client == null || client.inGameHud == null || tr == null) return 0;

        try {
            Object chatHud = null;
            // try getChatHud() method
            try {
                Method m = client.inGameHud.getClass().getMethod("getChatHud");
                chatHud = m.invoke(client.inGameHud);
            } catch (NoSuchMethodException ignored) {
                // fallback to field
                try {
                    Field f = client.inGameHud.getClass().getDeclaredField("chatHud");
                    f.setAccessible(true);
                    chatHud = f.get(client.inGameHud);
                } catch (NoSuchFieldException ignored2) {
                    // give up
                }
            }

            if (chatHud == null) return 0;

            // try methods that return visible messages or height
            try {
                Method getVisible = chatHud.getClass().getMethod("getVisibleMessages");
                Object list = getVisible.invoke(chatHud);
                if (list instanceof List) {
                    int count = ((List<?>) list).size();
                    int lineHeight = tr.fontHeight + 2;
                    return Math.max(0, count * lineHeight);
                }
            } catch (Exception ignored) {}

            try {
                Method getHeight = chatHud.getClass().getMethod("getHeight");
                Object h = getHeight.invoke(chatHud);
                if (h instanceof Integer) return (Integer) h;
            } catch (Exception ignored) {}

        } catch (Exception e) {
            // ignore and return 0
        }

        return 0;
    }
}
