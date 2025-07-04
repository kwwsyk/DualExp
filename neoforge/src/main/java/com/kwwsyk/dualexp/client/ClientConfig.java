package com.kwwsyk.dualexp.client;

import com.kwwsyk.dualexp.platform.services.IClientConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {

    public static final ClientConfig CLIENT_CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    private final ModConfigSpec.BooleanValue renderRune;
    private final ModConfigSpec.BooleanValue disableXpCmdMention;

    static {
        Pair<ClientConfig,ModConfigSpec> pair = new ModConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }

    private ClientConfig(ModConfigSpec.Builder builder){
        renderRune = builder.comment("Render currency exp (rune) count at right corner.")
                .define("render_rune",true);
        disableXpCmdMention = builder.comment("Disable the mention when typing deprecated xp command.")
                .define("disable_mention",false);
    }

    public final IClientConfig NEO_IMPLEMENTATION = new IClientConfig() {
        @Override
        public boolean disableXpCmdMention() {
            return disableXpCmdMention.getAsBoolean();
        }

        @Override
        public boolean renderRuneCount() {
            return renderRune.getAsBoolean();
        }

        @Override
        public void setDisableMention(boolean disableMention) {
            disableXpCmdMention.set(disableMention);
            CONFIG_SPEC.save();
        }

        @Override
        public void setRenderingRune(boolean renderingRune) {
            renderRune.set(renderingRune);
            CONFIG_SPEC.save();
        }


    };
}
