package com.kwwsyk.dualexp.api;

import com.kwwsyk.dualexp.Runtime;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;

import java.util.function.Predicate;

public class MappingRegister {

    public static void registerXpToScoreMapping(Int2IntFunction map){
        Runtime.xpScoreMapping.register(map);
    }

    public static void registerXpToScoreMapping(Predicate<StackTraceElement[]> stack, Int2IntFunction map){
        Runtime.xpScoreMapping.register(stack,map);
    }

    public static void registerXpToCurrencyMapping(Int2IntFunction map){
        Runtime.xpRuneMapping.register(map);
    }

    public static void registerXpToCurrencyMapping(Predicate<StackTraceElement[]> stack, Int2IntFunction map){
        Runtime.xpRuneMapping.register(stack, map);
    }
}
