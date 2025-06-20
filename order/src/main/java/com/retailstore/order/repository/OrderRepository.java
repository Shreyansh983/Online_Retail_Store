package com.retailstore.order.repository;


import com.retailstore.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	public Optional<List<Order>> findByCustomerId(Long customerId);
}
