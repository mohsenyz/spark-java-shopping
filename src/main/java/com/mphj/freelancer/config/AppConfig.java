package com.mphj.freelancer.config;

public class AppConfig {

    public static final String CONFIG_FILE = System.getenv("CONFIG_FILE");
    public static final String DEFAULT_CONFIG_FILE = "config.properties";
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "admin*@123";


    /**
     * It's changeable in runtime, no need to persistence
     */
    public static boolean AUTO_FORWARD_SHOPPING_CARDS = false;

}
