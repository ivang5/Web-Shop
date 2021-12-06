package com.ivang.webshop.service;

import java.util.List;

import com.ivang.webshop.dto.OrderDTO;
import com.ivang.webshop.entity.Order;

public interface OrderServiceInterface {
    
    public Order findOne(Long id);
    public List<OrderDTO> findAll();
    public void save(OrderDTO order);
    public void update(OrderDTO order);
    public void remove(Long id);
}
