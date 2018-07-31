package com.gordeev.onlinestore.entity;

public class Product {
    private int id;
    private String name;
    private double priceUah;
    private double priceUsd;
    private String description;
    private String imgLink;

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

    public double getPriceUah() {
        return priceUah;
    }

    public void setPriceUah(double priceUah) {
        this.priceUah = priceUah;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priceUah=" + priceUah +
                ", description='" + description + '\'' +
                '}';
    }
}
