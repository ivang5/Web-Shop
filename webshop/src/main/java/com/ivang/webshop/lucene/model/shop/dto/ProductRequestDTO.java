package com.ivang.webshop.lucene.model.shop.dto;

import java.util.List;

import org.springframework.data.elasticsearch.core.SearchHits;

import com.ivang.webshop.lucene.model.shop.ProductEs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequestDTO {
    
    private Long associatedId;
    private String name;
    private String description;
    private String detailedDescription;
    private double price;

    public ProductRequestDTO(ProductEs productEs) {
        associatedId = productEs.getAssociatedId();
        name = productEs.getName();
        description = productEs.getDescription();
        price = productEs.getPrice();
    }

    public static List<ProductRequestDTO> mapDtos(SearchHits<ProductEs> searchHits) {
        return searchHits
            .map(product -> new ProductRequestDTO(product.getContent()))
            .toList();
    }
}
