package com.kwwsyk.dualexp;

import com.kwwsyk.dualexp.mapping.XpRuneMapping;
import com.kwwsyk.dualexp.mapping.XpScoreMapping;
import com.kwwsyk.dualexp.platform.services.IClientConfig;
import com.kwwsyk.dualexp.platform.services.IStartUpConfig;

public class Runtime {

    public static IStartUpConfig STARTUP_CONFIG;
    public static IClientConfig CLIENT_CONFIG;

    public static XpScoreMapping xpScoreMapping = new XpScoreMapping();
    public static XpRuneMapping xpRuneMapping = new XpRuneMapping();

    public static PlayerRuneAttachment runeAttachment;
}
