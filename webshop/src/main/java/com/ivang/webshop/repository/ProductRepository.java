package com.ivang.webshop.repository;

import java.util.List;

import com.ivang.webshop.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p.* FROM products p JOIN sellers s ON p.seller_id = s.id WHERE s.blocked = 0", nativeQuery = true)
    List<Product> findByActiveSellers();

    @Query("SELECT p FROM Product p JOIN p.sales s WHERE s.id = ?1")
    List<Product> findBySale(Long id);

    @Query("SELECT p FROM Product p JOIN p.seller s WHERE s.username = ?1")
    List<Product> findBySeller(String username);

    @Query(value = "SELECT p.name FROM products p JOIN items i ON p.id = i.product_id JOIN orders o ON i.order_id = o.id WHERE o.id = ?1", nativeQuery = true)
    List<String> getNamesByOrder(Long id);

    @Query(value = "SELECT DISTINCT p.* FROM products p JOIN items i ON p.id = i.product_id JOIN orders o ON i.order_id = o.id WHERE ((SELECT AVG(ord.rate) FROM orders ord JOIN items itm ON ord.id = itm.order_id JOIN products prd ON itm.product_id = prd.id WHERE prd.id = p.id) BETWEEN ?1 AND ?2)", nativeQuery = true)
    List<Product> findByAverageRate(int from, int to);

    @Query(value= "SELECT DISTINCT p.* FROM products p JOIN items i ON p.id = i.product_id JOIN orders o ON i.order_id = o.id WHERE ((SELECT COUNT(ord.comment) FROM orders ord JOIN items itm ON ord.id = itm.order_id JOIN products prd ON itm.product_id = prd.id WHERE prd.id = p.id AND ord.comment IS NOT NULL) BETWEEN ?1 AND ?2)", nativeQuery = true)
    List<Product> findByNumberOfComments(int from, int to);
}
