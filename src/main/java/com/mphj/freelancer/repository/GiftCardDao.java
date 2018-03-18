package com.mphj.freelancer.repository;

import com.mphj.freelancer.repository.models.GiftCard;
import org.hibernate.SessionFactory;

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
}
