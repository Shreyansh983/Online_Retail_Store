package com.retailstore.products.repository;

import com.retailstore.products.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Long> {

    Products findByName(String name);

    @Query("SELECT p FROM Products p WHERE " + "(:name IS NULL OR p.name = :name) AND " + "(:price IS NULL OR p.price = :price)" + " AND " + "(:Category IS NULL OR p.category = :Category)" + " AND " + "(:brand IS NULL OR p.brand = :brand)")
    List<Products> findProductsByNamePriceAndCategory(String name, Double price, String Category, String brand);
}
