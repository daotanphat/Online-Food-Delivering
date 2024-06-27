package com.phat.food_delivering.service;

import com.phat.food_delivering.model.Cart;
import com.phat.food_delivering.model.CartItem;
import com.phat.food_delivering.request.CartItemRequest;

public interface CartService {
    void saveCart(Cart cart);

    public CartItem addCartItemIntoCart(CartItemRequest request, String token);

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity);

    public Cart removeItemFromCart(Long cartItemId, String token);

    public Long calculateTotal(Cart cart);

    public Cart findCartById(Long cartId);

    public Cart findCartByUserId(String token);

    public CartItem findCartItemById(Long id);

    public Cart clearCartItem(String token);
}
