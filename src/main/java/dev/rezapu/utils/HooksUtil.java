package dev.rezapu.utils;

import dev.rezapu.dao.HookDAO;
import dev.rezapu.hooks.BaseHook;
import dev.rezapu.model.Hook;
import net.dv8tion.jda.api.JDA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HooksUtil {
    private static final HooksUtil hooksUtil = new HooksUtil();
    private final Map<Class<? extends BaseHook>, BaseHook> hooks;

    private HooksUtil(){
        hooks = new HashMap<>();
    }

    public static void initHooks(JDA jda) {
        HookDAO hookDAO = new HookDAO();
        List<Hook> hookList = hookDAO.getAll();

        try{
            for(Hook hookModel: hookList){
                Class<? extends BaseHook> clazz = hookModel.getType().getHookClass();
                BaseHook hook = clazz
                        .getDeclaredConstructor()
                        .newInstance()
                        .connect(jda, hookModel);
                if(hook.getMessage()==null) hookDAO.deleteData(hookModel);
                hook.update();
                hooksUtil.hooks.put(clazz, hook);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        System.out.println("Successfully initiated hooks");
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseHook> T getHook(Class<T> clazz) throws InstantiationException{
        if(BaseHook.class.isAssignableFrom(clazz)){
            return (T) hooksUtil.hooks.get(clazz);
        }
        throw new InstantiationException();
    }

    public static <T extends BaseHook> void addHook(T hook){
        HookDAO hookDAO = new HookDAO();
        hooksUtil.hooks.put(hook.getClass(), hook);
        hookDAO.addData(hook.getModel());
    }

    public static <T extends BaseHook> void deleteHook(T hook){
        HookDAO hookDAO = new HookDAO();
        hooksUtil.hooks.remove(hook.getClass(), hook);
        hookDAO.deleteData(hook.getModel());
    }
}
