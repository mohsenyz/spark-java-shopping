package com.mphj.freelancer.repository.models;


import javax.persistence.*;

@Entity
@Table(name = "gift_card")
public class GiftCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String serial;
    private long createdAt;
    private int value;
    private long expiredAt;
    private int initialValue;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(long expiredAt) {
        this.expiredAt = expiredAt;
    }


    public int getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(int initialValue) {
        this.initialValue = initialValue;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
