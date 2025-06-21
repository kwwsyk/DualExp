package com.kwwsyk.dualexp;

import net.minecraft.world.entity.player.Player;

public interface PlayerRuneAttachment {

    int getRune(Player player);

    void setRune(Player player, int rune);

    default void giveRune(Player player, int rune){
        setRune(player,getRune(player)+rune);
    }

    default void costRune(Player player, int rune){
        setRune(player,Math.max(getRune(player)-rune,0));
    }
}
