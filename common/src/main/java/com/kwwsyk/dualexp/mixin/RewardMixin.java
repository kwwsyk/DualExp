package com.kwwsyk.dualexp.mixin;

import com.kwwsyk.dualexp.api.ExpHelper;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AdvancementRewards.class)
public class RewardMixin {

    @Inject(method = "grant",at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;giveExperiencePoints(I)V"))
    public void grant(ServerPlayer player, CallbackInfo ci){
        ExpHelper.giveLevelExp(player, ((AdvancementRewards)((Object)this)).experience());
    }
}
