package com.phat.food_delivering.controller;

import com.phat.food_delivering.dto.OrderDTO;
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
    public ResponseEntity<OrderDTO> createOrder(
            @RequestBody OrderRequest request,
            @RequestHeader("Authorization") String token
    ) {
        OrderDTO orderDTO = orderService.createOrder(request, token);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDTO>> getUserOrder(@RequestHeader("Authorization") String token) {
        List<OrderDTO> orderDTOs = orderService.getUserOrder(token);
        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }
}
