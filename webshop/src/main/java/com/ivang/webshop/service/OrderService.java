package com.ivang.webshop.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.ivang.webshop.dto.OrderDTO;
import com.ivang.webshop.entity.Order;
import com.ivang.webshop.lucene.indexing.OrderIndexer;
import com.ivang.webshop.lucene.model.shop.dto.OrderRequestDTO;
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
    private final OrderIndexer orderIndexer;

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
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        populateOrder(order, orderDTO, orderRequestDTO, false);
    }

    @Override
    public void update(OrderDTO orderDTO) {
        log.info("Updating order {}", orderDTO.getId());
        Order order = orderRepository.getById(orderDTO.getId());
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();

        if (order != null) {
            populateOrder(order, orderDTO, orderRequestDTO, true);
        }
    }

    @Override
    public void remove(Long id) {
        log.info("Removing order {}", id);
        orderRepository.deleteById(id);
        orderIndexer.deleteOrder(id);
    }

    @Override
    public List<OrderDTO> getByBuyer(Long id) {
        log.info("Fetching all orders for buyer {}", id);

        List<OrderDTO> ordersDTO = new ArrayList<OrderDTO>();

        for(Order o : orderRepository.getByBuyer(id)) {
            ordersDTO.add(new OrderDTO(o));
        }

        return ordersDTO;
    }

    @Override
    public int getRateBySeller(Long id) {
        return orderRepository.getRateBySeller(id);
    }

    @Override
    public int getRatesNumBySeller(Long id) {
        return orderRepository.getRatesNumBySeller(id);
    }

    @Override
    public List<OrderDTO> getNonArchivedBySeller(Long id) {
        log.info("Fetching all orders for seller {}", id);
        List<OrderDTO> ordersDTO = new ArrayList<OrderDTO>();

        for(Order o : orderRepository.findNonArcivedBySeller(id)) {
            ordersDTO.add(new OrderDTO(o));
        }

        return ordersDTO;
    }

    @Override
    public Order getLastOrderByBuyer(Long id) {
        Long orderId =orderRepository.getLastOrderByBuyer(id);
        return orderRepository.getById(orderId);
    }

    private void populateOrder(Order order, OrderDTO orderDTO, OrderRequestDTO orderRequestDTO, boolean updating) {
        order.setTime(orderDTO.getTime());
        orderRequestDTO.setTime(orderDTO.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        order.setDelivered(orderDTO.isDelivered());
        orderRequestDTO.setDelivered(orderDTO.isDelivered());
        order.setRate(orderDTO.getRate());
        orderRequestDTO.setRate(orderDTO.getRate());
        order.setComment(orderDTO.getComment());
        orderRequestDTO.setComment(orderDTO.getComment());
        order.setAnonymousComment(orderDTO.isAnonymousComment());
        orderRequestDTO.setAnonymousComment(orderDTO.isAnonymousComment());
        order.setArchivedComment(orderDTO.isArchivedComment());
        orderRequestDTO.setArchivedComment(orderDTO.isArchivedComment());
        order.setBuyer(buyerRepository.getById(orderDTO.getBuyer().getId()));
        orderRequestDTO.setPrice(orderDTO.getPrice());
        
        Order newOrder = orderRepository.save(order);

        try {
            orderRequestDTO.setAssociatedId(newOrder.getId());

            if (updating) {
                orderIndexer.deleteOrder(orderRequestDTO.getAssociatedId());
            }

            orderIndexer.indexOrder(orderRequestDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
