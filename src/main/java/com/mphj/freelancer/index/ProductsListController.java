package com.mphj.freelancer.index;

import com.google.common.net.MediaType;
import com.mphj.freelancer.repository.CategoryDao;
import com.mphj.freelancer.repository.ProductDao;
import com.mphj.freelancer.repository.models.Product;
import com.mphj.freelancer.utils.*;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductsListController {


    public static String index(Request request, Response response) {
        String requestHash = Hash.hashRequest(request);
        if (Cache.exists(requestHash)) {
            response.type(MediaType.HTML_UTF_8.toString());
            return (String) Cache.get(requestHash);
        }


        Map<String, Object> map = new HashMap<>();
        CategoryDao categoryDao = new CategoryDao(HibernateUtils.getSessionFactory());
        ProductDao productDao = new ProductDao(HibernateUtils.getSessionFactory());
        map.put("cats", categoryDao.getAll());
        map.put("products", new ArrayList<>());

        if (request.queryParams("cat_id") != null) {
            System.err.println(request.queryParams("cat_id"));
            try {
                map.put(
                        "products",
                        productDao.findByCategory(Integer.parseInt(request.queryParams("cat_id")))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.type(MediaType.HTML_UTF_8.toString());
        String body = ViewUtils.render(Path.Template.LIST_PRODUCTS, map);
        Cache.putAsync(Hash.hashRequest(request, Hash.HashType.FAST), body);
        return body;
    }


    public static String view(Request request, Response response) {
        String requestHash = Hash.hashRequest(request);

        if (Cache.exists(requestHash)) {
            response.type(MediaType.HTML_UTF_8.toString());
            return (String) Cache.get(requestHash);
        }

        CategoryDao categoryDao = new CategoryDao(HibernateUtils.getSessionFactory());
        ProductDao productDao = new ProductDao(HibernateUtils.getSessionFactory());
        Map<String, Object> map = new HashMap<>();
        map.put("cats", categoryDao.getAll());

        if (request.queryParams("id") != null) {
            try {
                Product product = productDao.findById(Integer.parseInt(request.queryParams("id")));
                if (product != null) {
                    if (product.getMainImage() == null)
                        product.setMainImage("/image/test.jpg");
                    map.put("product", product);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.type(MediaType.HTML_UTF_8.toString());
        String body = ViewUtils.render(Path.Template.PRODUCT, map);
        Cache.putAsync(Hash.hashRequest(request, Hash.HashType.FAST), body);

        return body;
    }


}
