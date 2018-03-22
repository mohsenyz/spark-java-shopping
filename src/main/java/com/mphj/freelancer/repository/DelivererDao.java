package com.mphj.freelancer.repository;

import com.mphj.freelancer.repository.models.Deliverer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
}
