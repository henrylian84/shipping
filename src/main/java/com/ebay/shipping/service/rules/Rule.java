package com.ebay.shipping.service.rules;

/**
 *
 * business rule base interface
 *
 * @param <T>
 */
public interface Rule<T> {

    /**
     * identity name for a rule
     *
     * @return
     */
    String name();

    /**
     * function to check if the rule applies to the input t
     *
     * @param t
     * @return
     */
    boolean isSatisfied(T t);

}
