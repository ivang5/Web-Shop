package com.ivang.webshop.lucene.model.shop.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
    
    private String name;
    private String description;
    private double price;
}
