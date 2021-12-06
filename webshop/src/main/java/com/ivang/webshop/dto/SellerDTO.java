package com.ivang.webshop.dto;

import java.util.ArrayList;
import java.util.Date;

import com.ivang.webshop.entity.Product;
import com.ivang.webshop.entity.Sale;
import com.ivang.webshop.entity.Seller;

public class SellerDTO extends UserDTO {
    private Date operatesFrom;
    private String email;
    private String address;
    private String name;
    private ArrayList<SaleDTO> sales = new ArrayList<SaleDTO>();
    private ArrayList<ProductDTO> products = new ArrayList<ProductDTO>();

    public SellerDTO() {
        super();
    }

    public SellerDTO(Date operatesFrom, String email, String address, String name, ArrayList<SaleDTO> sales,
            ArrayList<ProductDTO> products) {
        super();
        this.operatesFrom = operatesFrom;
        this.email = email;
        this.address = address;
        this.name = name;
        this.sales = sales;
        this.products = products;
    }

    public SellerDTO(Seller seller) {
        super();
        this.operatesFrom = seller.getOperatesFrom();
        this.email = seller.getEmail();
        this.address = seller.getAddress();
        this.name = seller.getName();
        ArrayList<SaleDTO> sales = new ArrayList<SaleDTO>();
        for (Sale sale : seller.getSales()) {
            sales.add(new SaleDTO(sale));
        }
        ArrayList<ProductDTO> products = new ArrayList<ProductDTO>();
        for (Product product : seller.getProducts()) {
            products.add(new ProductDTO(product));
        }
    }

    public Date getOperatesFrom() {
        return operatesFrom;
    }

    public void setOperatesFrom(Date operatesFrom) {
        this.operatesFrom = operatesFrom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SaleDTO> getSales() {
        return sales;
    }

    public void setSales(ArrayList<SaleDTO> sales) {
        this.sales = sales;
    }

    public ArrayList<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductDTO> products) {
        this.products = products;
    }
}
