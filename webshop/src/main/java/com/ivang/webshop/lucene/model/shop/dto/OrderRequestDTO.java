package com.ivang.webshop.lucene.model.shop.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.elasticsearch.core.SearchHits;

import com.ivang.webshop.lucene.model.shop.OrderEs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderRequestDTO {
    
    private Long associatedId;
    private LocalDate time;
    private boolean delivered = false;
    private Integer rate;
    private String comment;
    private boolean anonymousComment = false;
    private boolean archivedComment = false;
    private double price;

    public OrderRequestDTO(OrderEs orderEs) {
        associatedId = orderEs.getAssociatedId();
        time = orderEs.getTime();
        delivered = orderEs.isDelivered();
        rate = orderEs.getRate();
        comment = orderEs.getComment();
        anonymousComment = orderEs.isAnonymousComment();
        archivedComment = orderEs.isArchivedComment();
        price = orderEs.getPrice();
    }

    public static List<OrderRequestDTO> mapDtos(SearchHits<OrderEs> searchHits) {
        return searchHits
            .map(order -> new OrderRequestDTO(order.getContent()))
            .toList();
    }
}
