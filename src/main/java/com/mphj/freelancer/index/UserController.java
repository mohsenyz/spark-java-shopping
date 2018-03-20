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
            return user.getToken();
        } else {
            response.status(500);
            return "500";
        }
    }

}
