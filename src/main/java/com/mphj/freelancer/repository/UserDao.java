package com.mphj.freelancer.repository;

import com.mphj.freelancer.repository.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;

public class UserDao extends BaseDao<User> {

    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public void save(User user) {
        if (user.getId() == 0) {
            super.save(user);
        } else {
            super.update(user);
        }
    }


    public User findByPhone(String phone) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User U WHERE U.phone = :phone");
        query.setMaxResults(1);
        query.setParameter("phone", phone);
        session.getTransaction().commit();
        return (User) query.getSingleResult();
    }
}
