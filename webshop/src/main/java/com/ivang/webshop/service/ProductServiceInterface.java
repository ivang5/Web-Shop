package com.ivang.webshop.service;

import java.util.List;

import com.ivang.webshop.dto.ProductDTO;
import com.ivang.webshop.entity.Product;

public interface ProductServiceInterface {
    
    public Product findOne(Long id);
    public List<ProductDTO> findAll();
    public List<ProductDTO> findByActiveSellers();
    public List<ProductDTO> findBySeller(String username);
    public List<ProductDTO> findBySale(Long id);
    public List<String> getNamesByOrder(Long id);
    public void save(ProductDTO product);
    public void update(ProductDTO product);
    public void remove(Long id);
}
