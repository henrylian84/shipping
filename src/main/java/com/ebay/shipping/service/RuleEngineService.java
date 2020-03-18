package com.ebay.shipping.service;


import com.ebay.shipping.exception.ConflictResultException;
import com.ebay.shipping.exception.EmptyResultException;
import com.ebay.shipping.model.NumericRule;
import com.ebay.shipping.model.RulePOJO;

import java.util.List;

/**
 *
 * rules will be loaded and processed in this engine.
 *
 * @param <T>
 */
public interface RuleEngineService<T> {

    /**
     * takes the input and apply rules accordingly
     *
     * @param t
     * @return
     */
    boolean process(T t);

    void updateNumericRule(NumericRule numericRule) throws EmptyResultException;

    void deleteNumericRuleById(Long id) throws EmptyResultException;

    NumericRule addNumericRule(NumericRule numericRule) throws ConflictResultException;

    List<NumericRule> getAllNumericRules();

    List<RulePOJO> getAllRules();
}
