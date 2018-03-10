package com.mphj.freelancer.utils;

import com.mphj.freelancer.config.AppConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {

    private static Properties prop;

    public static final String REDIS_ENABLED = "redis.enabled";
    public static final String REDIS_HOST = "redis.host";

    static {
        prop = new Properties();
        try {
            if (AppConfig.CONFIG_FILE != null) {
                prop.load(new FileInputStream(AppConfig.CONFIG_FILE));
            } else {
                prop.load(AppProperties.class.getClassLoader().getResourceAsStream(AppConfig.DEFAULT_CONFIG_FILE));
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isRedisEnabled() {
        return prop.getProperty(REDIS_ENABLED).trim().equals("true");
    }


    public static String getRedisHost() {
        return prop.getProperty(REDIS_HOST).trim();
    }


    public static Properties getProp() {
        return prop;
    }
}