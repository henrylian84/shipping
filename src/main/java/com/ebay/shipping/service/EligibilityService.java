package com.ebay.shipping.service;

/**
 *
 * core service to check if the shipping program is eligible
 *
 * @param <T>
 */
public interface EligibilityService<T> {
    public boolean isEligible(T t);
}
