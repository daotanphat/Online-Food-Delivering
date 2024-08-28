package com.phat.food_delivering.controller.Admin;

import com.phat.food_delivering.dto.OrderDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        OrderDTO orderDTO = orderService.updateOrderStatus(id, status);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/{status}")
    public ResponseEntity<List<OrderDTO>> getRestaurantOrder(
            @PathVariable Long id,
            @PathVariable String status) {
        List<OrderDTO> orderDTOS = orderService.getRestaurantOrder(id, status);
        return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
    }
}
