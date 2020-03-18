package com.ebay.shipping.service;

import com.ebay.shipping.dao.CategoryRepository;
import com.ebay.shipping.exception.ConflictResultException;
import com.ebay.shipping.exception.EmptyResultException;
import com.ebay.shipping.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    final static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Inject
    private CategoryRepository categoryRepository;

    public boolean isPreApprovedCategory(Integer categoryId){

        Category cat = categoryRepository.findByCategoryId(categoryId);
        if (cat == null) return false;
        else return true;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(Integer categoryId) throws ConflictResultException {
        Category conflict = categoryRepository.findByCategoryId(categoryId);
        if(conflict != null){
            logger.error("result exists");
            throw new ConflictResultException("categoryId already exists");
        } else{
            Category cat = new Category();
            cat.setCategoryId(categoryId);
            categoryRepository.save(cat);
        }
    }

    @Override
    @Transactional
    public void removeCategory(Integer categoryId) {
        categoryRepository.deleteByCategoryId(categoryId);
    }
}
