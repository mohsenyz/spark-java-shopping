package com.mphj.freelancer.utils;

import java.io.File;
import java.nio.file.Paths;

public class Path {

    public static final String TEMPLATE_PATH = "/template/";

    public static class Dir {
        public static final File UPLOAD_DIR;
        static {
            if (AppProperties.getProp().getProperty("app.debug_mode").trim().equals("true")) {
                UPLOAD_DIR = Paths.get("src", "main", "resources", "public", "upload").toFile();
            } else {
                UPLOAD_DIR = new File("/uploads");
            }
        }
        public static final File PAGE_DIR = Paths.get("src", "main", "resources").toFile();
    }

    public static class Template {

        public static final String INDEX = TEMPLATE_PATH + "index.vm";
        public static final String ADMIN_MANAGE_CATEGORIES = TEMPLATE_PATH + "admin_cats.vm";
        public static final String ADMIN_INDEX = TEMPLATE_PATH + "admin.vm";
        public static final String ADMIN_LIST_PRODUCTS = TEMPLATE_PATH + "admin_products.vm";
        public static final String ADMIN_NEW_PRODUCT = TEMPLATE_PATH + "admin_new_product.vm";
        public static final String ADMIN_GIFT_CARDS = TEMPLATE_PATH + "admin_giftcards.vm";
        public static final String ADMIN_DELIVERERS = TEMPLATE_PATH + "admin_deliverers.vm";
        public static final String ADMIN_SHOPPINGCARDS = TEMPLATE_PATH + "admin_shoppingcards.vm";
        public static final String LIST_PRODUCTS = TEMPLATE_PATH + "product_list.vm";
        public static final String PRODUCT = TEMPLATE_PATH + "product.vm";
        public static final String SHOPPING_CARD = TEMPLATE_PATH + "shopping_card.vm";
        public static final String USER_SHOPPING_CARDS = TEMPLATE_PATH + "user_shoppingcards.vm";
        public static final String USER_MAP = TEMPLATE_PATH + "user_map.vm";
        public static final String USER_COMPLAINTS = TEMPLATE_PATH + "complaints.vm";
        public static final String USER_VIEW_PAGE = TEMPLATE_PATH + "view_page.vm";
        public static final String ADMIN_EDIT_PAGE = TEMPLATE_PATH + "admin_editpage.vm";

    }

}
