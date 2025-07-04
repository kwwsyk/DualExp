package com.kwwsyk.dualexp.command;


import com.kwwsyk.dualexp.Runtime;

public class DeprecationMention {

    public static boolean doMention(String value) {
        return !Runtime.CLIENT_CONFIG.disableXpCmdMention() && inputtingXpCmd(value);
    }

    public static boolean inputtingXpCmd(String value){
        final String xp = "/xp";
        final String experience = "/experience";

        // 精确匹配 /xp 或 /experience 或它们带空格的形式
        if (value.equals(xp) || value.equals(experience)) {
            return true;
        }

        // 模糊匹配：/exp 开头，长度不超过 /experience，并且是其前缀
        if (value.startsWith("/exp")) {
            // 提取用户输入的前缀长度不超过 experience，并且是其前缀
            int len = value.length();
            if(experience.length() < value.length()) return false;
            String expectedPrefix = experience.substring(0, len);
            return value.startsWith(expectedPrefix);
        }

        return false;
    }
}
