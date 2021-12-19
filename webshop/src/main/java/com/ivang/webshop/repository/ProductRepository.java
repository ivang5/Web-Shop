package com.ivang.webshop.repository;

import java.util.List;

import com.ivang.webshop.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p JOIN p.sales s WHERE s.id = ?1")
    List<Product> findBySale(Long id);
}
