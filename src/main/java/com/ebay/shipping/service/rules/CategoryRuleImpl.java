package com.ebay.shipping.service.rules;

import com.ebay.shipping.model.Item;
import com.ebay.shipping.service.CategoryService;

public class CategoryRuleImpl implements ValidationRule<Item> {

    private CategoryService categoryService;

    public CategoryRuleImpl(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Override
    public String name() {
        return getClass().getName();
    }

    @Override
    public boolean isSatisfied(Item item) {
        return categoryService.isPreApprovedCategory(item.getCategory());
    }

}
