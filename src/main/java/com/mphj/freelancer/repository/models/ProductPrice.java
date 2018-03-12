package com.mphj.freelancer.repository.models;


import javax.persistence.*;

@Entity
@Table(name = "product_price")
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double price = 0;
    private double off = 0;
    private long createdAt;
    private int productId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPrice() {
        return Math.round(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getOff() {
        return Math.round(off);
    }

    public void setOff(double off) {
        this.off = off;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public long getActualPrice() {
        return Math.round(getPrice() * (100 - getOff()) / 100);
    }
}
