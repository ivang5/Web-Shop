package com.ivang.webshop.repository;

import com.ivang.webshop.entity.Buyer;

import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends UserRepository <Buyer, Long> {
    Buyer findByUsername(String username);
}
