package com.mphj.freelancer.repository.models;


import javax.persistence.*;

@Entity
@Table(name = "deliverer")
public class Deliverer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phone;
    private String password;
    private double lat;
    private double lng;

}
