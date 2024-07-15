package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.CartDTO;
import com.phat.food_delivering.dto.CartItemDTO;
import com.phat.food_delivering.model.Cart;
import com.phat.food_delivering.model.CartItem;
import com.phat.food_delivering.request.CartItemRequest;

public interface CartService {
    void saveCart(Cart cart);

    public CartItemDTO addCartItemIntoCart(CartItemRequest request, String token);

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity);

    public CartDTO removeItemFromCart(Long cartItemId, String token);

    public Long calculateTotal(Cart cart);

    public Cart findCartById(Long cartId);

    public CartDTO findCartByUserId(String token);

    public CartItem findCartItemById(Long id);

    public CartDTO clearCartItem(String token);
}
