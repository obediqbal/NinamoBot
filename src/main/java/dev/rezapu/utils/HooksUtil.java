package dev.rezapu.utils;

import dev.rezapu.dao.HookDAO;
import dev.rezapu.hooks.BaseHook;
import dev.rezapu.model.Hook;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HooksUtil {
    private static final HooksUtil hooksUtil = new HooksUtil();
    private final Map<Class<? extends BaseHook>, Map<String, BaseHook>> hooks;

    private HooksUtil(){
        hooks = new HashMap<>();
    }

    public static void initHooks(JDA jda) {
        HookDAO hookDAO = new HookDAO();
        List<Hook> hookList = hookDAO.getAll();

        try{
            for(Hook hookModel: hookList){
                Class<? extends BaseHook> clazz = hookModel.getType().getHookClass();
                try{
                    BaseHook hook = clazz
                            .getDeclaredConstructor()
                            .newInstance()
                            .connect(jda, hookModel);
                    hook.update();
                    putIntoBuffer(hook);
                }catch (ErrorResponseException e){
                    hookDAO.deleteData(hookModel);
                }catch (InstantiationException e){
                    throw new RuntimeException(e);
                }
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        System.out.println("Successfully initiated hooks");
    }

    public static <T extends BaseHook> T getHook(Class<T> clazz, String message_id) throws InstantiationException{
        T res = getFromBuffer(clazz, message_id);

        if(res!=null) return res;
        throw new InstantiationException();
    }

    public static <T extends BaseHook> void updateHooks(Class<T> clazz) throws InstantiationException{
        List<BaseHook> hooks = getHooksList(clazz);
        for(BaseHook hook: hooks){
            hook.update();
        }
    }

    public static <T extends BaseHook> List<BaseHook> getHooksList(Class<T> clazz) throws InstantiationException{
        if(BaseHook.class.isAssignableFrom(clazz)){
            return new ArrayList<>(getHooksMap(clazz).values());
        }
        throw new InstantiationException();
    }

    public static <T extends BaseHook> void addHook(T hook){
        putIntoBuffer(hook);
        HookDAO hookDAO = new HookDAO();
        hookDAO.addData(hook.getModel());
    }

    public static <T extends BaseHook> void deleteHook(T hook){
        deleteFromBuffer(hook);
        HookDAO hookDAO = new HookDAO();
        hookDAO.deleteData(hook.getModel());
    }

    private static <T extends BaseHook> Map<String, BaseHook> getHooksMap(Class<T> clazz){
        return hooksUtil.hooks.get(clazz);
    }

    @SuppressWarnings("unchecked")
    private static <T extends BaseHook> T getFromBuffer(Class<T> clazz, String message_id){
        if(BaseHook.class.isAssignableFrom(clazz)) {
            Map<String, BaseHook> specificHooks = hooksUtil.hooks.get(clazz);
            if(specificHooks!=null && specificHooks.containsKey(message_id)) return (T) specificHooks.get(message_id);
        }
        return null;
    }

    private static <T extends BaseHook> void putIntoBuffer(T hook){
        Map<String, BaseHook> specificHooks = hooksUtil.hooks.get(hook.getClass());
        if(specificHooks==null){
            hooksUtil.hooks.put(hook.getClass(), new HashMap<String, BaseHook>());
            specificHooks = hooksUtil.hooks.get(hook.getClass());
        }
        specificHooks.put(hook.getMessage().getId(), hook);
    }

    private static <T extends BaseHook> void deleteFromBuffer(T hook){
        Map<String, BaseHook> specificHooks = hooksUtil.hooks.get(hook.getClass());
        if(specificHooks!=null){
            specificHooks.remove(hook.getMessage().getId());
            if(specificHooks.isEmpty()) hooksUtil.hooks.remove(hook.getClass());
        }
    }
}
