package com.ebay.shipping.dao;

import com.ebay.shipping.model.NumericRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstraintRuleRepository extends JpaRepository<NumericRule, Long> {

    NumericRule findByName(String name);
}
