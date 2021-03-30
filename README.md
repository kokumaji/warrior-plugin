![Warrior Plugin Logo](../assets/assets/warrior_logo.png?raw=true "Warrior Logo")
---

## About this Project
**Warrior** is a fully customizable, class-based competitive FFA plugin. Its easy
to understand commands & integrated script system allows you to add/remove content as you wish!

## Features
- Fast & simple arena creation
- Advanced skill system
- Killstreak based rewards
- Quest & Achievement system
- Custom chat manager
- Easy to use placeholders
- 50+ **different** weapons
- 8 preconfigured classes

## Commands

### Arena Command

| Command | Permission | Description |
| ------- | ---------- | ----------- |
| `/arena create <Arena>` | `warrior.arena.create` | Starts the Arena Builder Session |
| `/arena flags <Arena>` | `warrior.flags.list` | Lists all flags set for this arena |
| `/arena join <Arena>` | `warrior.arena.join` | Teleports the player to an arena |
| `/arena leave` | `warrior.arena.leave` | Teleports the player back to the lobby |
| `/arena rate <Arena> <1-5>` | `warrior.arena.rate` | Allows the player to review an arena |
| `/arena remove <Arena>` | `warrior.arena.remove` | Deletes an arena from disk |
| `/arena setup <pos1, pos2, spawn, confirm, cancel>` | `warrior.arena.setup` | Allows for command-based interaction within the arena setup process |
| `/arena spectate <Arena>` | `warrior.arena.spectate` | Teleports the player to an arena with spectator mode enabled. |

### Kit Command (WIP)

**NOTE** This command is experimental and should not be used in its current state, unless you know what you're doing.

| Command | Permission | Description |
| ------- | ---------- | ----------- |
| `/kit create <Name>` | `warrior.command.kit` | **Experimental** Creates a custom kit from the players inventory. <br> <br>(Creates a JSON file at `data/kits/<name>.json` for further customization) |

### Warrior Command

| Command | Permission | Description |
| ------- | ---------- | ----------- |
| `/warrior about` | `warrior.command.admin` | Displays information about the plugin |
| `/warrior help` | `warrior.command.admin` | Displays useful commands & instructions |
| `/warrior hologram <stats, leaderboard>` | *TBA*  | *TBA* |
| `/warrior setlobby` | `warrior.command.setlobby`  | Sets the lobby spawn to the players position (`lobby-settings.custom-spawn` needs to be set to `true` in the plugin config) |
| `/warrior reload <language, config>` | `warrior.command.reload`  | Reloads the plugin config and/or language files |
