package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.AddressDTO;
import com.phat.food_delivering.dto.CartDTO;
import com.phat.food_delivering.dto.Mapper.AddressDTOMapper;
import com.phat.food_delivering.dto.Mapper.OrderDTOMapper;
import com.phat.food_delivering.dto.OrderDTO;
import com.phat.food_delivering.exception.EntityNotFoundException;
import com.phat.food_delivering.model.*;
import com.phat.food_delivering.repository.*;
import com.phat.food_delivering.request.AddressRequest;
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
    private final CartRepository cartRepository;
    OrderRepository orderRepository;
    UserService userService;
    RestaurantService restaurantService;
    RestaurantRepository restaurantRepository;
    AddressService addressService;
    CartService cartService;
    OrderDTOMapper orderDTOMapper;
    AddressDTOMapper addressDTOMapper;
    CartItemRepository cartItemRepository;


    @Override
    public OrderDTO createOrder(OrderRequest request, String token) {
        token = token.replace(SecurityConstants.BEARER, "");
        User user = userService.findUserBasedOnToken(token);
        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());

        CartDTO cartDTO = cartService.findCartByUserId(token);
        Cart cart = cartService.findCartById(cartDTO.id());

        Order order = new Order();
        order.setCustomer(user);
        order.setRestaurant(restaurant);
        order.setOrderStatus("PENDING");
        order.setCreateAt(new Date());
        order.setDeliveryAddress(user.getAddress());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(item.getFood());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getTotalPrice());
            orderItem.setIngredients(item.getIngredients());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
            cartItemRepository.delete(item);
        }
        order.setItems(orderItems);
        order.setTotalItem(cart.getItems().size());
        order.setTotalPrice(cart.getTotal());

        Order savedOrder = orderRepository.save(order);
        restaurant.getOrders().add(savedOrder);
        restaurantRepository.save(restaurant);

        return orderDTOMapper.apply(orderRepository.save(order));
    }

    @Override
    public OrderDTO updateOrderStatus(Long id, String status) {
        Order order = getOrderById(id);
        if (status.equals("OUT_FOR_DELIVERY")
                || status.equals("DELIVERED")
                || status.equals("PENDING")
                || status.equals("COMPLETED")
        ) {
            order.setOrderStatus(status);
            order = orderRepository.save(order);
        }
        return orderDTOMapper.apply(order);
    }

    @Override
    public void cancelOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDTO> getUserOrder(String token) {
        token = token.replace(SecurityConstants.BEARER, "");
        User user = userService.findUserBasedOnToken(token);
        List<Order> orders = orderRepository.findOrderByCustomerIdOrderByCreateAtDesc(user.getId());
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = orderDTOMapper.apply(order);
            orderDTOS.add(orderDTO);
        }

        MessageResponse messageResponse = new MessageResponse();
        if (orders.isEmpty()) {
            messageResponse.setMessage("No order can be found.");
        }
        return orderDTOS;
    }

    @Override
    public List<OrderDTO> getRestaurantOrder(Long restaurantId, String status) {
        List<Order> orders = orderRepository.findOrderByRestaurantId(restaurantId);
        MessageResponse messageResponse = new MessageResponse();
        if (orders.isEmpty()) {
            messageResponse.setMessage("No order can be found.");
        } else {
            if ((!status.isEmpty() || !status.trim().equals("")) && !status.equals("ALL")) {
                orders = orders.stream().filter(o -> o.getOrderStatus().equals(status)).collect(Collectors.toList());
            }
        }
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = orderDTOMapper.apply(order);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
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
