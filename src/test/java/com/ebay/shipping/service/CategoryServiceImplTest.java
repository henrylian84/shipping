package com.ebay.shipping.service;


import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest extends TestCase {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testIsPreApproved() {
        boolean result = categoryService.isPreApprovedCategory(4);
        Assert.assertEquals(true, result);
    }

}
