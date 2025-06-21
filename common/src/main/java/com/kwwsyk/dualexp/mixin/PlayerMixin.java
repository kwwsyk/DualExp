package com.kwwsyk.dualexp.mixin;

import com.kwwsyk.dualexp.api.ExpHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.kwwsyk.dualexp.Runtime.*;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    @Shadow
    protected int enchantmentSeed;

    @Shadow
    public abstract void increaseScore(int score);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "giveExperiencePoints", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;increaseScore(I)V"),cancellable = true)
    public void giveExperiencePoints(int xpPoints, CallbackInfo ci){
        this.increaseScore(xpScoreMapping.get(xpPoints, xpScoreMapping.StackQuestFlag?Thread.currentThread().getStackTrace():null));

        ExpHelper.giveCurrencyExp((Player)((Object)this), xpRuneMapping.get(xpPoints,xpRuneMapping.StackQuestFlag?Thread.currentThread().getStackTrace() : null));

        ci.cancel();
    }

    @Inject(method = "onEnchantmentPerformed(Lnet/minecraft/world/item/ItemStack;I)V",at=@At("HEAD"),cancellable = true)
    public void onEnchantPerformed(ItemStack item, int levelCost, CallbackInfo info){
        ExpHelper.giveCurrencyExp((Player)((Object)this),-ExpHelper.transformLevelToRuneCost(levelCost));
        this.enchantmentSeed = this.random.nextInt();
        info.cancel();
    }

    @Inject(method = "giveExperienceLevels", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Player;experienceLevel:I"), cancellable = true)
    public void giveExperienceLevels(int levels, CallbackInfo ci){
        if(levels < 0){
            ExpHelper.giveCurrencyExp((Player)((Object)this),ExpHelper.transformLevelToRuneCost(levels));
            ci.cancel();
        }
    }
}
