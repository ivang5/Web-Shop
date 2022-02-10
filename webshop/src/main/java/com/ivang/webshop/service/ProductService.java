package com.ivang.webshop.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.ivang.webshop.dto.ProductDTO;
import com.ivang.webshop.entity.Product;
import com.ivang.webshop.repository.ProductRepository;
import com.ivang.webshop.repository.SellerRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ProductService implements ProductServiceInterface {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    @Override
    public Product findOne(Long id) {
        log.info("Fetching product {}", id);
        return productRepository.getById(id);
    }

    @Override
    public List<ProductDTO> findAll() {
        log.info("Fetching all products");
        List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();
        
        for(Product p : productRepository.findAll()) {
            productsDTO.add(new ProductDTO(p));
        }
        
        return productsDTO;
    }

    @Override
    public List<ProductDTO> findByActiveSellers() {
        log.info("Fetching all products");
        List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();
        
        for(Product p : productRepository.findByActiveSellers()) {
            productsDTO.add(new ProductDTO(p));
        }
        
        return productsDTO;
    }

    @Override
    public List<ProductDTO> findBySeller(String username) {
        log.info("Fetching all products from seller {}", username);
        List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();

        for(Product p : productRepository.findBySeller(username)) {
            productsDTO.add(new ProductDTO(p));
        }

        return productsDTO;
    }
    
    @Override
    public List<ProductDTO> findBySale(Long id) {
        log.info("Fetching all products with sale {}", id);
        List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();

        for(Product p : productRepository.findBySale(id)) {
            productsDTO.add(new ProductDTO(p));
        }

        return productsDTO;
    }
    
    @Override
    public List<String> getNamesByOrder(Long id) {
        log.info("Fetching product names for order {}", id);
        return productRepository.getNamesByOrder(id);
    }

    @Override
    public void save(ProductDTO productDTO) {
        log.info("Saving new product {} to the database", productDTO.getId());
        Product product = new Product();
        populateProduct(product, productDTO, false);
    }

    @Override
    public void update(ProductDTO productDTO) {
        log.info("Updating product {}", productDTO.getId());
        Product product = productRepository.getById(productDTO.getId());

        if (product != null) {
            populateProduct(product, productDTO, true);
        }
    }

    @Override
    public void remove(Long id) {
        log.info("Removing product {}", id);
        productRepository.deleteById(id);
    }

    private void populateProduct(Product product, ProductDTO productDTO, boolean updating) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setProductType(productDTO.getProductType());
        product.setPicturePath(productDTO.getPicturePath());
        if (!updating) {
            product.setSeller(sellerRepository.getById(productDTO.getSeller().getId()));
        }
        
        productRepository.save(product);
    }
}
