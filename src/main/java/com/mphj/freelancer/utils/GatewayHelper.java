package com.mphj.freelancer.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

public class GatewayHelper {

    public static String newSession(int price, int shoppingCardId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Amount", price);
        jsonObject.put(
                "MerchantID",
                AppProperties.getProp().getProperty("gateway.token")
        );
        String callbackUrl = AppProperties.getProp().getProperty("host")
                .replace("{id}", String.valueOf(shoppingCardId));
        jsonObject.put("CallbackURL", callbackUrl);
        jsonObject.put(
                "Description",
                AppProperties.getProp().getProperty("gateway.description")
        );
        String body = jsonObject.toString();
        HttpResponse<JsonNode> response = Unirest.post("https://www.zarinpal.com/pg/rest/WebGate/PaymentRequest.json")
                .header(HTTP.USER_AGENT, "ZarinPal Rest Api v1")
                .header(HTTP.CONTENT_TYPE, "application/json")
                .body(body)
                .asJson();
        JSONObject responseObj = response.getBody().getObject();
        int status = responseObj.getInt("Status");
        if (status == 100) {
            String authority = responseObj.getString("Authority");
            return authority;
        } else {
            throw new RuntimeException(
                    "Failed to create gateway session \n" + responseObj.toString()
            );
        }
    }


    public static String gatewayUrl(String authority) {
        return "https://www.zarinpal.com/pg/StartPay/" + authority;
    }


    public static String verify(String authority, int amount) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(
                "MerchantID",
                AppProperties.getProp().getProperty("gateway.token")
        );
        jsonObject.put("Authority", authority);
        jsonObject.put("Amount", amount);
        String jsonBody = jsonObject.toString();

        HttpResponse<JsonNode> response = Unirest.post("https://www.zarinpal.com/pg/rest/WebGate/PaymentVerification.json")
                .header(HTTP.USER_AGENT, "ZarinPal Rest Api v1")
                .header(HTTP.CONTENT_TYPE, "application/json")
                .body(jsonBody)
                .asJson();
        JSONObject responseObj = response.getBody().getObject();
        int status = responseObj.getInt("Status");
        if (status == 100) {
            String refId = responseObj.getString("RefID");
            return refId;
        } else {
            throw new RuntimeException(
                    "Error while payment verification \n" + responseObj.toString()
            );
        }
    }

}
