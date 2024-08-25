package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.OrderDTO;
import com.phat.food_delivering.model.Order;
import com.phat.food_delivering.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public OrderDTO createOrder(OrderRequest request, String token);

    public OrderDTO updateOrderStatus(Long id, String status);

    public void cancelOrder(Long id);

    public List<OrderDTO> getUserOrder(String token);

    public List<OrderDTO> getRestaurantOrder(Long restaurantId, String status);

    public Order getOrderById(Long id);
}
