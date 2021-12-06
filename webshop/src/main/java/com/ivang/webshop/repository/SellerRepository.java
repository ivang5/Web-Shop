package com.ivang.webshop.repository;

import com.ivang.webshop.entity.Seller;

import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends UserRepository <Seller, Long> {
    Seller findByUsername(String username);
}
