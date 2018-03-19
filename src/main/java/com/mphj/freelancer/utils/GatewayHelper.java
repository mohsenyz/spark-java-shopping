package com.mphj.freelancer.utils;

public class GatewayHelper {

    public static String newSession(int price, int shoppingCardId) {
        String description = "پرداخت سبد خرید";
        return RandomGenerator.randomAlphaNumeric(20);
    }

}
