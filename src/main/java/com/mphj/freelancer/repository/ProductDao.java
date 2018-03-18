package com.mphj.freelancer.repository;

import com.mphj.freelancer.repository.models.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class ProductDao extends BaseDao<Product> {

    public ProductDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void save(Product product) {
        if (product.getId() == 0) {
            super.save(product);
        } else {
            super.update(product);
        }
    }


    public List<Product> findByCategory(int categoryId) {
        CategoryDao categoryDao = new CategoryDao(sessionFactory);
        ProductPriceDao productPriceDao = new ProductPriceDao(sessionFactory);

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Product P WHERE P.categoryId = :categoryId");
        query.setParameter("categoryId", categoryId);
        List<Product> list = query.getResultList();
        session.getTransaction().commit();
        for (Product product : list) {
            if (product.getMainImage() == null) {
                product.setMainImage("/image/test.jpg");
            }
            product.setCategory(categoryDao.findById(product.getCategoryId()));
            product.setProductPrice(productPriceDao.findLatestByProductId(product.getId()));
        }
        return list;
    }


    public Product findById(int productId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Product P WHERE P.id = :id");
        query.setParameter("id", productId);
        Product product = (Product) query.getSingleResult();
        session.getTransaction().commit();

        CategoryDao categoryDao = new CategoryDao(sessionFactory);
        ProductPriceDao productPriceDao = new ProductPriceDao(sessionFactory);
        ProductPropertyDao productPropertyDao = new ProductPropertyDao(sessionFactory);

        product.setCategory(categoryDao.findById(product.getCategoryId()));
        product.setProductPrice(productPriceDao.findLatestByProductId(product.getId()));
        product.setProductProperties(productPropertyDao.findByProductId(product.getId()));
        return product;
    }

}
