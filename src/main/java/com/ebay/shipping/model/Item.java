package com.ebay.shipping.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Item {

    private String title;
    private String seller;
    private int category;
    private double price;
    private boolean isEligible;

    public boolean isEligible() {
        return isEligible;
    }

    public void setEligible(boolean eligible) {
        isEligible = eligible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @JsonIgnore
    public boolean isValid(){
        if (title == null || seller == null || category == 0) return false;
        return true;
    }
}
