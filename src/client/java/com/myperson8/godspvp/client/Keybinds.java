package com.myperson8.godspvp.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class Keybinds {
    public static KeyBinding OPEN_HOTBAR_WHEEL;
    public static KeyBinding PIN_CHAT_MESSAGE;

    public static void register() {
        OPEN_HOTBAR_WHEEL = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.godspvp.hotbar_wheel",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.godspvp.keys"
        ));

        PIN_CHAT_MESSAGE = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.godspvp.pin_chat",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P,
                "category.godspvp.keys"
        ));
    }
}
