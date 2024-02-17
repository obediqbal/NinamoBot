package dev.rezapu.hooks;

public enum HookType {
    LEADERBOARD(LeaderboardHook.class);

    private Class<? extends BaseHook> clazz;
    private <T extends BaseHook> HookType(Class<T> clazz){
        this.clazz = clazz;
    }

    public Class<? extends BaseHook> getHookClass(){
        return this.clazz;
    }
}
