package com.ivang.webshop.dto;

import java.io.Serializable;

import com.ivang.webshop.entity.Item;

public class ItemDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer quantity;
    private ProductDTO product;
    private OrderDTO order;

    public ItemDTO() {}

    public ItemDTO(Long id, Integer quantity, ProductDTO product, OrderDTO order) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
        this.order = order;
    }

    public ItemDTO(Item item) {
        this.id = item.getId();
        this.quantity = item.getQuantity();
        this.product = new ProductDTO(item.getProduct());
        this.order = new OrderDTO(item.getOrder());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }
}
