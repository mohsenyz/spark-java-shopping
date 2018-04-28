package com.mphj.freelancer;

import com.mphj.freelancer.config.AppConfig;
import com.mphj.freelancer.index.*;
import com.mphj.freelancer.index.admin.*;
import com.mphj.freelancer.mocks.MockedRLocalCache;
import com.mphj.freelancer.sockets.DelivererWebSocket;
import com.mphj.freelancer.utils.AppProperties;
import com.mphj.freelancer.utils.Cache;
import com.mphj.freelancer.utils.HibernateUtils;
import com.mphj.freelancer.utils.Redis;
import com.qmetric.spark.authentication.AuthenticationDetails;
import com.qmetric.spark.authentication.BasicAuthenticationFilter;
import spark.Filter;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        initRedis();
        initDB();

        final int portNumber = (args.length >= 1) ? Integer.parseInt(args[0]) : 8090;

        webSocket("/ws/deliverers", DelivererWebSocket.class);

        port(portNumber);
        staticFiles.location("/public");

        before(
                "/admin/*",
                new BasicAuthenticationFilter(
                        new AuthenticationDetails(AppConfig.ADMIN_USERNAME, AppConfig.ADMIN_PASSWORD)
                )
        );

        before("/*", new Filter() {
            @Override
            public void handle(Request request, Response response) throws Exception {
                if (request != null && request.queryParams("token") != null) {
                    response.cookie("token", request.queryParams("token"), 60 * 60 * 60);
                }
            }
        });

        get("/", IndexController::index);
        get("/upload/:filename", IndexController::serveUploadFiles);
        get("/products", ProductsListController::index);
        get("/products/view", ProductsListController::view);
        get("/shc", ShoppingCardController::view);
        post("/shc", ShoppingCardController::newShoppingCard);

        get("/admin", AdminIndexController::index);

        get("/admin/categories", AdminCategoryController::index);
        post("/admin/categories", AdminCategoryController::modify);
        delete("/admin/categories", AdminCategoryController::delete);

        get("/admin/products", AdminProductsController::index);
        get("/admin/products/new", AdminProductsController::newProduct);
        post("/admin/products/new", AdminProductsController::postProduct);

        get("/admin/giftcards", AdminGiftCardsController::index);
        post("/admin/giftcards", AdminGiftCardsController::postGiftcard);

        get("/admin/shoppingcards", AdminShoppingCardController::index);
        get("/admin/shoppingcards/deliverer", AdminShoppingCardController::setDeliverer);

        get("/admin/editpage", AdminPageController::view);
        post("/admin/editpage", AdminPageController::edit);

        get("/admin/deliverers", AdminDeliverersController::index);
        post("/admin/deliverers", AdminDeliverersController::postDeliverer);
        delete("/admin/deliverers", AdminDeliverersController::deleteDeliverer);

        get("/user/verify", UserController::verifyUser);
        get("/user/new", UserController::newUser);
        get("/user/profile", UserController::viewShoppingCards);
        get("/user/map", UserController::viewMap);
        get("/user/shc/:id/done", ShoppingCardController::setShoppingCardDone);

        get("/complaints", ComplaintsController::index);
        post("/complaints", ComplaintsController::post);

        get("/page", PageController::view);
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
