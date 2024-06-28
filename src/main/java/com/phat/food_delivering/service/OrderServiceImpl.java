package com.phat.food_delivering.service;

import com.phat.food_delivering.exception.EntityNotFoundException;
import com.phat.food_delivering.model.*;
import com.phat.food_delivering.repository.OrderItemRepository;
import com.phat.food_delivering.repository.OrderRepository;
import com.phat.food_delivering.repository.RestaurantRepository;
import com.phat.food_delivering.repository.UserRepository;
import com.phat.food_delivering.request.OrderRequest;
import com.phat.food_delivering.response.MessageResponse;
import com.phat.food_delivering.security.SecurityConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    OrderRepository orderRepository;
    UserService userService;
    RestaurantService restaurantService;
    RestaurantRepository restaurantRepository;
    AddressService addressService;
    CartService cartService;


    @Override
    public Order createOrder(OrderRequest request, String token) {
        token = token.replace(SecurityConstants.BEARER, "");
        User user = userService.findUserBasedOnToken(token);
        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
        Address shipAddress = request.getShipAddress();
        List<Address> addresses = addressService.getAddresses();
        if (!addressService.checkDuplicateAddress(shipAddress, addresses)) {
            addressService.save(shipAddress);
        } else {
            shipAddress = addressService.findAddress(shipAddress);
        }
        if (!addressService.checkDuplicateAddress(shipAddress, user.getAddresses())) {
            user.getAddresses().add(shipAddress);
            userRepository.save(user);
        }
        Cart cart = cartService.findCartByUserId(token);

        Order order = new Order();
        order.setCustomer(user);
        order.setRestaurant(restaurant);
        order.setOrderStatus("PENDING");
        order.setCreateAt(new Date());
        order.setDeliveryAddress(shipAddress);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(item.getFood());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getTotalPrice());
            orderItem.setIngredients(item.getIngredients());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }
        order.setItems(orderItems);
        order.setTotalItem(cart.getItems().size());
        order.setTotalPrice(cart.getTotal());

        Order savedOrder = orderRepository.save(order);
        restaurant.getOrders().add(savedOrder);
        restaurantRepository.save(restaurant);

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderStatus(Long id, String status) {
        Order order = getOrderById(id);
        if (status.equals("OUT_FOR_DELIVERY")
                || status.equals("DELIVERED")
                || status.equals("PENDING")
                || status.equals("COMPLETED")
        ) {
            order.setOrderStatus(status);
            order = orderRepository.save(order);
        }
        return order;
    }

    @Override
    public void cancelOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getUserOrder(String token) {
        token = token.replace(SecurityConstants.BEARER, "");
        User user = userService.findUserBasedOnToken(token);
        List<Order> orders = orderRepository.findOrderByCustomerId(user.getId());

        MessageResponse messageResponse = new MessageResponse();
        if (orders.isEmpty()) {
            messageResponse.setMessage("No order can be found.");
        }
        return orders;
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String status) {
        List<Order> orders = orderRepository.findOrderByRestaurantId(restaurantId);
        MessageResponse messageResponse = new MessageResponse();
        if (orders.isEmpty()) {
            messageResponse.setMessage("No order can be found.");
        } else {
            if (!status.isEmpty() || status.trim().equals("")) {
                orders = orders.stream().filter(o -> o.getOrderStatus().equals(status)).collect(Collectors.toList());
            }
        }
        return orders;
    }

    @Override
    public Order getOrderById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return unwarpOrder(optionalOrder, id);
    }

    static Order unwarpOrder(Optional<Order> entity, Long id) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(Order.class, id);
        }
    }
}
