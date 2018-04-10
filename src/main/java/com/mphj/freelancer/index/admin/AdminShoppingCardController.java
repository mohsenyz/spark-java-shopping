package com.mphj.freelancer.index.admin;

import com.google.common.net.MediaType;
import com.mphj.freelancer.repository.DelivererDao;
import com.mphj.freelancer.repository.ShoppingCardDao;
import com.mphj.freelancer.repository.UserDao;
import com.mphj.freelancer.repository.models.Deliverer;
import com.mphj.freelancer.repository.models.ShoppingCard;
import com.mphj.freelancer.repository.models.User;
import com.mphj.freelancer.utils.*;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminShoppingCardController {

    public static String index(Request request, Response response) {
        String requestHash = Hash.hashRequest(request);
        if (Cache.exists(requestHash)) {
            response.type(MediaType.HTML_UTF_8.toString());
            return (String) Cache.get(requestHash);
        }

        ShoppingCardDao shoppingCardDao = new ShoppingCardDao(HibernateUtils.getSessionFactory());
        UserDao userDao = new UserDao(HibernateUtils.getSessionFactory());
        DelivererDao delivererDao = new DelivererDao(HibernateUtils.getSessionFactory());

        List<ShoppingCard> shoppingCards = shoppingCardDao.getAll();

        int totalPayedShoppingCardPrice = 0;
        int totalPayedShoppingCards = 0;
        int totalUndeliveredShoppingCards = 0;
        int totalDeliveredShoppingCards = 0;

        for (ShoppingCard shoppingCard : shoppingCards) {
            User user = userDao.findById(shoppingCard.getUserId());
            shoppingCard.setUser(user);
            if (shoppingCard.isPayed()) {
                totalPayedShoppingCardPrice += shoppingCard.getPrice();
                totalPayedShoppingCards++;
                if (shoppingCard.isDelivered()) {
                    totalDeliveredShoppingCards++;
                } else {
                    totalUndeliveredShoppingCards--;
                }
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("shoppingcards", shoppingCards);
        map.put("deliverers", delivererDao.getAll());
        map.put("totalPayedShoppingCardPrice", totalPayedShoppingCardPrice);
        map.put("totalPayedShoppingCards", totalPayedShoppingCards);
        map.put("totalDeliveredShoppingCards", totalDeliveredShoppingCards);
        map.put("totalUndeliveredShoppingCards", totalUndeliveredShoppingCards);

        response.type(MediaType.HTML_UTF_8.toString());
        String body = ViewUtils.render(Path.Template.ADMIN_SHOPPINGCARDS, map);
        Cache.putAsync(Hash.hashRequest(request), body);

        return body;
    }


    public static String setDeliverer(Request request, Response response) {
        String shoppingCardIdStr = request.queryParams("shc_id");
        String delivererIdStr = request.queryParams("id");

        int delivererId, shoppingCardId;

        if (delivererIdStr == null ||
                delivererIdStr.isEmpty() ||
                shoppingCardIdStr == null ||
                shoppingCardIdStr.isEmpty()) {

            response.redirect("/admin/shoppingcards?bad_input=1");
            return null;

        }

        try {
            delivererId = Integer.parseInt(delivererIdStr);
            shoppingCardId = Integer.parseInt(shoppingCardIdStr);
        } catch (Exception e) {
            response.redirect("/admin/shoppingcards?bad_input=1");
            return null;
        }

        ShoppingCardDao shoppingCardDao = new ShoppingCardDao(HibernateUtils.getSessionFactory());
        DelivererDao delivererDao = new DelivererDao(HibernateUtils.getSessionFactory());

        if (delivererId != 0) {
            Deliverer deliverer = delivererDao.findById(delivererId);
        }

        ShoppingCard shoppingCard = shoppingCardDao.findById(shoppingCardId);
        shoppingCard.setDelivererId(delivererId);

        shoppingCardDao.save(shoppingCard);

        response.redirect("/admin/shoppingcards?sd_success=1");
        return null;
    }
}
