package com.kwwsyk.dualexp.mixin;

import com.kwwsyk.dualexp.api.ExpHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ExperienceOrb.class)
public class OrbMixin {

    @Inject(method = "playerTouch", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;giveExperiencePoints(I)V"),locals = LocalCapture.CAPTURE_FAILSOFT)
    public void playerTouch(Player entity, CallbackInfo ci, ServerPlayer serverplayer, int i){
        ExpHelper.giveLevelExp(serverplayer, i);
    }
}
