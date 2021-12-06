package com.ivang.webshop.service;

import java.util.List;

import com.ivang.webshop.dto.ProductDTO;
import com.ivang.webshop.entity.Product;

public interface ProductServiceInterface {
    
    public Product findOne(Long id);
    public List<ProductDTO> findAll();
    public void save(ProductDTO product);
    public void update(ProductDTO product);
    public void remove(Long id);
}