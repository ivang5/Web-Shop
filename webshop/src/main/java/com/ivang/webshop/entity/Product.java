package com.ivang.webshop.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.*;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    private String name;

    @Column(columnDefinition="TEXT")
    private String description;
    private double price;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(columnDefinition="TEXT")
    private String picturePath;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detailed_desc_id", referencedColumnName = "id")
    private DetailedDescription detailedDescription;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = LAZY, mappedBy = "product")
    private List<Item> items = new ArrayList<Item>();

    @ManyToMany
    @JoinTable(name = "product_sale", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "sale_id"))
    private List<Sale> sales = new ArrayList<Sale>();

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false)
    private Seller seller;

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

    public DetailedDescription getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(DetailedDescription detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
