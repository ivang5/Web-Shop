package com.ivang.webshop.service;

import java.util.List;

import com.ivang.webshop.dto.BuyerDTO;
import com.ivang.webshop.entity.Buyer;

public interface BuyerServiceInterface {
    
    public Buyer findOne(Long id);
    public Buyer findByUsername(String username);
    public List<BuyerDTO> findAll();
    public void save(BuyerDTO buyer);
    public void update(BuyerDTO buyer);
    public void handleBlock(Long id);
    public void remove(Long id);
}
