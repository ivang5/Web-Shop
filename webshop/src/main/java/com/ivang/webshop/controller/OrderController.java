package com.ivang.webshop.controller;

import java.util.List;

import com.ivang.webshop.dto.OrderDTO;
import com.ivang.webshop.entity.Order;
import com.ivang.webshop.service.OrderServiceInterface;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping(value = "shop/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderServiceInterface orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return new ResponseEntity<List<OrderDTO>>(orderService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") Long id) {
		Order order = orderService.findOne(id);
		
		if(order == null) {
			return new ResponseEntity<OrderDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<OrderDTO>(new OrderDTO(order), HttpStatus.OK);
	}

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO order) {
        try {
            orderService.save(order);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody OrderDTO order) {
        try {
            orderService.update(order);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Order order = orderService.findOne(id);

        if (order != null) {
            orderService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
