package com.ivang.webshop.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales")
@NoArgsConstructor
@AllArgsConstructor
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer percentage;
    private Date fromDate;
    private Date toDate;
    private String text;

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false)
    private Seller seller;

    @ManyToMany
    @JoinTable(name = "product_sale", joinColumns = @JoinColumn(name = "sale_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<Product>();

    // public Sale() {}

    // public Sale(Long id, Integer percentage, Date fromDate, Date toDate, String text, Seller seller, List<Product> products) {
    //     this.id = id;
    //     this.percentage = percentage;
    //     this.fromDate = fromDate;
    //     this.toDate = toDate;
    //     this.text = text;
    //     this.seller = seller;
    //     this.products = products;
    // }

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

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
