package com.mphj.freelancer.utils;

import spark.Request;

import java.util.Map;

public class DeviceUtils {

    public static void handleMobile(Map map, Request request) {
        String mobileHeader = request.headers("Mobile-Version");
        if (mobileHeader == null) {
            mobileHeader = request.queryParams("mobile_version");
        }
        if (mobileHeader != null) {
            map.put("isMobile", true);
        } else {
            map.put("isMobile", false);
        }
    }

}
