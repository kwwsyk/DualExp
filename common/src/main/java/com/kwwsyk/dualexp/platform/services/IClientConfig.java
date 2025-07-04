package com.kwwsyk.dualexp.platform.services;

public interface IClientConfig {

    boolean disableXpCmdMention();

    boolean renderRuneCount();

    void setDisableMention(boolean disableMention);

    void setRenderingRune(boolean renderingRune);
}
