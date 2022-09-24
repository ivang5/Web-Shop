package com.ivang.webshop.repository;

import com.ivang.webshop.entity.Item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT * FROM items WHERE items.order_id = ?1", nativeQuery = true)
    List<Item> getByOrder(Long id);
}
