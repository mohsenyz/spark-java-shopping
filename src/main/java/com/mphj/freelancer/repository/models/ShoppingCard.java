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
    private String address;
    private String email;
    private String description;
    private String userName;
    private String token;
    private long payedAt;
    private int delivererId;
    private boolean isDelivered;

    @Transient
    private User user;

    @Transient
    private Deliverer deliverer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getUsedCard() {
        return usedCard;
    }

    public void setUsedCard(int usedCard) {
        this.usedCard = usedCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getPayedAt() {
        return payedAt;
    }

    public void setPayedAt(long payedAt) {
        this.payedAt = payedAt;
    }

    public int getDelivererId() {
        return delivererId;
    }

    public void setDelivererId(int delivererId) {
        this.delivererId = delivererId;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Deliverer getDeliverer() {
        return deliverer;
    }

    public void setDeliverer(Deliverer deliverer) {
        this.deliverer = deliverer;
    }
}
