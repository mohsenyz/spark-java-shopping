package com.mphj.freelancer.index.admin;

import com.google.common.net.MediaType;
import com.mphj.freelancer.repository.CategoryDao;
import com.mphj.freelancer.repository.models.Category;
import com.mphj.freelancer.utils.*;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class AdminCategoryController {


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
        String body = ViewUtils.render(Path.Template.ADMIN_MANAGE_CATEGORIES, map);
        Cache.putAsync(Hash.hashRequest(request, Hash.HashType.FAST), body);

        return body;
    }


    public static String modify(Request request, Response response) {

        Integer id = null;
        try {
            id = Integer.parseInt(request.queryParams("id"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String name = request.queryParams("name");

        Integer parentId = -1;
        try {
            parentId = Integer.parseInt(request.queryParams("parentId"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Category category = new Category();
        category.setName(name);
        category.setParentId(parentId);

        CategoryDao categoryDao = new CategoryDao(HibernateUtils.getSessionFactory().getCurrentSession());

        if (id == null) {
            categoryDao.save(category);
        } else {
            category.setId(id);
            categoryDao.save(category);
        }
        return "done";
    }



    public static String delete(Request request, Response response) {

        Integer id = Integer.parseInt(request.queryParams("id"));
        CategoryDao categoryDao = new CategoryDao(HibernateUtils.getSessionFactory().getCurrentSession());

        Category category = new Category();
        category.setId(id);
        categoryDao.delete(category);

        return "done";
    }

}
