package com.ivang.webshop.service;

import java.util.List;

import com.ivang.webshop.dto.SaleDTO;
import com.ivang.webshop.entity.Sale;

public interface SaleServiceInterface {
    
    public Sale findOne(Long id);
    public Sale findOneByProduct(Long id);
    public List<SaleDTO> findAll();
    public List<SaleDTO> findBySeller(Long id);
    public List<SaleDTO> findByProduct(Long id);
    public void save(SaleDTO sale);
    public void update(SaleDTO sale);
    public void remove(Long id);
}
