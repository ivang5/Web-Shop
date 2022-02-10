package com.ivang.webshop.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.NoArgsConstructor;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "sellers")
@NoArgsConstructor
public class Seller extends User {

    private Date operatesFrom;
    private String email;
    private String address;
    private String name;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = LAZY, mappedBy = "seller")
    private List<Product> products = new ArrayList<Product>();

    @OneToMany(cascade = { CascadeType.ALL }, fetch = LAZY, mappedBy = "seller")
    private List<Sale> sales = new ArrayList<Sale>();

    public Seller(Long id, String firstName, String lastName, String username, String password, boolean blocked, Date operatesFrom,
            String email, String address, String name, List<Product> products, List<Sale> sales) {
        super(id, firstName, lastName, username, password, blocked);
        this.operatesFrom = operatesFrom;
        this.email = email;
        this.address = address;
        this.name = name;
        this.products = products;
        this.sales = sales;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}
