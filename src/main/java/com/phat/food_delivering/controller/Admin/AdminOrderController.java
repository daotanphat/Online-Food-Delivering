package com.phat.food_delivering.controller.Admin;

import com.phat.food_delivering.model.Order;
import com.phat.food_delivering.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {
    @Autowired
    OrderService orderService;

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        Order order = orderService.updateOrderStatus(id, status);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/{status}")
    public ResponseEntity<List<Order>> getRestaurantOrder(
            @PathVariable Long id,
            @PathVariable String status) {
        List<Order> orders = orderService.getRestaurantOrder(id, status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
