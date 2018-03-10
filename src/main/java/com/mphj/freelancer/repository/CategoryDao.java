package com.mphj.freelancer.repository;

import com.mphj.freelancer.repository.models.Category;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDao {

    private Session session;

    public CategoryDao (Session session) {
        this.session = session;
    }


    public void save(Category category) {
        Transaction tr = session.beginTransaction();
        if (category.getId() == 0) {
            session.save(category);
        } else {
            session.update(category);
        }
        tr.commit();
    }


    public void delete(Category category) {
        Transaction tr = session.beginTransaction();
        session.delete(category);
        tr.commit();
    }


    public Map<Category, List<Category>> getAll() {
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
        return map;
    }
}
