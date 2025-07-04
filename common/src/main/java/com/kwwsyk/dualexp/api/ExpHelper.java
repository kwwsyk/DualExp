package com.kwwsyk.dualexp.api;

import com.kwwsyk.dualexp.Runtime;
import com.kwwsyk.dualexp.events.RuneEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

/**
 * Utility class for handling XP and Rune logic separately.
 * Provides low-level methods to grant XP or currency without triggering full XP/Rune system hooks.
 */
public class ExpHelper {

    /**
     * Grants XP to the player and increases their score.
     * This method simulates level experience gain as in vanilla, but also calls giveLevelExpWithoutScore.
     *
     * @param player the player receiving experience
     * @param xpPoints the amount of XP points to grant
     */
    public static void giveLevelExp(Player player, int xpPoints){
        player.increaseScore(xpPoints);
        giveLevelExpWithoutScore(player,xpPoints);
    }

    /**
     * Grants level experience without affecting the player's score.
     * This manually updates the player's experience progress and level based on the given XP points.
     *
     * @param player the player receiving experience
     * @param xpPoints the amount of XP points to grant
     */
    public static void giveLevelExpWithoutScore(Player player, int xpPoints){

        player.experienceProgress = player.experienceProgress + (float)xpPoints / (float)player.getXpNeededForNextLevel();
        player.totalExperience = Mth.clamp(player.totalExperience + xpPoints, 0, Integer.MAX_VALUE);

        while (player.experienceProgress < 0.0F) {
            float f = player.experienceProgress * (float)player.getXpNeededForNextLevel();
            if (player.experienceLevel > 0) {
                player.giveExperienceLevels(-1);
                player.experienceProgress = 1.0F + f / (float)player.getXpNeededForNextLevel();
            } else {
                player.giveExperienceLevels(-1);
                player.experienceProgress = 0.0F;
            }
        }

        while (player.experienceProgress >= 1.0F) {
            player.experienceProgress = (player.experienceProgress - 1.0F) * (float)player.getXpNeededForNextLevel();
            player.giveExperienceLevels(1);
            player.experienceProgress = player.experienceProgress / (float)player.getXpNeededForNextLevel();
        }
    }

    /**
     * Grants or removes XP levels directly.
     * <p>
     * - If {@code levels > 0}, behaves like vanilla XP level gain.
     * - If {@code levels < 0}, decreases the level manually and clamps state if below zero.
     *
     * @param player the target player
     * @param levels the number of levels to add (positive) or remove (negative)
     */
    public static void givePlayerLevels(Player player, int levels){
        if(levels > 0){
            player.giveExperienceLevels(levels);
        }else {
            player.experienceLevel += levels;
            if (player.experienceLevel < 0) {
                player.experienceLevel = 0;
                player.experienceProgress = 0.0F;
                player.totalExperience = 0;
            }
        }
    }

    /**
     * Gives or consumes rune (currency) for the player.
     * <p>
     * Triggers a {@code RuneEvent.Give} event, which can be modified or cancelled by other systems.
     * - Positive values will increase the player's rune.
     * - Negative values will attempt to deduct rune.
     *
     * @param player the target player
     * @param rune the amount of rune to give (positive) or consume (negative)
     */
    public static void giveCurrencyExp(Player player, int rune){
        var event = new RuneEvent.Give(player,rune);
        DualExpEvent.handleRuneEvent(event);
        rune = event.getRune();

        if(rune > 0) {
            Runtime.runeAttachment.giveRune(player, rune);
        } else if(rune < 0){
            Runtime.runeAttachment.costRune(player, -rune);
        }
    }

    /**
     * Converts a number of XP levels to their equivalent rune cost.
     * This method follows Minecraft's vanilla XP curve logic.
     *
     * @param level the number of levels to convert (can be negative)
     * @return the equivalent rune cost
     */
    public static int transformLevelToRuneCost(int level){
        if (level < 0) return -transformLevelToRuneCost(-level);

        if (level < 15) {
            return level * level + 6 * level;
        } else if (level < 30) {
            return (5 * level * level - 43 * level) / 2 + 1080;
        } else {
            return (9 * level * level - 325 * level) / 2 + 5510;
        }
    }
}
