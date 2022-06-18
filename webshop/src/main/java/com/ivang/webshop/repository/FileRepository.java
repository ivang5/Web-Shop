package com.ivang.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivang.webshop.entity.DetailedDescription;

@Repository
public interface FileRepository extends JpaRepository<DetailedDescription, Long> {
    
}
