package com.kwwsyk.dualexp.mixin;

import com.kwwsyk.dualexp.Constants;
import com.kwwsyk.dualexp.command.DeprecationMention;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CommandSuggestions.class)
public class CommandSuggestionsMixin {

    @Shadow
    @Final
    EditBox input;

    @Shadow
    @Final
    private List<FormattedCharSequence> commandUsage;

    @Inject(method = "updateUsageInfo",at = @At(value = "HEAD"))
    public void updateInfo(CallbackInfo ci){
        if (this.input.getCursorPosition() == this.input.getValue().length()
                && DeprecationMention.doMention(this.input.getValue()) && this.commandUsage.isEmpty()){
            this.commandUsage.add(Constants.DEPRECATION_TIP.get().getVisualOrderText());
        }
    }



}
