package com.mphj.freelancer.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import java.net.URLEncoder;

public class Sms {


    public static void sendSms(String phoneNumber, String content) {
        String url = "http://37.130.202.188/class/sms/webservice/send_url.php?"
                + "from=985000145"
                + "&to=" + URLEncoder.encode(phoneNumber)
                + "&msg=" + URLEncoder.encode(content)
                + "&uname=mohammad68"
                + "&pass=1234";
        try {
            HttpResponse<String> response = Unirest.get(url).asString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
