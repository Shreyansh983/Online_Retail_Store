package com.retailstore.customer.repository;

import com.retailstore.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Customer findByEmail(String email);


    @Query("SELECT c FROM Customer c WHERE " + "(:email IS NULL OR c.email = :email) AND " + "(:phoneNumber IS NULL OR c.phoneNumber = :phoneNumber)")
    List<Customer> findByEmailAndPhoneNumber(String email, String phoneNumber);
}
