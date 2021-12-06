package com.ivang.webshop.entity;

import javax.persistence.*;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "Admins")
@NoArgsConstructor
public class Admin extends User {

    // public Admin() {}

    public Admin(Long id, String firstName, String lastName, String username, String password, boolean blocked) {
        super(id, firstName, lastName, username, password, blocked);
    }
}
