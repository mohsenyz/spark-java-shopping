package com.mphj.freelancer.index.admin;

import com.mphj.freelancer.utils.Path;
import com.mphj.freelancer.utils.ViewUtils;
import spark.Request;
import spark.Response;
import spark.utils.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AdminPageController {

    public static String edit(Request request, Response response) {
        String page = request.queryParams("page");
        String content = request.queryParams("content");

        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        try {
            IOUtils.copy(
                    inputStream,
                    new FileOutputStream(
                            new File(Path.Dir.PAGE_DIR, page)
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.redirect("/admin/editpage?page=" + page);
        return null;
    }


    public static String view(Request request, Response response) {
        String page = request.queryParams("page");
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
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line)
                        .append("\n");
            }
        } catch (Exception e) {
        }

        Map<String, Object> map = new HashMap<>();
        map.put("pageContent", (stringBuilder.toString().trim().isEmpty()) ? null : stringBuilder.toString());
        map.put("page", page);
        return ViewUtils.render(Path.Template.ADMIN_EDIT_PAGE, map);
    }



}
