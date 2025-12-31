Client-side helper mod (starter)

This workspace adds a client-only feature set for Minecraft 1.21.4 (Fabric).

Included starter modules (client-side only):
- Block Reach Indicator (HUD distance, green/red)
- Hotbar Radial Wheel (hold key to display radial selector)
- Durability Alerts (plays a sound at low durability)
- Crosshair Modes (HUD text that shows current crosshair mode)

How to build

Run in the devcontainer or locally with Gradle wrapper:

```bash
./gradlew build
```

Run in Minecraft (IDE or run configurations): use existing Fabric run configs in the project.

Notes
- All features are client-side and do not send custom packets beyond normal vanilla behavior.
- The modules are starters and intended as architecture and working examples to extend.
