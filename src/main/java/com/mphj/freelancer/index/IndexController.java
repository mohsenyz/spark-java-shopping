package com.mphj.freelancer.index;

import com.google.common.net.MediaType;
import com.mphj.freelancer.repository.CategoryDao;
import com.mphj.freelancer.utils.*;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class IndexController {

    public static String index(Request request, Response response) {
        String requestHash = Hash.hashRequest(request);
        if (Cache.exists(requestHash)) {
            response.type(MediaType.HTML_UTF_8.toString());
            return (String) Cache.get(requestHash);
        }

        CategoryDao categoryDao = new CategoryDao(HibernateUtils.getSessionFactory().getCurrentSession());
        Map<String, Object> map = new HashMap<>();
        map.put("cats", categoryDao.getAll());

        response.type(MediaType.HTML_UTF_8.toString());
        String body = ViewUtils.render(Path.Template.INDEX, map);
        Cache.putAsync(Hash.hashRequest(request, Hash.HashType.FAST), body);

        return body;
    }

}
