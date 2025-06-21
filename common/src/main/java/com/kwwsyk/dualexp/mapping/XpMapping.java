package com.kwwsyk.dualexp.mapping;

import com.kwwsyk.dualexp.Constants;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class XpMapping {

    public static final Int2IntFunction DEFAULT_MAPPING = x->x;
    private Int2IntFunction xpMap = DEFAULT_MAPPING;
    private final Map<Predicate<StackTraceElement[]>,Int2IntFunction> byStackMap = new LinkedHashMap<>();

    protected String mapType = "AbstractXpMap";

    public boolean StackQuestFlag = false;

    public int get(int xpPoints, @Nullable StackTraceElement[] stack){
        if(StackQuestFlag){
            for(Map.Entry<Predicate<StackTraceElement[]>,Int2IntFunction> entry: byStackMap.entrySet()){
                if(entry.getKey().test(stack)) return entry.getValue().applyAsInt(xpPoints);
            }
        }
        return xpMap.get(xpPoints);
    }

    public void register(Int2IntFunction xpMap){
        if(xpMap!=DEFAULT_MAPPING) Constants.LOG.warn("A {} has been registered when registering an xpMap",mapType);
        this.xpMap = xpMap;
    }

    public void register(Predicate<StackTraceElement[]> stackPredicate, Int2IntFunction xpMap){
        byStackMap.put(stackPredicate,xpMap);
        this.StackQuestFlag = true;
    }
}
