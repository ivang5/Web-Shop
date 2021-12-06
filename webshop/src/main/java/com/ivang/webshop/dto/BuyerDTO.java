package com.ivang.webshop.dto;

import java.util.ArrayList;
import java.util.List;

import com.ivang.webshop.entity.Buyer;
import com.ivang.webshop.entity.Order;

public class BuyerDTO extends UserDTO {

    private String address;
    private List<OrderDTO> orders = new ArrayList<OrderDTO>();

    public BuyerDTO() {
        super();
    }

    public BuyerDTO(String address, List<OrderDTO> orders) {
        super();
        this.address = address;
        this.orders = orders;
    }

    public BuyerDTO(Buyer buyer) {
        super();
        this.address = buyer.getAddress();
        ArrayList<OrderDTO> orders = new ArrayList<OrderDTO>();
        for (Order order : buyer.getOrders()) {
            orders.add(new OrderDTO(order));
        }
        this.orders = orders;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
}
