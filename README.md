# GodsPVP

GodsPVP is a client-side quality-of-life mod for Minecraft 1.21.4 (Fabric). It provides unobtrusive HUD utilities and tooling to improve awareness and building workflows without affecting server state or gameplay rules.

Key features

- Clean HUD widgets that avoid overlapping with chat.
- Configurable minimap (small circular map by default, press `M` to toggle a larger draggable map).
- Block reach indicator, context-aware crosshair label, low-durability alerts, and a radial hotbar selector.

Installation

1. Build the mod:

```bash
./gradlew build
```

2. Install:

- Place the generated jar from `build/libs/` into your `%minecraft%/mods` folder.
- Launch Minecraft with Fabric Loader and Fabric API (targeting 1.21.4).

Usage

- Toggle minimap size: press `M`. The large map is draggable — click and drag inside the large map to reposition it.
- Open hotbar radial wheel: press and hold `R`.

Behavior and safety

- All data (waypoints, settings) is stored locally. The mod never modifies server-side behavior nor automates actions.
- HUD elements detect the chat area and automatically shift to remain visible when chat is expanded.

Customization & development

- Source modules are located under `src/client/java/com/myperson8/godspvp/client/modules/`.
- Implement additional modules by following the `Module` interface and register them in `ModuleManager`.

More information

See `CLIENT_FEATURES_README.md` for notes on the implemented starters and development tips.
