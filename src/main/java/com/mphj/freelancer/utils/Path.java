package com.mphj.freelancer.utils;

import java.io.File;
import java.nio.file.Paths;

public class Path {

    public static final String TEMPLATE_PATH = "/template/";

    public static class Dir {
        public static final File UPLOAD_DIR = Paths.get("src", "main", "resources", "public", "upload").toFile();
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

    }

}
