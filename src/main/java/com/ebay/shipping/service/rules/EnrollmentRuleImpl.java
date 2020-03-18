package com.ebay.shipping.service.rules;

import com.ebay.shipping.model.Item;
import com.ebay.shipping.service.EnrollmentService;

public class EnrollmentRuleImpl implements ValidationRule<Item> {

    public EnrollmentRuleImpl(EnrollmentService enrollmentService){
        this.enrollmentService = enrollmentService;
    }

    private EnrollmentService enrollmentService;

    @Override
    public String name() {
        return getClass().getName();
    }

    @Override
    public boolean isSatisfied(Item item) {
        return enrollmentService.isEnrolled(item.getSeller());
    }

}


