package com.ebay.shipping.model;

public class RulePOJO {

    public RulePOJO(String name){
        this.name = name;
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
