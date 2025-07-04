package com.kwwsyk.dualexp;

import com.kwwsyk.dualexp.platform.services.IStartUpConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class StartupConfig {

    private static final Pair<StartupConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(StartupConfig::new);
    public static final StartupConfig STARTUP_CONFIG = pair.getLeft();
    public static final ModConfigSpec CONFIG_SPEC = pair.getRight();

    private final ModConfigSpec.ConfigValue<String> currency_name;
    private final ModConfigSpec.ConfigValue<String> currency_command;
    private final ModConfigSpec.ConfigValue<String> new_exp_command;

    private StartupConfig(ModConfigSpec.Builder builder){
        currency_name = builder.comment("new Currency xp name.").define("currency_name",Constants.DEFAULT_CURRENCY_NAME);
        currency_command = builder.comment("Currency command, without '/'.").define("currency_command",Constants.DEFAULT_CURRENCY_NAME);
        new_exp_command = builder.comment("New experience command, replacing /xp or /experience, without '/'.").define("new_exp_command",Constants.NEW_XP_COMMAND);
    }

    public final IStartUpConfig NEO_IMPLEMENTATION = new IStartUpConfig() {
        @Override
        public String getCurrencyCmdName() {
            return currency_command.get();
        }

        @Override
        public String getCurrencyName() {
            return currency_name.get();
        }

        @Override
        public String getLevelXpCmdName() {
            return new_exp_command.get();
        }
    };
}
