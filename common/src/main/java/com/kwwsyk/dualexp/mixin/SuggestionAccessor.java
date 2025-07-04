package com.kwwsyk.dualexp.mixin;

import net.minecraft.client.gui.components.EditBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.client.gui.components.CommandSuggestions")
public interface SuggestionAccessor {

    @Accessor("input")
    EditBox getInput();
}
