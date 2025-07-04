package com.kwwsyk.dualexp;


import com.kwwsyk.dualexp.client.ClientConfig;
import com.kwwsyk.dualexp.network.PlayerRune;
import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static com.kwwsyk.dualexp.Constants.MOD_ID;

@Mod(MOD_ID)
public class DualExpMod {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE_REGISTER = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES,MOD_ID);

    public static final Supplier<AttachmentType<Integer>> RUNE = ATTACHMENT_TYPE_REGISTER.register("rune", ()-> AttachmentType.builder(()->0).serialize(Codec.INT)/*.copyOnDeath()*/.build());

    private static boolean tickRefresh = true;

    public DualExpMod(IEventBus eventBus, ModContainer container) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        //Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        ATTACHMENT_TYPE_REGISTER.register(eventBus);

        NeoForge.EVENT_BUS.addListener(PlayerEvent.Clone.class, event -> {
            if(!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
            if (serverPlayer.level().getServer().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) && event.isWasDeath() && event.getOriginal().hasData(RUNE)) {
                Runtime.runeAttachment.setRune(serverPlayer,event.getOriginal().getData(RUNE));
                tickRefresh = true;
            }
        });
        NeoForge.EVENT_BUS.addListener(PlayerTickEvent.Post.class, event->{
            if(event.getEntity() instanceof ServerPlayer serverPlayer){
                if(tickRefresh) {
                    PacketDistributor.sendToPlayer(serverPlayer, new PlayerRune(serverPlayer.getData(RUNE)));
                    tickRefresh = false ;
                }
            }
        });

        Runtime.runeAttachment = new PlayerRuneAttachment() {
            @Override
            public int getRune(Player player) {
                return player.getData(RUNE);
            }

            @Override
            public void setRune(Player player, int rune) {
                player.setData(RUNE,rune);
                if(player instanceof ServerPlayer serverPlayer) PacketDistributor.sendToPlayer(serverPlayer, new PlayerRune(serverPlayer.getData(RUNE)));
            }
        };

        eventBus.addListener(RegisterPayloadHandlersEvent.class, event -> {
            final PayloadRegistrar registrar = event.registrar("1");
            registrar.playToClient(PlayerRune.TYPE, PlayerRune.STREAM_CODEC, (pkt,cxt)->pkt.handle(convert(cxt)));
        });

        if(FMLEnvironment.dist.isClient()){
            container.registerConfig(ModConfig.Type.CLIENT, ClientConfig.CONFIG_SPEC);
        }
        Runtime.CLIENT_CONFIG = ClientConfig.CLIENT_CONFIG.NEO_IMPLEMENTATION;

        container.registerConfig(ModConfig.Type.STARTUP, StartupConfig.CONFIG_SPEC);
        Runtime.STARTUP_CONFIG = StartupConfig.STARTUP_CONFIG.NEO_IMPLEMENTATION;

        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    private static com.kwwsyk.dualexp.network.IPayloadContext convert(IPayloadContext context){
        return context::player;
    }
}