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
public class EnrollmentServiceImplTest extends TestCase {

    @Autowired
    private EnrollmentService enrollmentService;

    @Test
    public void testEnrollment(){
        Assert.assertEquals(true, enrollmentService.isEnrolled("Henry"));
    }
}
