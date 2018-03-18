package com.mphj.freelancer.index.admin;

import com.google.common.net.MediaType;
import com.mphj.freelancer.utils.Cache;
import com.mphj.freelancer.utils.Hash;
import com.mphj.freelancer.utils.Path;
import com.mphj.freelancer.utils.ViewUtils;
import spark.Request;
import spark.Response;

public class AdminIndexController {

    public static String index(Request request, Response response) {
        String requestHash = Hash.hashRequest(request);
        if (Cache.exists(requestHash)) {
            response.type(MediaType.HTML_UTF_8.toString());
            return (String) Cache.get(requestHash);
        }

        response.type(MediaType.HTML_UTF_8.toString());
        String body = ViewUtils.render(Path.Template.ADMIN_INDEX);
        Cache.putAsync(Hash.hashRequest(request), body);

        return body;
    }

}
