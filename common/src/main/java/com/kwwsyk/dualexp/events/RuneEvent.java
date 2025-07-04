package com.kwwsyk.dualexp.events;

import net.minecraft.world.entity.player.Player;

public class RuneEvent {

    private final Player player;
    private final int originalRune;
    private int rune;

    public RuneEvent(Player player, int originalRune){
        this.player = player;
        this.originalRune = originalRune;
        this.rune = originalRune;
    }


    public Player getPlayer() {
        return player;
    }

    public int getOriginalRune() {
        return originalRune;
    }

    public int getRune() {
        return rune;
    }

    public void setRune(int rune){
        this.rune = rune;
    }

    public static class Give extends RuneEvent{

        public Give(Player player, int originalRune) {
            super(player, originalRune);
        }
    }
}
