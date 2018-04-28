package com.mphj.freelancer.index;

import com.google.common.net.MediaType;
import com.mphj.freelancer.repository.CategoryDao;
import com.mphj.freelancer.repository.ComplaintDao;
import com.mphj.freelancer.repository.models.Complaint;
import com.mphj.freelancer.utils.*;
import org.hibernate.cfg.beanvalidation.HibernateTraversableResolver;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ComplaintsController {

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
        String body = ViewUtils.render(Path.Template.USER_COMPLAINTS, map);
        Cache.putAsync(Hash.hashRequest(request, Hash.HashType.FAST), body);

        return body;
    }


    public static String post(Request request, Response response) {

        String subject = request.queryParams("subject");
        String content = request.queryParams("content");
        String communicationInfo = request.queryParams("com");

        if (communicationInfo == null || communicationInfo.trim().isEmpty()) {
            response.redirect("/complaints?bad_communication_info=1");
            return null;
        }

        if (subject == null || subject.trim().isEmpty()) {
            response.redirect("/complaints?bad_subject=1");
            return null;
        }

        if (content == null || content.trim().isEmpty()) {
            response.redirect("/complaints?bad_content=1");
            return null;
        }

        ComplaintDao complaintDao = new ComplaintDao(HibernateUtils.getSessionFactory());

        Complaint complaint = new Complaint();
        complaint.setCommunicationInfo(communicationInfo);
        complaint.setContent(content);
        complaint.setCreatedAt(System.currentTimeMillis() / 1000l);
        complaint.setSubject(subject);
        complaintDao.save(complaint);
        response.redirect("/complaints?success=1");
        return null;
    }
}