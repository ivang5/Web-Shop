package com.ivang.webshop.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.ivang.webshop.dto.OrderDTO;
import com.ivang.webshop.entity.Order;
import com.ivang.webshop.repository.BuyerRepository;
import com.ivang.webshop.repository.OrderRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class OrderService implements OrderServiceInterface {
    
    private final OrderRepository orderRepository;
    private final BuyerRepository buyerRepository;

    @Override
    public Order findOne(Long id) {
        log.info("Fetching order {}", id);
        return orderRepository.getById(id);
    }
    
    @Override
    public List<OrderDTO> findAll() {
        log.info("Fetching all orders");
        List<OrderDTO> ordersDTO = new ArrayList<OrderDTO>();

        for(Order o : orderRepository.findAll()) {
            ordersDTO.add(new OrderDTO(o));
        }

        return ordersDTO;
    }

    @Override
    public void save(OrderDTO orderDTO) {
        log.info("Saving new order {} to the database", orderDTO.getId());
        Order order = new Order();
        populateOrder(order, orderDTO);
    }

    @Override
    public void update(OrderDTO orderDTO) {
        log.info("Updating order {}", orderDTO.getId());
        Order order = orderRepository.getById(orderDTO.getId());

        if (order != null) {
            populateOrder(order, orderDTO);
        }
    }

    @Override
    public void remove(Long id) {
        log.info("Removing order {}", id);
        orderRepository.deleteById(id);
    }

    private void populateOrder(Order order, OrderDTO orderDTO) {
        order.setTime(orderDTO.getTime());
        order.setDelivered(orderDTO.isDelivered());
        order.setRate(orderDTO.getRate());
        order.setComment(orderDTO.getComment());
        order.setAnonymousComment(orderDTO.isAnonymousComment());
        order.setArchivedComment(orderDTO.isArchivedComment());
        order.setBuyer(buyerRepository.getById(orderDTO.getBuyer().getId()));
        
        orderRepository.save(order);
    }
}
