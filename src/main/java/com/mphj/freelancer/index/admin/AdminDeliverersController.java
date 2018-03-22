package com.mphj.freelancer.index.admin;

import com.google.common.net.MediaType;
import com.mphj.freelancer.repository.DelivererDao;
import com.mphj.freelancer.repository.models.Deliverer;
import com.mphj.freelancer.utils.*;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class AdminDeliverersController {

    public static String index(Request request, Response response) {
        String requestHash = Hash.hashRequest(request);
        if (Cache.exists(requestHash)) {
            response.type(MediaType.HTML_UTF_8.toString());
            return (String) Cache.get(requestHash);
        }

        DelivererDao delivererDao = new DelivererDao(HibernateUtils.getSessionFactory());

        Map<String, Object> map = new HashMap<>();
        map.put("deliverers", delivererDao.getAll());

        response.type(MediaType.HTML_UTF_8.toString());
        String body = ViewUtils.render(Path.Template.ADMIN_DELIVERERS, map);
        Cache.putAsync(Hash.hashRequest(request), body);

        return body;
    }


    public static String postDeliverer(Request request, Response response) {
        String name = request.queryParams("name");
        String password = request.queryParams("password");
        String phone = request.queryParams("phone");

        if (name == null || name.trim().isEmpty()) {
            response.redirect("/admin/deliverers?bad_name=1");
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            response.redirect("/admin/deliverers?bad_password=1");
            return null;
        }

        if (phone == null || phone.trim().isEmpty()) {
            response.redirect("/admin/deliverers?bad_phone=1");
            return null;
        }

        DelivererDao delivererDao = new DelivererDao(HibernateUtils.getSessionFactory());

        Deliverer deliverer = new Deliverer();
        deliverer.setLat(0);
        deliverer.setLng(0);
        deliverer.setName(name);
        deliverer.setPassword(password);
        deliverer.setPhone(phone);

        delivererDao.save(deliverer);

        response.redirect(request.contextPath() + "?success=1");
        return null;
    }

}
