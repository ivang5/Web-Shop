package com.ivang.webshop.repository;

import java.util.List;

import com.ivang.webshop.entity.Sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT s FROM Sale s JOIN s.products p WHERE p.id = ?1")
    List<Sale> findByProduct(Long id);
}
