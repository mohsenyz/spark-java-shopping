package com.mphj.freelancer.repository;

import com.mphj.freelancer.repository.models.Category;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDao extends BaseDao {

    public CategoryDao (SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    private static Map<Category, List<Category>> cachedMap = null;

    public static Map<Category, List<Category>> getCache() {
        return cachedMap;
    }

    public static void setCache(Map<Category, List<Category>> map) {
        cachedMap = map;
    }

    public static void invalidateCache() {
        cachedMap = null;
    }


    public void save(Category category) {
        if (category.getId() == 0) {
            super.save(category);
        } else {
            super.update(category);
        }
        invalidateCache();
    }



    public Map<Category, List<Category>> getAll() {
        if (getCache() != null)
            return getCache();
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.beginTransaction();
        Query query = session.createQuery("FROM Category c");
        List<Category> list = query.getResultList();
        Map<Category, List<Category>> map = new HashMap<>();
        Map<Integer, Category> mapHelper = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            Category category = list.get(i);
            if (category.getParentId() == -1) {
                map.put(category, new ArrayList<>());
                mapHelper.put(category.getId(), category);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            Category category = list.get(i);
            if (category.getParentId() != -1)
                map.get(mapHelper.get(category.getParentId())).add(category);
        }
        tr.commit();
        setCache(map);
        return map;
    }


    public Category findById(int categoryId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Category C WHERE C.id = :id");
        query.setParameter("id", categoryId);
        Category category = (Category) query.getSingleResult();
        session.getTransaction().commit();
        return category;
    }
}
