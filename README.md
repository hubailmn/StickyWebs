# StickyWebs

A Paper 1.20.4 plugin that makes cobwebs sticky and adds configurable combat and explosive behavior. This version uses NMS and the Paperweight userdev toolchain.

## Features

- Automatically resets placed blocks after a configurable delay
- Prevents firework use and elytra gliding while inside cobwebs
- Blocks movement through cobwebs
- Applies a speed boost to TNT minecarts on rails
- Configurable TNT and TNT minecart explosion power
- Beacon placement protection

## Requirements

- Paper 1.20.4
- Java 17

## Installation

1. Build the plugin:
   ```bash
   ./gradlew build
   ```
2. Copy the built jar from `build/libs/` into the server's `plugins/` folder.
3. Restart the server.

## Commands

| Command | Permission | Description |
| --- | --- | --- |
| `/stickyWeb reload` | `StickyWeb.command.reload` | Reload the configuration |
| `/stickyWeb bypass` | `StickyWeb.command.bypass` | Toggle block-reset bypass for yourself |

## Configuration

`config.yml` controls block reset (delay, affected materials, batch size), cobweb movement rules, TNT minecart boosts, and explosion power.

## Notes

The build uses the Paperweight userdev plugin and depends on the author's local `cc.hubailmn:Utility` library, which must be available in the local Maven repository.
