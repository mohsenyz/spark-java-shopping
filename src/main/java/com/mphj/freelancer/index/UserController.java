package com.mphj.freelancer.index;

import com.mphj.freelancer.repository.CategoryDao;
import com.mphj.freelancer.repository.DelivererDao;
import com.mphj.freelancer.repository.ShoppingCardDao;
import com.mphj.freelancer.repository.UserDao;
import com.mphj.freelancer.repository.models.ShoppingCard;
import com.mphj.freelancer.repository.models.User;
import com.mphj.freelancer.utils.*;
import org.apache.http.entity.ContentType;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {

    public static String newUser(Request request, Response response) {
        String phone = request.queryParams("phone");
        if (!PhoneValidator.newMobileNumberValidator().isValid(phone)) {
            response.status(400);
            return "Invalid Phone";
        }

        String validatedPhone = PhoneValidator.newMobileNumberValidator().getValid(phone);
        UserDao userDao = new UserDao(HibernateUtils.getSessionFactory());
        User user = null;

        try {
            user = userDao.findByPhone(validatedPhone);
        } catch (Exception e) {
            HibernateUtils.getSessionFactory()
                    .getCurrentSession()
                    .getTransaction()
                    .commit();
            e.printStackTrace();
        }

        if (user == null) {
            user = new User();
            user.setPhone(validatedPhone);
            user.setCreatedAt(System.currentTimeMillis() / 1000l);
            userDao.save(user);
        }

        user.setVerificationCode(RandomGenerator.randomNumeric(5));
        // @TODO send code
        user.setVerificationCodeTime(System.currentTimeMillis() / 1000l);
        userDao.save(user);
        return "200";
    }


    public static String verifyUser(Request request, Response response) {
        String code = request.queryParams("code");
        String phone = request.queryParams("phone");

        PhoneValidator phoneValidator = PhoneValidator.newMobileNumberValidator();
        if (!phoneValidator.isValid(phone)) {
            response.status(500);
            return "Bad phone";
        }

        phone = phoneValidator.getValid(phone);

        UserDao userDao = new UserDao(HibernateUtils.getSessionFactory());
        User user = null;
        try {
            user = userDao.findByPhone(phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user == null) {
            response.status(400);
            return null;
        }
        if (code.trim().equals(user.getVerificationCode())) {
            if (user.getToken() == null) {
                user.setToken(RandomGenerator.randomAlphaNumeric(120));
            }
            userDao.save(user);
            response.status(200);
            response.type(ContentType.TEXT_PLAIN.toString());
            return user.getToken();
        } else {
            response.status(500);
            return "500";
        }
    }


    public static String viewShoppingCards(Request request, Response response) {
        String token = request.cookie("token");
        if (token == null) {
            token = request.queryParams("token");
        }
        if (token == null || token.trim().isEmpty()) {
            response.redirect("/?not_user=1");
            return null;
        }

        UserDao userDao = new UserDao(HibernateUtils.getSessionFactory());
        User user = null;
        try {
            user = userDao.findByToken(token);
        } catch (Exception e) {
            // Bad token
        }
        if (user == null) {
            response.redirect("/?bad_token=1");
            return null;
        }

        ShoppingCardDao shoppingCardDao = new ShoppingCardDao(HibernateUtils.getSessionFactory());
        DelivererDao delivererDao = new DelivererDao(HibernateUtils.getSessionFactory());

        List<ShoppingCard> shoppingCards = shoppingCardDao.findByUserId(user.getId());

        for (ShoppingCard shoppingCard : shoppingCards) {

            if (shoppingCard.getDelivererId() != 0) {
                shoppingCard.setDeliverer(delivererDao.findById(shoppingCard.getDelivererId()));
            }

        }

        Map<String, Object> map = new HashMap<>();
        map.put("shoppingcards", shoppingCards);
        DeviceUtils.handleMobile(map, request);

        CategoryDao categoryDao = new CategoryDao(HibernateUtils.getSessionFactory());
        map.put("cats", categoryDao.getAll());

        return ViewUtils.render(Path.Template.USER_SHOPPING_CARDS, map);
    }


    public static String viewMap(Request request, Response response) {
        return ViewUtils.render(Path.Template.USER_MAP);
    }

}
