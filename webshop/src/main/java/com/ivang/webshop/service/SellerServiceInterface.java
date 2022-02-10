package com.ivang.webshop.service;

import java.util.List;

import com.ivang.webshop.dto.SellerDTO;
import com.ivang.webshop.entity.Seller;

public interface SellerServiceInterface {

    public Seller findOne(Long id);
    public Seller findByUsername(String username);
    public List<SellerDTO> findAll();
    public List<SellerDTO> findActive();
    public void save(SellerDTO seller);
    public void update(SellerDTO seller);
    public void handleBlock(Long id);
    public void remove(Long id);
}
