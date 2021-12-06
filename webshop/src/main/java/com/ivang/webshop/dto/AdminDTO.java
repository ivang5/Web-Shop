package com.ivang.webshop.dto;

import com.ivang.webshop.entity.Admin;

public class AdminDTO extends UserDTO {

    public AdminDTO() {
        super();
    }

    public AdminDTO(Admin admin) {
        super(admin);
    }
}
