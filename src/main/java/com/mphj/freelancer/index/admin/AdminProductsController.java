package com.mphj.freelancer.index.admin;

import com.google.common.net.MediaType;
import com.mphj.freelancer.repository.CategoryDao;
import com.mphj.freelancer.repository.ProductDao;
import com.mphj.freelancer.repository.ProductPriceDao;
import com.mphj.freelancer.repository.ProductPropertyDao;
import com.mphj.freelancer.repository.models.Product;
import com.mphj.freelancer.repository.models.ProductPrice;
import com.mphj.freelancer.repository.models.ProductProperty;
import com.mphj.freelancer.utils.*;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import spark.utils.IOUtils;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class AdminProductsController {

    public static String index(Request request, Response response) {
        String requestHash = Hash.hashRequest(request);

        if (Cache.exists(requestHash)) {
            response.type(MediaType.HTML_UTF_8.toString());
            return (String) Cache.get(requestHash);
        }

        CategoryDao categoryDao = new CategoryDao(HibernateUtils.getSessionFactory());
        ProductDao productDao = new ProductDao(HibernateUtils.getSessionFactory());
        Map<String, Object> map = new HashMap<>();
        map.put("cats", categoryDao.getAll());
        map.put("products", new ArrayList<>());

        if (request.queryParams("cat") != null) {
            System.err.println(request.queryParams("cat"));
            try {
                map.put(
                        "products",
                        productDao.findByCategory(Integer.parseInt(request.queryParams("cat")))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.type(MediaType.HTML_UTF_8.toString());
        String body = ViewUtils.render(Path.Template.ADMIN_LIST_PRODUCTS, map);
        Cache.putAsync(Hash.hashRequest(request, Hash.HashType.FAST), body);

        return body;
    }


    public static String newProduct(Request request, Response response) {
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
                if (product != null)
                    map.put("product", product);
                else
                    map.put("product", Product.newComplex());
            } catch (Exception e) {
                map.put("product", Product.newComplex());
            }
        } else {
            map.put("product", Product.newComplex());
        }

        response.type(MediaType.HTML_UTF_8.toString());
        String body = ViewUtils.render(Path.Template.ADMIN_NEW_PRODUCT, map);
        Cache.putAsync(Hash.hashRequest(request, Hash.HashType.FAST), body);

        return body;
    }


    public static String postProduct(Request request, Response response) {
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

        try {
            request.raw().getParts().forEach(new Consumer<Part>() {
                @Override
                public void accept(Part part) {
                    System.out.println(part.getName());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        String name = request.queryParams("name").trim();
        String description = request.queryParams("description").trim();
        String price = request.queryParams("price").trim();
        String off = request.queryParams("off").trim();
        String count = request.queryParams("count").trim();
        String category = request.queryParams("category").trim();

        int priceValue = 0;
        int offValue = 0;
        int countValue = 0;
        int categoryValue = 0;


        try {
            priceValue = Integer.parseInt(price);
        } catch (Exception e) {
            System.err.println("Invalid number");
        }

        try {
            offValue = Integer.parseInt(off);
        } catch (Exception e) {
            System.err.println("Invalid number");
        }

        try {
            countValue = Integer.parseInt(count);
        } catch (Exception e) {
            System.err.println("Invalid number");
        }

        try {
            categoryValue = Integer.parseInt(category);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Invalid number");
        }

        if (categoryValue == 0) {
            response.redirect("/admin/products/new?bad_cat=1");
            return null;
        }

        String mainImage = null;
        try (InputStream is = request.raw().getPart("main-image").getInputStream()) {
            if (request.raw().getPart("main-image").getSize() == 0) {
                request.raw().getPart("main-image").delete();
            } else {
                String fileName = UUID.randomUUID().toString() + request.raw().getPart("main-image").getSubmittedFileName();
                IOUtils.copy(
                        is,
                        new FileOutputStream(
                                new File(Path.Dir.UPLOAD_DIR, fileName)
                        )
                );
                mainImage = fileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String image1 = null, image2 = null, image3 = null, image4 = null;
        for (int i = 1; i < 5; i++) {
            try (InputStream is = request.raw().getPart("image-" + i).getInputStream()) {
                if (request.raw().getPart("image-" + i).getSize() == 0) {
                    request.raw().getPart("image-" + i).delete();
                    continue;
                }
                String fileName = UUID.randomUUID().toString() + request.raw().getPart("image-" + i).getSubmittedFileName();
                IOUtils.copy(
                        is,
                        new FileOutputStream(
                                new File(Path.Dir.UPLOAD_DIR, fileName)
                        )
                );
                switch (i) {
                    case 1:
                        image1 = fileName;
                        break;
                    case 2:
                        image2 = fileName;
                        break;
                    case 3:
                        image3 = fileName;
                        break;
                    case 4:
                        image4 = fileName;
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        ProductPropertyDao productPropertyDao = new ProductPropertyDao(sessionFactory);
        ProductDao productDao = new ProductDao(sessionFactory);
        ProductPriceDao productPriceDao = new ProductPriceDao(sessionFactory);

        Product product;
        if (request.queryParams("id") != null && !request.queryParams("id").trim().equals("0") && !request.queryParams("id").isEmpty()) {
            product = productDao.findById(Integer.parseInt(request.queryParams("id")));
            productPropertyDao.deleteByProductId(product.getId());
        } else {
            product = new Product();
        }
        product.setName(name);
        product.setDescription(description);
        product.setCount(countValue);
        if (mainImage != null)
            product.setMainImage("/upload/" + mainImage);
        if (image1 != null)
            product.setSlider1Image("/upload/" + image1);
        if (image2 != null)
            product.setSlider2Image("/upload/" + image2);
        if (image3 != null)
            product.setSlider3Image("/upload/" + image3);
        if (image4 != null)
            product.setSlider4Image("/upload/" + image4);
        product.setCreatedAt(System.currentTimeMillis() / 1000l);
        product.setCategoryId(categoryValue);
        productDao.save(product);

        ProductPrice productPrice = new ProductPrice();
        productPrice.setCreatedAt(System.currentTimeMillis() / 1000l);
        productPrice.setOff(offValue);
        productPrice.setPrice(priceValue);
        productPrice.setProductId(product.getId());
        productPriceDao.save(productPrice);

        for (int i = 0; i < 20; i++) {
            String key = request.queryParams("key-" + i);
            String value = request.queryParams("value-" + i);
            if (key != null && !key.isEmpty() && value != null && !value.isEmpty()) {
                ProductProperty productProperty = new ProductProperty();
                productProperty.setProductId(product.getId());
                productProperty.setpKey(key);
                productProperty.setpValue(value);
                productPropertyDao.save(productProperty);
            }
        }

        response.redirect("/admin/products");
        return null;
    }


}
