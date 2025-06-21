package com.kwwsyk.dualexp;

import com.kwwsyk.dualexp.command.LevelExp;
import com.kwwsyk.dualexp.command.RuneCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class CommandReg {

    @SubscribeEvent
    public static void reg(RegisterCommandsEvent event){
        var dispatcher = event.getDispatcher();
        LevelExp.register(dispatcher);
        RuneCommand.register(dispatcher);
    }
}
