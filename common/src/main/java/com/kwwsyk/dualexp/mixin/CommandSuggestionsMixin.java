package com.kwwsyk.dualexp.mixin;

import com.kwwsyk.dualexp.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
                && suit(this.input.getValue()) && this.commandUsage.isEmpty()){
            this.commandUsage.add(Constants.DEPRECATION_TIP.getVisualOrderText());
        }
    }

    @Unique
    private boolean suit(String value) {
        final String xp = "/xp";
        final String exp = "/experience";
        for(String s : new String[]{xp,exp,xp+" ",exp+" "}){
            if(s.equals(value)) return true;
        }
        return false;
    }
}
