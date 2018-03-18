package com.mphj.freelancer;

import com.mphj.freelancer.config.AppConfig;
import com.mphj.freelancer.index.IndexController;
import com.mphj.freelancer.index.ProductsListController;
import com.mphj.freelancer.index.ShoppingCardController;
import com.mphj.freelancer.index.admin.AdminCategoryController;
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

        get("/", (req, resp) -> IndexController.index(req, resp));
        get("/upload/:filename", (req, resp) -> IndexController.serveUploadFiles(req, resp));
        get("/products", (req, resp) -> ProductsListController.index(req, resp));
        get("/products/view", (req, resp) -> ProductsListController.view(req, resp));
        get("/shc", (req, resp) -> ShoppingCardController.view(req, resp));

        get("/admin/categories", (req, resp) -> AdminCategoryController.index(req, resp));
        post("/admin/categories", (req, resp) -> AdminCategoryController.modify(req, resp));
        delete("/admin/categories", (req, resp) -> AdminCategoryController.delete(req, resp));


        get("/admin/products", (req, resp) -> AdminProductsController.index(req, resp));
        get("/admin/products/new", (req, resp) -> AdminProductsController.newProduct(req, resp));
        post("/admin/products/new", (req, resp) -> AdminProductsController.postProduct(req, resp));
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
