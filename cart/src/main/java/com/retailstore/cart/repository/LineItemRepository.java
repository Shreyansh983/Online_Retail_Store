package com.retailstore.cart.repository;

import com.retailstore.cart.entity.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem,Long> {
}
