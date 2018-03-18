package com.mphj.freelancer.repository;

import com.mphj.freelancer.repository.models.ProductPrice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;

public class ProductPriceDao extends BaseDao<ProductPrice> {


    public ProductPriceDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public void save(ProductPrice productPrice) {
        if (productPrice.getId() == 0) {
            super.save(productPrice);
        } else {
            super.update(productPrice);
        }
    }


    public ProductPrice findLatestByProductId(int productId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM ProductPrice P WHERE P.productId = :productId ORDER BY P.createdAt DESC");
        query.setMaxResults(1);
        query.setParameter("productId", productId);
        ProductPrice productPrice = (ProductPrice) query.getSingleResult();
        session.getTransaction().commit();
        return productPrice;
    }

}
