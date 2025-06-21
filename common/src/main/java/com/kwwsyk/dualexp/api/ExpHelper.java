package com.kwwsyk.dualexp.api;

import com.kwwsyk.dualexp.Runtime;
import com.kwwsyk.dualexp.events.RuneEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class ExpHelper {

    public static void giveLevelExp(Player player, int xpPoints){
        player.increaseScore(xpPoints);
        giveLevelExpWithoutScore(player,xpPoints);
    }

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

    public static void giveCurrencyExp(Player player, int rune){
        var event = new RuneEvent(player,rune);
        rune = event.getRune();

        if(rune > 0) {
            Runtime.runeAttachment.giveRune(player, rune);
        } else if(rune < 0){
            Runtime.runeAttachment.costRune(player, -rune);
        }
    }

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
