package dev.rezapu.utils;

import dev.rezapu.hooks.BaseHook;

import java.util.HashMap;
import java.util.Map;

public class HooksUtil {
    private static final HooksUtil hooksUtil = new HooksUtil();
    private final Map<Class<? extends BaseHook>, BaseHook> hooks;

    private HooksUtil(){
        hooks = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseHook> T getHook(Class<T> clazz) throws InstantiationException{
        if(BaseHook.class.isAssignableFrom(clazz)){
            return (T) hooksUtil.hooks.get(clazz);
        }
        throw new InstantiationException();
    }

    public static <T extends BaseHook> void addHook(T hook){
        hooksUtil.hooks.put(hook.getClass(), hook);
    }
}
