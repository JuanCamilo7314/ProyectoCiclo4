package com.g104g5.appciclo4.Entities;

import java.util.Date;
import java.util.UUID;

public class Product {
    private String id;
    private String image;
    private String name;
    private String description;
    private int price;
    private boolean deleted;
    private Date updatedAt;
    private Date createdAt;
    private Double latitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    private Double longitude;

    public Product(String id, String name, String description, int price, String image, Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude= longitude;
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.deleted = false;
    }

    public Product(String name, String description, int price, String image, Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude= longitude;
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.deleted = false;
    }

    public Product(String id, String name, String description, int price, String image, Boolean deleted, Date createdAt, Date updatedAt, Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude= longitude;
        this.id= id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deleted = deleted;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
