package com.ivang.webshop.service;

import java.util.List;

import com.ivang.webshop.dto.AdminDTO;
import com.ivang.webshop.entity.Admin;

public interface AdminServiceInterface {
    
    public Admin findOne(Long id);
    public Admin findByUsername(String username);
    public List<AdminDTO> findAll();
    public void update(AdminDTO admin);
    public void handleBlock(Long id);
    public void remove(Long id);
}
