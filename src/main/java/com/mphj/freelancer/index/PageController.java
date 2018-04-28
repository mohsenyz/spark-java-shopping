package com.mphj.freelancer.index;

import com.mphj.freelancer.repository.CategoryDao;
import com.mphj.freelancer.utils.DeviceUtils;
import com.mphj.freelancer.utils.HibernateUtils;
import com.mphj.freelancer.utils.Path;
import com.mphj.freelancer.utils.ViewUtils;
import spark.Request;
import spark.Response;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PageController {

    public static String view(Request request, Response response) {
        String page = request.queryParams("page");
        if (page == null || page.trim().isEmpty()) {
            response.redirect("/");
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File(
                                            Path.Dir.PAGE_DIR,
                                            page
                                    )
                            )
                    )
            );
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line)
                        .append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/");
            return null;
        }

        CategoryDao categoryDao = new CategoryDao(HibernateUtils.getSessionFactory());
        Map<String, Object> map = new HashMap<>();
        DeviceUtils.handleMobile(map, request);
        map.put("pageTitle", request.queryParams("title"));
        map.put("pageContent", stringBuilder.toString());
        map.put("cats", categoryDao.getAll());
        return ViewUtils.render(Path.Template.USER_VIEW_PAGE, map);
    }

}
