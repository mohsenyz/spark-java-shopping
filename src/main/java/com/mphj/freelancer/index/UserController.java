package com.mphj.freelancer.index;

import com.mphj.freelancer.repository.UserDao;
import com.mphj.freelancer.repository.models.User;
import com.mphj.freelancer.utils.HibernateUtils;
import com.mphj.freelancer.utils.PhoneValidator;
import com.mphj.freelancer.utils.RandomGenerator;
import spark.Request;
import spark.Response;

public class UserController {

    public static String newUser(Request request, Response response) {
        String phone = request.queryParams("phone");
        if (!PhoneValidator.newMobileNumberValidator().isValid(phone)) {
            response.status(400);
            return "Invalid Phone";
        }

        String validatedPhone = PhoneValidator.newMobileNumberValidator().getValid(phone);
        UserDao userDao = new UserDao(HibernateUtils.getSessionFactory());
        User user = userDao.findByPhone(validatedPhone);

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


    public static String verify(Request request, Response response) {
        String code = request.queryParams("code");
        String phone = request.queryParams("phone");

        UserDao userDao = new UserDao(HibernateUtils.getSessionFactory());
        User user = userDao.findByPhone(phone);
        if (user == null) {
            response.redirect("/user/register");
            return null;
        }
        if (code.trim().equals(user.getVerificationCode())) {
            if (user.getToken() == null) {
                user.setToken(RandomGenerator.randomAlphaNumeric(120));
            }
            response.cookie("token", user.getToken());
            response.redirect("/user");
        } else {
            response.redirect("/user/verify?phone=" + phone);
        }
        return null;
    }

}
