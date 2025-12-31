# GodsPvp — Client-side QoL Mod (Fabric, Minecraft 1.21.4)

This mod provides client-side quality-of-life HUD and utility features for Minecraft (Fabric, 1.21.4). All features are client-only and do not modify server behavior or send custom packets.

## Quick Tutorial

1. Build the mod

```bash
./gradlew build
```

The generated jar will be in `build/libs/`.

2. Install (single-player or client mods folder)

- Drop the generated jar into your `%minecraft%/mods` folder (or `~/.minecraft/mods`).
- Ensure you run Minecraft with Fabric Loader and Fabric API for 1.21.4.

3. Run in development (IDE)

- Use the provided Gradle run configurations or run the `runClient` Gradle task from your IDE.

4. Default keybinds

- Open Hotbar Radial Wheel: `R` (hold) — shows a simple radial selector for hotbar slots.
- Pin chat message: `P` — (placeholder) key registered; future feature will allow right-click pinning.

5. Included starter features

- Block Reach Indicator — shows reach distance on HUD (green when within reach, red when too far).
- Hotbar Radial Wheel — hold `R` to preview a radial selection of hotbar slots.
- Durability Alerts — plays a sound when main-hand item durability drops below 15%.
- Crosshair Modes — HUD indicator that changes based on held item (melee, ranged, build).

6. Where to extend

- The client modules are in `src/client/java/com/myperson8/godspvp/client/modules/`.
- Add features by implementing the `Module` interface and registering in `ModuleManager`.

7. Privacy & safety

- All bookmarks, waypoints, and settings are stored locally. The mod does not perform automated gameplay actions or provide any server-side advantages.

---

For developer notes and advanced features, see `CLIENT_FEATURES_README.md`.
