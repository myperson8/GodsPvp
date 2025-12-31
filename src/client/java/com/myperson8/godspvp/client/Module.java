package com.myperson8.godspvp.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

/**
 * Simple feature module interface.
 */
public interface Module {
    default void init() {}
    default void tick() {}
    default void renderHud(DrawContext drawContext, RenderTickCounter tickCounter) {}
    default void onClose() {}
}
