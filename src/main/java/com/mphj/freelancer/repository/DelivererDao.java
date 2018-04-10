package com.mphj.freelancer.repository;

import com.mphj.freelancer.repository.models.Deliverer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class DelivererDao extends BaseDao<Deliverer> {

    public DelivererDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    @Override
    public void save(Deliverer deliverer) {
        if (deliverer.getId() == 0) {
            super.save(deliverer);
        } else {
            super.update(deliverer);
        }
    }


    public List<Deliverer> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Deliverer> list = session.createQuery("FROM Deliverer").list();

        session.getTransaction().commit();
        return list;
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query query = session.createQuery("DELETE Deliverer D WHERE D.id = :id");
        query.setParameter("id", id);
        try {
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }


    public Deliverer findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Deliverer D WHERE D.id = :id");
        query.setMaxResults(1);
        query.setParameter("id", id);

        Deliverer deliverer = null;

        try {
            deliverer = (Deliverer) query.getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return deliverer;
    }


    public Deliverer findByPhoneAndPassword(String phone, String password) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Deliverer D WHERE D.phone = :phone AND D.password = :password");
        query.setMaxResults(1);
        query.setParameter("phone", phone);
        query.setParameter("password", password);

        Deliverer deliverer = null;

        try {
            deliverer = (Deliverer) query.getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return deliverer;
    }
}
