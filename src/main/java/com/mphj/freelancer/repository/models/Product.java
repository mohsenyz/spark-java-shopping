package com.mphj.freelancer.repository.models;


import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private String mainImage;
    private String slider1Image;
    private String slider2Image;
    private String slider3Image;
    private String slider4Image;
    private int count;

}
