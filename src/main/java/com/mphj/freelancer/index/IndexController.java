package com.mphj.freelancer.index;

import com.google.common.net.MediaType;
import com.mphj.freelancer.repository.CategoryDao;
import com.mphj.freelancer.utils.*;
import spark.Request;
import spark.Response;
import spark.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class IndexController {

    public static String index(Request request, Response response) {
        String requestHash = Hash.hashRequest(request);
        if (Cache.exists(requestHash)) {
            response.type(MediaType.HTML_UTF_8.toString());
            return (String) Cache.get(requestHash);
        }

        CategoryDao categoryDao = new CategoryDao(HibernateUtils.getSessionFactory());
        Map<String, Object> map = new HashMap<>();
        map.put("cats", categoryDao.getAll());

        DeviceUtils.handleMobile(map, request);

        response.type(MediaType.HTML_UTF_8.toString());
        String body = ViewUtils.render(Path.Template.INDEX, map);
        Cache.putAsync(Hash.hashRequest(request, Hash.HashType.FAST), body);

        return body;
    }


    public static Object serveUploadFiles(Request request, Response response) {
        String fileName = request.params("filename");
        File sourceFile = new File(Path.Dir.UPLOAD_DIR, fileName);
        if (!sourceFile.exists()) {
            response.status(404);
            return null;
        }
        try {
            String mimeType = URLConnection.guessContentTypeFromName(sourceFile.getName());
            response.type(mimeType);
            response.header("Cache-Control", "max-age=2592000, public");
            IOUtils.copy(new FileInputStream(sourceFile), response.raw().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
