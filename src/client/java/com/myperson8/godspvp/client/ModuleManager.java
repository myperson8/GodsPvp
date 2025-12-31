package com.myperson8.godspvp.client;

import com.myperson8.godspvp.client.modules.BlockReachIndicator;
import com.myperson8.godspvp.client.modules.CrosshairModes;
import com.myperson8.godspvp.client.modules.DurabilityAlerts;
import com.myperson8.godspvp.client.modules.HotbarRadialWheel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ModuleManager {
    private static final List<Module> MODULES = new ArrayList<>();

    public static void init() {
        // register modules here
        register(new BlockReachIndicator());
        register(new HotbarRadialWheel());
        register(new DurabilityAlerts());
        register(new CrosshairModes());

        // initialize modules
        MODULES.forEach(Module::init);

        // client tick
        ClientTickEvents.END_CLIENT_TICK.register(client -> MODULES.forEach(Module::tick));

        // HUD render
        HudRenderCallback.EVENT.register((context, tickCounter) -> MODULES.forEach(m -> m.renderHud(context, tickCounter)));
    }

    private static void register(Module m) {
        MODULES.add(m);
    }
}
