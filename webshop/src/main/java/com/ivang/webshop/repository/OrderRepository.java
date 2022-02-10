package com.ivang.webshop.repository;

import java.util.List;

import com.ivang.webshop.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT o.* FROM orders o JOIN buyers b ON o.buyer_id = b.id WHERE b.id = ?1", nativeQuery = true)
    List<Order> getByBuyer(Long id);
    
    @Query(value = "SELECT ROUND(AVG(o.rate), 0) as averageRate FROM orders o JOIN items i ON o.id = i.order_id JOIN products p ON i.product_id = p.id JOIN sellers s ON p.seller_id = s.id WHERE s.id = ?1 AND o.delivered = 1 AND o.rate IS NOT NULL GROUP BY s.id", nativeQuery = true)
    int getRateBySeller(Long id);

    @Query(value = "SELECT COUNT(DISTINCT o.id) as rateNum FROM orders o JOIN items i ON o.id = i.order_id JOIN products p ON i.product_id = p.id JOIN sellers s ON p.seller_id = s.id WHERE s.id = ?1 AND o.delivered = 1 AND o.rate IS NOT NULL GROUP BY s.id", nativeQuery = true)
    int getRatesNumBySeller(Long id);

    @Query(value = "SELECT DISTINCT o.* FROM orders o JOIN items i ON o.id = i.order_id JOIN products p ON i.product_id = p.id JOIN sellers s ON p.seller_id = s.id WHERE s.id = ?1 AND o.delivered = 1 AND o.archived_comment = 0 AND o.rate IS NOT NULL", nativeQuery = true)
    List<Order> findNonArcivedBySeller(Long id);

    @Query(value = "SELECT MAX(o.id) as lastOrder FROM orders o JOIN buyers b ON o.buyer_id = b.id WHERE b.id = ?1", nativeQuery = true)
    Long getLastOrderByBuyer(Long id);
}
