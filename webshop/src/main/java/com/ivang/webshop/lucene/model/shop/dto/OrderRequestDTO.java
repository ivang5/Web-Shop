package com.ivang.webshop.lucene.model.shop.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrderRequestDTO {
    
    private LocalDate time;
    private boolean delivered = false;
    private Integer rate;
    private String comment;
    private boolean anonymousComment = false;
}
