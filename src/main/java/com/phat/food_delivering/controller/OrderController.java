package com.phat.food_delivering.controller;

import com.phat.food_delivering.model.Order;
import com.phat.food_delivering.request.OrderRequest;
import com.phat.food_delivering.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody OrderRequest request,
            @RequestHeader("Authorization") String token
    ) {
        Order order = orderService.createOrder(request, token);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> getUserOrder(@RequestHeader("Authorization") String token) {
        List<Order> orders = orderService.getUserOrder(token);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
