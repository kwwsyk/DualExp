package com.kwwsyk.dualexp.network;

import com.kwwsyk.dualexp.Constants;
import com.kwwsyk.dualexp.Runtime;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PlayerRune(int rune) implements CustomPacketPayload {

    public static final Type<PlayerRune> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID,"player_rune"));

    public static final StreamCodec<FriendlyByteBuf,PlayerRune> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,PlayerRune::rune,PlayerRune::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context){
        Runtime.runeAttachment.setRune(context.getPlayer(),rune);
    }
}
