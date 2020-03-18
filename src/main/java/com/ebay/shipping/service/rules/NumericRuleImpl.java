package com.ebay.shipping.service.rules;

import com.ebay.shipping.model.Item;
import com.ebay.shipping.model.NumberOperator;
import com.ebay.shipping.model.NumericRule;

import java.lang.reflect.Field;

public class NumericRuleImpl implements ConstraintRule<Item> {

    public NumericRuleImpl(NumericRule numericRule){
        this.numericRule = numericRule;
    }
    private NumericRule numericRule;

    @Override
    public boolean isSatisfied(Item item) {
        Class<?> c = item.getClass();
        Double attributeValue = null;
        try {
            Field f = c.getDeclaredField(numericRule.getAttributeName());
            f.setAccessible(true);
            attributeValue = (Double) f.get(item);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        switch (NumberOperator.valueOf(numericRule.getOperator())){
            case GREATER_THAN:
                return attributeValue > numericRule.getValue();
            case LESS_THAN:
                return attributeValue < numericRule.getValue();
            case GREATER_EQUAL:
                return attributeValue >= numericRule.getValue();
            case LESS_EQUAL:
                return attributeValue <= numericRule.getValue();
            case EQUAL:
                return attributeValue == numericRule.getValue();
            default:
                return false;

        }
    }

    @Override
    public String name() {
        return numericRule.getName();
    }
}
