package com.kwwsyk.dualexp.platform.services;

import com.kwwsyk.dualexp.Constants;

public interface IStartUpConfig {

    default String getLevelXpCmdName(){
        return Constants.NEW_XP_COMMAND;
    }

    default String getCurrencyCmdName(){
        return getCurrencyName();
    }

    default String getCurrencyName(){
        return Constants.DEFAULT_CURRENCY_NAME;
    }
}
