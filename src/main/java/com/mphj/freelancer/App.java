package com.mphj.freelancer;

import com.mphj.freelancer.config.AppConfig;
import com.mphj.freelancer.index.IndexController;
import com.mphj.freelancer.index.ProductsListController;
import com.mphj.freelancer.index.ShoppingCardController;
import com.mphj.freelancer.index.admin.AdminCategoryController;
import com.mphj.freelancer.index.admin.AdminGiftCardsController;
import com.mphj.freelancer.index.admin.AdminIndexController;
import com.mphj.freelancer.index.admin.AdminProductsController;
import com.mphj.freelancer.mocks.MockedRLocalCache;
import com.mphj.freelancer.utils.AppProperties;
import com.mphj.freelancer.utils.Cache;
import com.mphj.freelancer.utils.HibernateUtils;
import com.mphj.freelancer.utils.Redis;
import com.qmetric.spark.authentication.AuthenticationDetails;
import com.qmetric.spark.authentication.BasicAuthenticationFilter;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        initRedis();
        initDB();
        final int portNumber = (args.length >= 1) ? Integer.parseInt(args[0]) : 8090;
        port(portNumber);
        staticFiles.location("/public");

        before(
                "/admin/*",
                new BasicAuthenticationFilter(
                        new AuthenticationDetails(AppConfig.ADMIN_USERNAME, AppConfig.ADMIN_PASSWORD)
                )
        );

        get("/", IndexController::index);
        get("/upload/:filename", IndexController::serveUploadFiles);
        get("/products", ProductsListController::index);
        get("/products/view", ProductsListController::view);
        get("/shc", ShoppingCardController::view);

        get("/admin", AdminIndexController::index);

        get("/admin/categories", AdminCategoryController::index);
        post("/admin/categories", AdminCategoryController::modify);
        delete("/admin/categories", AdminCategoryController::delete);

        get("/admin/products", AdminProductsController::index);
        get("/admin/products/new", AdminProductsController::newProduct);
        post("/admin/products/new", AdminProductsController::postProduct);

        get("/admin/giftcards", AdminGiftCardsController::index);
        post("/admin/giftcards", AdminGiftCardsController::postGiftcard);
    }


    public static void initRedis() {
        if (AppProperties.isRedisEnabled()) {
            Redis.init();
            Cache.init(Redis.getInstance().getLocalCachedMap("any", Cache.options()));
        } else {
            Cache.init(new MockedRLocalCache());
        }
    }

    public static void initDB() {
        HibernateUtils.getSessionFactory().getCurrentSession();
    }

}
