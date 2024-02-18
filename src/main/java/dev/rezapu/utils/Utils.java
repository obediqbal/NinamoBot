package dev.rezapu.utils;

public class Utils {
    public static void setPropertiesFromEnv(String... keys){
        for(String key: keys){
            if(System.getenv(key)==null) continue;
            System.setProperty(key, System.getenv(key));
        }
    }
}
