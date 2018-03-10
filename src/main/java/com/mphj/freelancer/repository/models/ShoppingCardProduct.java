package com.mphj.freelancer.repository.models;


import javax.persistence.*;

@Entity
@Table(name = "shopping_card_product")
public class ShoppingCardProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int productId;
    private int count;
    private int cardId;

}
