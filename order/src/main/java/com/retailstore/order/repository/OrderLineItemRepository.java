package com.retailstore.order.repository;


import com.retailstore.order.entity.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, Long>{

	public List<OrderLineItem> findByOrderContaining(Long orderId);
}

