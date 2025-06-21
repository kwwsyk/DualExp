package com.kwwsyk.dualexp.client;

import com.kwwsyk.dualexp.Constants;
import com.kwwsyk.dualexp.DualExpMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(value = Dist.CLIENT,modid = Constants.MOD_ID,bus = EventBusSubscriber.Bus.GAME)
public class RenderEvent {
    @SubscribeEvent
    public static void renderRune(RenderGuiEvent.Post event){
        Minecraft mc = Minecraft.getInstance();
        if(mc.player==null) return;
        if(!mc.options.hideGui&&!mc.player.isSpectator()) {
            int rune = mc.player.getData(DualExpMod.RUNE);
            GuiGraphics guiGraphics = event.getGuiGraphics();
            int w =guiGraphics.guiWidth();
            int h =guiGraphics.guiHeight();
            String s = rune+"";
            int x = w-mc.font.width(s)-18;
            int y = h-4-18;
            guiGraphics.drawString(mc.font,s,x-1,y,0);
            guiGraphics.drawString(mc.font,s,x+1,y,0);
            guiGraphics.drawString(mc.font,s,x,y-1,0);
            guiGraphics.drawString(mc.font,s,x,y+1,0);
            guiGraphics.drawString(mc.font,s,x,y,0xA0A080);
        }
    }
}
