package com.ivang.webshop.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ivang.webshop.entity.Product;
import com.ivang.webshop.entity.Sale;

public class SaleDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer percentage;
    private Date fromDate;
    private Date toDate;
    private String text;
    private SellerDTO seller;
    private List<ProductDTO> products = new ArrayList<ProductDTO>();
    
    public SaleDTO() {}

    public SaleDTO(Long id, Integer percentage, Date fromDate, Date toDate, String text, SellerDTO seller,
            List<ProductDTO> products) {
        this.id = id;
        this.percentage = percentage;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.text = text;
        this.seller = seller;
        this.products = products;
    }

    public SaleDTO(Sale sale) {
        this.id = sale.getId();
        this.percentage = sale.getPercentage();
        this.fromDate = sale.getFromDate();
        this.toDate = sale.getToDate();
        this.text = sale.getText();
        this.seller = new SellerDTO(sale.getSeller());
        List<ProductDTO> products = new ArrayList<ProductDTO>();
        for (Product prodcut : sale.getProducts()) {
            products.add(new ProductDTO(prodcut));
        }
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
