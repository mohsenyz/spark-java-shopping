package com.mphj.freelancer.repository;

import com.mphj.freelancer.repository.models.Product;
import com.mphj.freelancer.repository.models.ProductProperty;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class ProductPropertyDao extends BaseDao<ProductProperty> {


    public ProductPropertyDao (SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public void save(ProductProperty productProperty) {
        if (productProperty.getId() == 0) {
            super.save(productProperty);
        } else {
            super.update(productProperty);
        }
    }


    public List<ProductProperty> findByProductId(int productId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM ProductProperty P WHERE P.productId = :productId");
        query.setParameter("productId", productId);
        List<ProductProperty> list = query.getResultList();
        session.getTransaction().commit();
        return list;
    }


    public void deleteByProductId(int productId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE ProductProperty P WHERE P.productId = :productId");
        query.setParameter("productId", productId);
        query.executeUpdate();
        session.getTransaction().commit();
    }

}
