package com.phat.food_delivering.dto.Mapper;

import com.phat.food_delivering.dto.CartItemDTO;
import com.phat.food_delivering.model.CartItem;
import com.phat.food_delivering.model.Food;
import com.phat.food_delivering.request.CartItemRequest;
import com.phat.food_delivering.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class CartItemDTOMapper implements Function<CartItem, CartItemDTO> {
    FoodDTOMapper foodDTOMapper;
    FoodService foodService;

    @Override
    public CartItemDTO apply(CartItem cartItem) {
        return new CartItemDTO(
                cartItem.getId(),
                foodDTOMapper.apply(cartItem.getFood()),
                cartItem.getQuantity(),
                cartItem.getIngredients(),
                cartItem.getTotalPrice()
        );
    }

    public CartItem toCartItem(CartItemRequest cartItemRequest){
        Food food = foodService.getFoodById(cartItemRequest.getFoodId());
        CartItem cartItem = new CartItem();
        cartItem.setFood(food);
        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setIngredients(cartItemRequest.getIngredients());

        return cartItem;
    }
}
