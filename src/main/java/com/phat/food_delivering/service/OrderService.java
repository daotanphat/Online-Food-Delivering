package com.phat.food_delivering.service;

import com.phat.food_delivering.model.Order;
import com.phat.food_delivering.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest request, String token);

    public Order updateOrderStatus(Long id, String status);

    public void cancelOrder(Long id);

    public List<Order> getUserOrder(String token);

    public List<Order> getRestaurantOrder(Long restaurantId, String status);

    public Order getOrderById(Long id);
}
