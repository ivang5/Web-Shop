package com.ivang.webshop.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ivang.webshop.entity.Order;

public class OrderDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date time;
    private boolean delivered = false;
    private Integer rate;
    private String comment;
    private boolean anonymousComment = false;
    private boolean archivedComment = false;
    private BuyerDTO buyer;
    private List<ItemDTO> items = new ArrayList<ItemDTO>();

    public OrderDTO() {}

    public OrderDTO(Long id, Date time, boolean delivered, Integer rate, String comment, boolean anonymousComment,
            boolean archivedComment, BuyerDTO buyer, List<ItemDTO> items) {
        this.id = id;
        this.time = time;
        this.delivered = delivered;
        this.rate = rate;
        this.comment = comment;
        this.anonymousComment = anonymousComment;
        this.archivedComment = archivedComment;
        this.buyer = buyer;
        this.items = items;
    }

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.time = order.getTime();
        this.delivered = order.getDelivered();
        this.rate = order.getRate();
        this.comment = order.getComment();
        this.anonymousComment = order.getAnonymousComment();
        this.archivedComment = order.getArchivedComment();
        this.buyer = new BuyerDTO(order.getBuyer());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isAnonymousComment() {
        return anonymousComment;
    }

    public void setAnonymousComment(boolean anonymousComment) {
        this.anonymousComment = anonymousComment;
    }

    public boolean isArchivedComment() {
        return archivedComment;
    }

    public void setArchivedComment(boolean archivedComment) {
        this.archivedComment = archivedComment;
    }

    public BuyerDTO getBuyer() {
        return buyer;
    }

    public void setBuyer(BuyerDTO buyer) {
        this.buyer = buyer;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
