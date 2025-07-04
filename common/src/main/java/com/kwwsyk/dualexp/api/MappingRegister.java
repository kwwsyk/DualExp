package com.kwwsyk.dualexp.api;

import com.kwwsyk.dualexp.Runtime;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;

import java.util.function.Predicate;
/**
 * Provides registration methods for customizing how experience points (XP) are converted
 * to score or currency within the runtime system. Supports both global and context-sensitive
 * (stack-trace-based) mappings.
 */
public class MappingRegister {
    /**
     * Registers a global XP-to-score mapping.
     * <p>
     * This mapping will be applied universally whenever a player gains experience.
     * The mapping function defines how many score points are gained per XP point.
     * </p>
     *
     * @param map a function that maps the XP value to a corresponding score value.
     *            By default, a 1:1 identity mapping is used if no override is provided.
     */
    public static void registerXpToScoreMapping(Int2IntFunction map){
        Runtime.xpScoreMapping.register(map);
    }

    /**
     * Registers a conditional XP-to-score mapping based on stack trace context.
     * <p>
     * When XP is gained, the current stack trace is evaluated by the provided predicate.
     * If the predicate returns true, the specified mapping function is applied.
     * This allows context-sensitive control over XP conversion logic.
     * </p>
     *
     * @param stack a predicate that inspects the current stack trace and determines
     *              whether this mapping should be applied.
     * @param map   a function that maps the XP value to a score value when the predicate matches.
     */
    public static void registerXpToScoreMapping(Predicate<StackTraceElement[]> stack, Int2IntFunction map){
        Runtime.xpScoreMapping.register(stack,map);
    }

    /**
     * Registers a global XP-to-currency mapping.
     * <p>
     * This mapping affects how much currency (or “rune”) is awarded when XP is gained.
     * The function defines the conversion logic from XP points to currency units.
     * </p>
     *
     * @param map a function that maps the XP value to a corresponding currency value.
     */
    public static void registerXpToCurrencyMapping(Int2IntFunction map){
        Runtime.xpRuneMapping.register(map);
    }

    /**
     * Registers a conditional XP-to-currency mapping based on stack trace context.
     * <p>
     * When XP is gained, the current stack trace is checked against the given predicate.
     * If matched, the specified mapping function is used to determine the currency gained.
     * This is useful for distinguishing XP usage contexts such as combat, crafting, or commands.
     * </p>
     *
     * @param stack a predicate evaluating the current stack trace to conditionally apply the mapping.
     * @param map   a function that maps XP values to currency values under the given context.
     */
    public static void registerXpToCurrencyMapping(Predicate<StackTraceElement[]> stack, Int2IntFunction map){
        Runtime.xpRuneMapping.register(stack, map);
    }
}
