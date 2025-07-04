# MappingRegister API

This API provides registration methods for customizing how experience points (XP) are converted into score or currency within the runtime. It supports both global mappings and conditional mappings based on the current stack trace.

## API Reference

### MappingRegister

Provides registration methods to define how XP is converted to score or currency.

#### `registerXpToScoreMapping(Int2IntFunction map)`
Registers a global function that maps XP to score. This will override the default 1:1 mapping.

#### `registerXpToScoreMapping(Predicate<StackTraceElement[]> stack, Int2IntFunction map)`
Registers a context-specific XP-to-score mapping. If the `stack` predicate evaluates to `true`, the given `map` function will be used.

#### `registerXpToCurrencyMapping(Int2IntFunction map)`
Registers a global function that maps XP to currency.

#### `registerXpToCurrencyMapping(Predicate<StackTraceElement[]> stack, Int2IntFunction map)`
Registers a context-specific XP-to-currency mapping. Similar to the score version, this applies only when the predicate passes.

---

### ExpHelper

Utility class for low-level experience and rune operations.

#### `giveLevelExp(Player player, int xpPoints)`
Grants XP to the player and increases their score.
Also calls `giveLevelExpWithoutScore` to handle level progress.

#### `giveLevelExpWithoutScore(Player player, int xpPoints)`
Manually adjusts experience progress and level based on XP points.
Does not affect player score.

#### `givePlayerLevels(Player player, int levels)`
Grants or removes XP levels:
- Positive values add levels using vanilla behavior.
- Negative values reduce level manually and clamp if below zero.

#### `giveCurrencyExp(Player player, int rune)`
Gives or consumes rune (custom currency). Triggers `RuneEvent.Give` which may be modified or canceled by other systems.
- Positive value: rune is added.
- Negative value: rune is deducted.

#### `transformLevelToRuneCost(int level)`
Converts XP levels to rune cost based on vanilla XP curve.
Handles negative levels symmetrically.

---

### RuneEvent Handling

#### `registerRuneEvents(Consumer<RuneEvent> event)`
Registers event handler methods that will be invoked when `ExpHelper.giveCurrencyExp(Player, int)` is called.

- Allows injection or modification of rune events.
- Enables integration with other mods that hook into rune transactions.

---

### Notes

- Stack trace-based registration enables fine-grained control over XP usage scenarios.
- XP-to-score mappings affect level gain (e.g., quests).
- XP-to-currency mappings govern what is spendable (e.g., enchanting, anvil).
- Rune provides a modular interface for spendable XP separation.

---

This API is part of the DualExp mod and is intended to support modular, extensible XP/rune management across modded Minecraft environments.

