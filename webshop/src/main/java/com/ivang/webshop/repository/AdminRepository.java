package com.ivang.webshop.repository;

import com.ivang.webshop.entity.Admin;

import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends UserRepository <Admin, Long> {
    Admin findByUsername(String username);
}
