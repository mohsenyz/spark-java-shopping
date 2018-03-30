package com.mphj.freelancer.repository;

import com.mphj.freelancer.repository.models.ShoppingCard;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class ShoppingCardDao extends BaseDao<ShoppingCard> {

    public ShoppingCardDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    @Override
    public void save(ShoppingCard shoppingCard) {
        if (shoppingCard.getId() == 0) {
            super.save(shoppingCard);
        } else {
            super.update(shoppingCard);
        }
    }


    public List<ShoppingCard> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<ShoppingCard> list = session.createQuery("FROM ShoppingCard")
                .list();

        session.getTransaction().commit();
        return list;
    }


    public ShoppingCard findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM ShoppingCard S WHERE S.id = :id");
        query.setMaxResults(1);
        query.setParameter("id", id);
        ShoppingCard user = (ShoppingCard) query.getSingleResult();
        session.getTransaction().commit();
        return user;
    }
}
