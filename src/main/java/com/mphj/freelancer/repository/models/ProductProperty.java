package com.mphj.freelancer.repository.models;


import javax.persistence.*;

@Entity
@Table(name = "product_property")
public class ProductProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int productId;
    private String pKey;
    private String pValue;
}
