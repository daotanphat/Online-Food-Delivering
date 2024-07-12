package com.phat.food_delivering.dto.Mapper;

import com.phat.food_delivering.dto.CartDTO;
import com.phat.food_delivering.dto.CartItemDTO;
import com.phat.food_delivering.model.Cart;
import com.phat.food_delivering.model.CartItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class CartDTOMapper implements Function<Cart, CartDTO> {
    CartItemDTOMapper cartItemDTOMapper;

    @Override
    public CartDTO apply(Cart cart) {
        List<CartItemDTO> cartItemDTOS = new ArrayList<>();
        for(CartItem cartItem : cart.getItems()){
            cartItemDTOS.add(cartItemDTOMapper.apply(cartItem));
        }
        return new CartDTO(
                cart.getId(),
                cartItemDTOS,
                cart.getTotal()
        );
    }
}
