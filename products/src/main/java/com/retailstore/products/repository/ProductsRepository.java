package com.retailstore.products.repository;

import com.retailstore.products.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Long> {

    Products findByProductId(Long productId);
}
