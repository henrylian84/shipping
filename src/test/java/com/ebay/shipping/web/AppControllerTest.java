package com.ebay.shipping.web;

import com.ebay.shipping.model.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testEligibleTrue(){
        Item item = new Item();
        item.setSeller("Henry");
        item.setPrice(161);
        item.setCategory(4);
        item.setTitle("Wipes");
        List<Item> list = new ArrayList<>();
        list.add(item);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity<List> request = new HttpEntity<>(list, headers);
        ResponseEntity<String> result = restTemplate.postForEntity("/api/shipping/eligible", request, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("[{\"title\":\"Wipes\",\"seller\":\"Henry\",\"category\":4,\"price\":161.0,\"eligible\":true}]", result.getBody());
    }

    @Test
    public void testEligibleFalse(){
        Item item = new Item();
        item.setSeller("Henry");
        item.setPrice(161);
        item.setCategory(10);
        item.setTitle("Wipes");
        List<Item> list = new ArrayList<>();
        list.add(item);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity<List> request = new HttpEntity<>(list, headers);
        ResponseEntity<String> result = restTemplate.postForEntity("/api/shipping/eligible", request, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("[{\"title\":\"Wipes\",\"seller\":\"Henry\",\"category\":10,\"price\":161.0,\"eligible\":false}]", result.getBody());
    }

    @Test
    public void testInvalidRequest(){
        //missing seller name
        Item item = new Item();
        item.setPrice(161);
        item.setCategory(4);
        item.setTitle("Wipes");
        List<Item> list = new ArrayList<>();
        list.add(item);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity<List> request = new HttpEntity<>(list, headers);
        ResponseEntity<String> result = restTemplate.postForEntity("/api/shipping/eligible", request, String.class);

        Assert.assertEquals(400, result.getStatusCodeValue());
    }

}
