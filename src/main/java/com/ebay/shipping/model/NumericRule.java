package com.ebay.shipping.model;

import javax.persistence.*;

@Entity
@Table(name="numeric_rules")
public class NumericRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String name;
    @Column(name = "attribute_name")
    private String attributeName;
    private String operator;
    private Double value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean isValid(){
        NumberOperator op = null;
        try{
            op = NumberOperator.valueOf(operator);
        }catch (Exception e){

        }

        if (name == null || attributeName == null || value == 0
        || op == null ) return false;
        return true;
    }
}
