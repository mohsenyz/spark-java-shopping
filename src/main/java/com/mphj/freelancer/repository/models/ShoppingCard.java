package com.mphj.freelancer.repository.models;

import javax.persistence.*;

@Entity
@Table(name = "shopping_card")
public class ShoppingCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;
    private boolean payed;
    private double price;
    private long createdAt;
    private int usedCard;
    private String address1;
    private String address2;
    private String userName;
    private String token;
    private long payedAt;
    private int delivererId;
    private boolean isDelivered;

}
