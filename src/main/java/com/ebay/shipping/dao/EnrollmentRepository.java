package com.ebay.shipping.dao;

import com.ebay.shipping.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Seller, Long> {

    Seller findByName(String name);
    Long deleteByName(String name);
}
