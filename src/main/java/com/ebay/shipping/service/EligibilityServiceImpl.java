package com.ebay.shipping.service;

import com.ebay.shipping.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class EligibilityServiceImpl implements EligibilityService<Item>{

    final static Logger logger = LoggerFactory.getLogger(EligibilityServiceImpl.class);

    @Inject
    private RuleEngineService ruleEngineService;

    public boolean isEligible(Item item){
        return ruleEngineService.process(item);
    }
}
