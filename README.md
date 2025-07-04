# DualExp: Split XP roles: level vs currency

DualExp is a Minecraft mod that separates the traditional XP system into two distinct roles: Level, used for gating access to features like enchanting, and Currency, used for spending experience on actions.
This decoupling allows more precise control over progression mechanics and paves the way for modpacks or datapacks to define their own experience usage models.

By default, XP orbs contribute to both roles simultaneously, but mod developers and pack creators can fully customize how experience is earned, stored, and consumed. DualExp provides a lightweight yet extensible API for accessing, syncing, and manipulating both XP roles independently.

Ideal for RPG systems, custom economies, or any mod that wants to treat XP as more than just a single pool.

*Still updating*

## Design Philosophy

Separate the leveling use of experience points (XP) from their spendable use.
A new attribute is independently attached to the player: I call it **rune** (a symbolic or mystical unit). In this document, we’ll refer to it simply as "rune."

This mod is particularly suited for modpacks where many mods interact with XP mechanics at the player level. Therefore, the mod intercepts vanilla XP behavior at a relatively low level:

- Vanilla methods for gaining and consuming XP are fully overridden to instead modify the player's rune value. This is because many mods already use raw XP costs instead of level deductions (e.g., Apotheosis).
- XP level **increases** are preserved, since they’re usually awarded directly (e.g., as quest rewards).
- XP level **decreases** are redirected to **rune** reductions instead, to preserve consistency with vanilla and mod usage where XP loss represents cost.
- Experience orbs provide both XP (for level) and rune (for spendable use).
- The `/xp` command only affects XP level; rune is managed through a separate command `/rune` (configurable).

## Features

### Core Mechanics

This mod intercepts vanilla XP behavior using [Mixin](https://github.com/SpongePowered/Mixin):

- XP gain/consumption is fully rerouted to rune gain/loss.
- XP level increases remain vanilla; level reductions are translated to rune loss.
- Experience orbs grant both XP and rune.
- `/xp` affects only XP; a new `/rune` command (renameable) manages rune.

### Secondary Mechanics

- A constant (or in future: a mapping table) determines how XP translates to rune values.
- A simple client-side HUD displays rune information.
    - Customizable via config options.
- Rune and XP behavior on death respects the `keepInventory` rule.

