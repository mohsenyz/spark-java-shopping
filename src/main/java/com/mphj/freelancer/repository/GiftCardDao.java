package com.mphj.freelancer.repository;

import com.mphj.freelancer.repository.models.GiftCard;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class GiftCardDao extends BaseDao<GiftCard> {

    public GiftCardDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void save(GiftCard giftCard) {
        if (giftCard.getId() == 0) {
            super.save(giftCard);
        } else {
            super.update(giftCard);
        }
    }


    public List<GiftCard> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<GiftCard> list = session.createQuery("FROM GiftCard").list();

        session.getTransaction().commit();
        return list;
    }


    public GiftCard findBySerial(String serial) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM GiftCard G WHERE G.serial = :serial");
        query.setParameter("serial", serial.trim());
        GiftCard giftCard = (GiftCard) query.getSingleResult();

        session.getTransaction().commit();
        return giftCard;
    }


    public boolean existsBySerial(String serial) {
        try {
            return findBySerial(serial) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
