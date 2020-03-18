package com.ebay.shipping.service;

import com.ebay.shipping.dao.ConstraintRuleRepository;
import com.ebay.shipping.exception.ConflictResultException;
import com.ebay.shipping.exception.EmptyResultException;
import com.ebay.shipping.model.Item;
import com.ebay.shipping.model.NumericRule;
import com.ebay.shipping.model.RulePOJO;
import com.ebay.shipping.service.rules.CategoryRuleImpl;
import com.ebay.shipping.service.rules.EnrollmentRuleImpl;
import com.ebay.shipping.service.rules.NumericRuleImpl;
import com.ebay.shipping.service.rules.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;

@Service
public class RuleEngineServiceImpl implements RuleEngineService<Item> {

    final static Logger logger = LoggerFactory.getLogger(RuleEngineServiceImpl.class);

    private Map<String, Rule> rules;

    @Inject
    private EnrollmentService enrollmentService;
    @Inject
    private CategoryService categoryService;
    @Inject
    private ConstraintRuleRepository constraintRuleRepository;

    @PostConstruct
    public void init(){
        rules = new HashMap<>();
        //initiate validation rules
        logger.info("loading validation rules");
        register(new EnrollmentRuleImpl(enrollmentService));
        register(new CategoryRuleImpl(categoryService));

        //initiate numeric constraint rules
        logger.info("loading numeric constraint rules");
        List<NumericRule> list = constraintRuleRepository.findAll();
        list.stream().forEach(numericRule -> register(new NumericRuleImpl(numericRule)));
    }
    @Override
    public boolean process(Item item){
        boolean result = true;
        for(Rule rule: rules.values()){
            if(!rule.isSatisfied(item)) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public void updateNumericRule(NumericRule numericRule) throws EmptyResultException{
        if(constraintRuleRepository.findById(numericRule.getId()).isPresent()){
            constraintRuleRepository.save(numericRule);
            register(new NumericRuleImpl(numericRule));
        } else{
            logger.error("no record found");
            throw new EmptyResultException("result not found");
        }
    }

    @Override
    public void deleteNumericRuleById(Long id) throws EmptyResultException {

        try{
            constraintRuleRepository.deleteById(id);
        } catch (Exception e){
            logger.error("Deletion failed", e);
            throw new EmptyResultException("Result not found");
        }

        NumericRule rule = constraintRuleRepository.findById(id).get();
        deRegister(rule.getName());

    }

    @Override
    public NumericRule addNumericRule(NumericRule numericRule) throws ConflictResultException {
        NumericRule conflict = constraintRuleRepository.findByName(numericRule.getName());
        if(conflict != null){
            logger.error("result exists");
            throw new ConflictResultException("Name already exists");
        } else{
            NumericRule result = constraintRuleRepository.save(numericRule);
            register(new NumericRuleImpl(result));
            return result;
        }
    }

    @Override
    public List<NumericRule> getAllNumericRules() {
        return constraintRuleRepository.findAll();
    }

    @Override
    public List<RulePOJO> getAllRules(){
        List<RulePOJO> list = new ArrayList<>();
        for(String name: rules.keySet()){
            list.add(new RulePOJO(name));
        }
        return list;
    }

    public void register(Rule rule){
        rules.put(rule.name(), rule);
    }

    public void deRegister(String name){
        rules.remove(name);
    }

}
