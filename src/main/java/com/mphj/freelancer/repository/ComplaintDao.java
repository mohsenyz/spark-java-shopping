package com.mphj.freelancer.repository;

import com.mphj.freelancer.repository.models.Complaint;
import com.mphj.freelancer.repository.models.User;
import org.hibernate.SessionFactory;

public class ComplaintDao extends BaseDao<Complaint> {


    public ComplaintDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public void save(Complaint complaint) {
        if (complaint.getId() == 0) {
            super.save(complaint);
        } else {
            super.update(complaint);
        }
    }
}
