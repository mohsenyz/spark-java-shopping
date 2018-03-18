package com.mphj.freelancer.index.admin;

import com.google.common.net.MediaType;
import com.mphj.freelancer.repository.GiftCardDao;
import com.mphj.freelancer.repository.models.GiftCard;
import com.mphj.freelancer.utils.*;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class AdminGiftCardsController {

    public static String index(Request request, Response response) {
        String requestHash = Hash.hashRequest(request);
        if (Cache.exists(requestHash)) {
            response.type(MediaType.HTML_UTF_8.toString());
            return (String) Cache.get(requestHash);
        }

        GiftCardDao giftCardDao = new GiftCardDao(HibernateUtils.getSessionFactory());

        Map<String, Object> map = new HashMap<>();
        map.put("giftcards", giftCardDao.getAll());

        response.type(MediaType.HTML_UTF_8.toString());
        String body = ViewUtils.render(Path.Template.ADMIN_GIFT_CARDS, map);
        Cache.putAsync(Hash.hashRequest(request), body);

        return body;
    }


    public static String postGiftcard(Request request, Response response) {
        String serial = request.queryParams("serial");
        String description = request.queryParams("description");
        String value = request.queryParams("value");

        int intValue = 0;

        try {
            intValue = Integer.parseInt(value);
            if (intValue <= 0)
                throw new IllegalArgumentException("Bad value");
        } catch (Exception e) {
            response.redirect("/admin/giftcards?bad_value=1");
            return null;
        }


        if (serial == null || serial.isEmpty()) {
            response.redirect("/admin/giftcards?bad_serial=1");
            return null;
        }

        GiftCardDao giftCardDao = new GiftCardDao(HibernateUtils.getSessionFactory());

        GiftCard giftCard = new GiftCard();
        giftCard.setCreatedAt(System.currentTimeMillis() / 1000l);
        giftCard.setDescription(description);
        giftCard.setExpiredAt(Long.MAX_VALUE);
        giftCard.setInitialValue(intValue);
        giftCard.setValue(intValue);
        giftCard.setSerial(serial);

        giftCardDao.save(giftCard);

        response.redirect("/admin/giftcards?success=1");
        return null;
    }

}
