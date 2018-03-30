package com.mphj.freelancer.index.admin;

import com.google.common.net.MediaType;
import com.mphj.freelancer.repository.ShoppingCardDao;
import com.mphj.freelancer.repository.UserDao;
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

        List<ShoppingCard> shoppingCards = shoppingCardDao.getAll();

        for (ShoppingCard shoppingCard : shoppingCards) {
            User user = userDao.findById(shoppingCard.getUserId());
            shoppingCard.setUser(user);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("shoppingcards", shoppingCards);

        response.type(MediaType.HTML_UTF_8.toString());
        String body = ViewUtils.render(Path.Template.ADMIN_SHOPPINGCARDS, map);
        Cache.putAsync(Hash.hashRequest(request), body);

        return body;
    }
}
