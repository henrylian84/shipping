package com.ebay.shipping.service;

import com.ebay.shipping.exception.ConflictResultException;
import com.ebay.shipping.exception.EmptyResultException;
import com.ebay.shipping.model.Category;

import java.util.List;

/**
 * checking if the category is pre-approved
 *
 * admin related methods live here too
 *
 */
public interface CategoryService {

    boolean isPreApprovedCategory(Integer categoryId);

    List<Category> getAllCategories();

    void addCategory(Integer categoryId) throws ConflictResultException;

    void removeCategory(Integer categoryId);
}
