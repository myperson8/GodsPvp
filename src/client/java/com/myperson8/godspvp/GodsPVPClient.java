package com.myperson8.godspvp;

import net.fabricmc.api.ClientModInitializer;
import com.myperson8.godspvp.client.ModuleManager;

public class GodsPVPClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Initialize client-side modules and keybinds
		ModuleManager.init();
	}
}