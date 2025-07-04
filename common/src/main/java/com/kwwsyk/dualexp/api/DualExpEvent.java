package com.kwwsyk.dualexp.api;

import com.kwwsyk.dualexp.events.RuneEvent;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DualExpEvent {

    private static final List<Consumer<RuneEvent>> Handlers = new ArrayList<>();

    /**
     * Register event handler methods that would be invoked when {@link ExpHelper#giveCurrencyExp(Player, int)} is called.
     * @param event a consumer that has a RuneEvent param/
     */
    public static void registerRuneEvents(Consumer<RuneEvent> event){
        Handlers.add(event);
    }

    public static void handleRuneEvent(RuneEvent event){
        for(Consumer<RuneEvent> consumer : Handlers){
            consumer.accept(event);
        }
    }
}
