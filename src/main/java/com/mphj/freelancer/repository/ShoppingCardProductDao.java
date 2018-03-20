package com.mphj.freelancer.repository;

import com.mphj.freelancer.repository.models.ShoppingCardProduct;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ShoppingCardProductDao extends BaseDao<ShoppingCardProduct> {

    public ShoppingCardProductDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void save(List<ShoppingCardProduct> shoppingCardProducts) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        for (ShoppingCardProduct shoppingCardProduct : shoppingCardProducts) {
            if (shoppingCardProduct.getId() == 0) {
                session.save(shoppingCardProduct);
            } else {
                session.update(shoppingCardProduct);
            }
        }

        session.getTransaction().commit();
    }
}
