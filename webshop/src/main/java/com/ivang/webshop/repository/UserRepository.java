package com.ivang.webshop.repository;

import java.io.Serializable;

import com.ivang.webshop.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository<T extends User, ID extends Serializable> extends JpaRepository <T, ID> {
    
}
