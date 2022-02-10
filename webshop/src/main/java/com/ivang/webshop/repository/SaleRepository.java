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

    @Query(value = "SELECT s.* FROM sales s JOIN product_sale ps ON s.id = ps.sale_id JOIN products p ON ps.product_id = p.id WHERE p.id = ?1 AND NOW() > s.from_date AND NOW() < s.to_date GROUP BY p.id", nativeQuery = true)
    Sale findOneByProduct(Long id);
}
