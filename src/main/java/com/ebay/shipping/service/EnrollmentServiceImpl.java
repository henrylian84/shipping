package com.ebay.shipping.service;

import com.ebay.shipping.dao.EnrollmentRepository;
import com.ebay.shipping.exception.ConflictResultException;
import com.ebay.shipping.exception.EmptyResultException;
import com.ebay.shipping.model.Seller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService{

    final static Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

    @Inject
    private EnrollmentRepository enrollmentRepository;

    public boolean isEnrolled(String name){
        Seller seller = enrollmentRepository.findByName(name.toUpperCase());
        if (seller == null ) return false;
        else
        return true;
    }

    @Override
    public List<Seller> getAllEnrolledSellers() {
        return enrollmentRepository.findAll();
    }

    @Override
    public Seller enrollSeller(String sellerName) throws ConflictResultException {
        Seller conflict = enrollmentRepository.findByName(sellerName.toUpperCase());
        if(conflict != null){
            logger.error("result exists");
            throw new ConflictResultException("sellerName already exists");
        } else{
            Seller seller = new Seller();
            seller.setName(sellerName.toUpperCase());
            return enrollmentRepository.save(seller);
        }
    }

    @Override
    @Transactional
    public void removeEnrolledSellerById(Long id) throws EmptyResultException {
        try{
            enrollmentRepository.deleteById(id);
        } catch (Exception e){
            logger.error("Deletion failed ", e);
            throw new EmptyResultException("Result not found");
        }
    }
}
