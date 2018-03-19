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
        User user = (User) query.getSingleResult();
        session.getTransaction().commit();
        return user;
    }


    public User findByToken(String token) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM User U WHERE U.token = :token");
        query.setParameter("token", token);
        User user = (User) query.getSingleResult();

        session.getTransaction().commit();
        return user;
    }
}
