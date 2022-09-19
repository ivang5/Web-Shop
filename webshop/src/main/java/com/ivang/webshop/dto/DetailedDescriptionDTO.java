package com.ivang.webshop.dto;

import java.io.Serializable;

import com.ivang.webshop.entity.DetailedDescription;

public class DetailedDescriptionDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String name;
    private String type;
    private byte[] data;

    public DetailedDescriptionDTO() {}

    public DetailedDescriptionDTO(Long id, String name, String type, byte[] data) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public DetailedDescriptionDTO(DetailedDescription detailedDescription) {
        this.id = detailedDescription.getId();
        this.name = detailedDescription.getName();
        this.type = detailedDescription.getType();
        this.data = detailedDescription.getData();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
