package com.mphj.freelancer.repository.models;


import javax.persistence.*;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phone;
    private String verificationCode;
    private long createdAt;
    private long verificationCodeTime;
    private String token;
}
