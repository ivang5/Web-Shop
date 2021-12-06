package com.ivang.webshop.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ivang.webshop.entity.Item;
import com.ivang.webshop.entity.Product;
import com.ivang.webshop.entity.ProductType;
import com.ivang.webshop.entity.Sale;

public class ProductDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private double price;
    private ProductType productType;
    private String picturePath;
    private List<ItemDTO> items = new ArrayList<ItemDTO>();
    private List<SaleDTO> sales = new ArrayList<SaleDTO>();
    private SellerDTO seller;

    public ProductDTO() {}

    public ProductDTO(Long id, String name, String description, double price, ProductType productType, String picturePath, List<ItemDTO> items,
            List<SaleDTO> sales, SellerDTO seller) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productType = productType;
        this.picturePath = picturePath;
        this.items = items;
        this.sales = sales;
        this.seller = seller;
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.productType = product.getProductType();
        this.picturePath = product.getPicturePath();
        List<ItemDTO> items = new ArrayList<ItemDTO>();
        for (Item item : product.getItems()) {
            items.add(new ItemDTO(item));
        }
        List<SaleDTO> sales = new ArrayList<SaleDTO>();
        for (Sale sale : product.getSales()) {
            sales.add(new SaleDTO(sale));
        }
        this.seller = new SellerDTO(product.getSeller());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public List<SaleDTO> getSales() {
        return sales;
    }

    public void setSales(List<SaleDTO> sales) {
        this.sales = sales;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }
}
