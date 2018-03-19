package com.mphj.freelancer.repository;

import com.mphj.freelancer.repository.models.ShoppingCard;
import org.hibernate.SessionFactory;

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
}
