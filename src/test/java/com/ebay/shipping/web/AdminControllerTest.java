package com.ebay.shipping.web;

import com.ebay.shipping.model.NumericRule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testEnrollSellerHappyPath(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity request = new HttpEntity(headers);
        String name = "Kevin";
        ResponseEntity response = restTemplate.exchange("/api/admin/enrollment/{name}", HttpMethod.PUT, request, (Class<Object>) null, name);

        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testEnrollSellerConflict(){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity request = new HttpEntity(headers);
        String name = "Henry";
        ResponseEntity response = restTemplate.exchange("/api/admin/enrollment/{name}", HttpMethod.PUT, request, (Class<Object>) null, name);

        Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    }

    @Test
    public void testGetEnrolledSellers(){
        ResponseEntity response = restTemplate.getForEntity("/api/admin/enrollment", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testGetAllCategories(){
        ResponseEntity response = restTemplate.getForEntity("/api/admin/category", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteEnrolledSeller(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity request = new HttpEntity(headers);
        Integer id = 1;
        ResponseEntity response = restTemplate.exchange("/api/admin/enrollment/{id}", HttpMethod.DELETE, request, (Class<Object>) null, id);

        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteEnrolledSellerNotFound(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity request = new HttpEntity(headers);
        Integer id = 111;
        ResponseEntity response = restTemplate.exchange("/api/admin/enrollment/{id}", HttpMethod.DELETE, request, (Class<Object>) null, id);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddCategory(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity request = new HttpEntity(headers);
        Integer categoryId = 10;
        ResponseEntity response = restTemplate.exchange("/api/admin/category/{categoryId}", HttpMethod.PUT, request, (Class<Object>) null, categoryId);

        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testAddCategoryConflict(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity request = new HttpEntity(headers);
        Integer categoryId = 4;
        ResponseEntity response = restTemplate.exchange("/api/admin/category/{categoryId}", HttpMethod.PUT, request, (Class<Object>) null, categoryId);

        Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testDeleteCategory(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity request = new HttpEntity(headers);
        Integer categoryId = 4;
        ResponseEntity response = restTemplate.exchange("/api/admin/category/{categoryId}", HttpMethod.DELETE, request, (Class<Object>) null, categoryId);

        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetAllNumericRules(){
        ResponseEntity response = restTemplate.getForEntity("/api/admin/rules/valueCompare", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateNumericRule(){
        NumericRule numericRule = new NumericRule();
        numericRule.setAttributeName("price");
        numericRule.setOperator("LESS_THAN");
        numericRule.setValue(200.0);
        numericRule.setName("price-max");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity<NumericRule> request = new HttpEntity(numericRule, headers);
        ResponseEntity<NumericRule> response = restTemplate.exchange("/api/admin/rules/valueCompare", HttpMethod.PUT, request, NumericRule.class);

        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals("price", request.getBody().getAttributeName());
        Assert.assertEquals("LESS_THAN", request.getBody().getOperator());
        Assert.assertEquals("price-max", request.getBody().getName());
        Assert.assertNotNull(response.getBody().getId());
    }

    @Test
    public void testCreateNumericRuleInValidRequest(){
        NumericRule numericRule = new NumericRule();
        numericRule.setAttributeName("price");
        numericRule.setOperator("IN VALID");
        numericRule.setValue(200.0);
        numericRule.setName("price-max");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity<NumericRule> request = new HttpEntity(numericRule, headers);
        ResponseEntity response = restTemplate.exchange("/api/admin/rules/valueCompare", HttpMethod.PUT, request, (Class<NumericRule>) null);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
