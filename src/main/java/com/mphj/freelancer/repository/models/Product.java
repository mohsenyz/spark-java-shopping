package com.mphj.freelancer.repository.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private long createdAt;
    private int categoryId;

    @Transient
    private Category category;

    @Transient
    private ProductPrice productPrice;

    @Transient
    private List<ProductProperty> productProperties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getSlider1Image() {
        return slider1Image;
    }

    public void setSlider1Image(String slider1Image) {
        this.slider1Image = slider1Image;
    }

    public String getSlider2Image() {
        return slider2Image;
    }

    public void setSlider2Image(String slider2Image) {
        this.slider2Image = slider2Image;
    }

    public String getSlider3Image() {
        return slider3Image;
    }

    public void setSlider3Image(String slider3Image) {
        this.slider3Image = slider3Image;
    }

    public String getSlider4Image() {
        return slider4Image;
    }

    public void setSlider4Image(String slider4Image) {
        this.slider4Image = slider4Image;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }

    public List<ProductProperty> getProductProperties() {
        return productProperties;
    }

    public void setProductProperties(List<ProductProperty> productProperties) {
        this.productProperties = productProperties;
    }


    public static Product newComplex() {
        Product product = new Product();
        product.setProductProperties(new ArrayList<>());
        product.setCategory(new Category());
        product.setProductPrice(new ProductPrice());
        return product;
    }
}
