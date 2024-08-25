package com.phat.food_delivering.dto.Mapper;

import com.phat.food_delivering.dto.OrderItemDTO;
import com.phat.food_delivering.model.OrderItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class OrderItemDTOMapper implements Function<OrderItem, OrderItemDTO> {
    FoodDTOMapper foodDTOMapper;

    @Override
    public OrderItemDTO apply(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getId(),
                foodDTOMapper.apply(orderItem.getFood()),
                orderItem.getQuantity(),
                orderItem.getTotalPrice(),
                orderItem.getIngredients()
        );
    }
}
