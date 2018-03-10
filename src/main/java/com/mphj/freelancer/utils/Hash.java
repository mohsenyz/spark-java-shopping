package com.mphj.freelancer.utils;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import spark.Request;

public class Hash {

    public enum HashType {
        FAST
    }

    public static String fast(String data) {
        return Hashing.goodFastHash(16)
                .hashString(data, Charsets.UTF_8)
                .toString();
    }


    public static String hashRequest(Request request, HashType hashType) {
        String requestData = request.requestMethod() + ":" + request.uri();
        switch (hashType) {
            case FAST:
                return fast(requestData);
        }
        throw new IllegalArgumentException("HashType is incorrect!");
    }


    public static String hashRequest(Request request) {
        return hashRequest(request, HashType.FAST);
    }

}
