package com.ebay.shipping.service;


import com.ebay.shipping.exception.ConflictResultException;
import com.ebay.shipping.exception.EmptyResultException;
import com.ebay.shipping.model.Seller;

import java.util.List;

/**
 * checking if the seller is enrolled
 *
 * admin related methods live here too
 *
 */
public interface EnrollmentService {

    boolean isEnrolled(String sellerName);

    List<Seller> getAllEnrolledSellers();

    Seller enrollSeller(String sellerName) throws ConflictResultException;

    void removeEnrolledSellerById(Long id) throws EmptyResultException;
}
