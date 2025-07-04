package com.kwwsyk.dualexp.mixin;

import com.kwwsyk.dualexp.Constants;
import com.kwwsyk.dualexp.command.DeprecationMention;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(targets = "net.minecraft.client.gui.components.CommandSuggestions$SuggestionsList")
public class SuggestionListMixin {

    @Shadow(aliases = "field_21615") @Final
    private CommandSuggestions this$0;

    @Inject(method = "useSuggestion",at = @At("HEAD"))
    private void mention(CallbackInfo ci){
        if(DeprecationMention.doMention((((SuggestionAccessor)this$0).getInput().getValue()))){
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Constants.DEPRECATION_TIP.get().withStyle(Style.EMPTY.withHoverEvent(Constants.DEPRECATION_HOVERING_TIP)));
        }
    }

}
