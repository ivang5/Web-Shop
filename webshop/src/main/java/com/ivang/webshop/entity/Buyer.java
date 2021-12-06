package com.ivang.webshop.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.NoArgsConstructor;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "buyers")
@NoArgsConstructor
public class Buyer extends User {
    
    private String address;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = EAGER, mappedBy = "buyer")
    private List<Order> orders = new ArrayList<Order>();

    // public Buyer() {}

    public Buyer(Long id, String firstName, String lastName, String username, String password, boolean blocked, String address,
            List<Order> orders) {
        super(id, firstName, lastName, username, password, blocked);
        this.address = address;
        this.orders = orders;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
