package com.retailstore.cart.repository;

import com.retailstore.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository <Cart,Long>{

    Optional<Object> findByCustomerId(Long customerId);
}
