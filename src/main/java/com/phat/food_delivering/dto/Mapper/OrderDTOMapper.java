package com.phat.food_delivering.dto.Mapper;

import com.phat.food_delivering.dto.OrderDTO;
import com.phat.food_delivering.dto.OrderItemDTO;
import com.phat.food_delivering.model.Order;
import com.phat.food_delivering.model.OrderItem;
import com.phat.food_delivering.repository.UserRepository;
import com.phat.food_delivering.service.RestaurantService;
import com.phat.food_delivering.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class OrderDTOMapper implements Function<Order, OrderDTO> {
    UserDTOMapper userDTOMapper;
    RestaurantDTOMapper restaurantDTOMapper;
    OrderItemDTOMapper orderItemDTOMapper;
    AddressDTOMapper addressDTOMapper;

    @Override
    public OrderDTO apply(Order order) {
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for (OrderItem orderItem : order.getItems()) {
            OrderItemDTO orderItemDTO = orderItemDTOMapper.apply(orderItem);
            orderItemDTOS.add(orderItemDTO);
        }

        return new OrderDTO(
                order.getId(),
                userDTOMapper.apply(order.getCustomer()),
                restaurantDTOMapper.apply(order.getRestaurant()),
                order.getTotalAmount(),
                order.getOrderStatus(),
                order.getCreateAt(),
                addressDTOMapper.apply(order.getDeliveryAddress()),
                orderItemDTOS,
                order.getTotalItem(),
                order.getTotalPrice()
        );
    }
}
