package com.phat.food_delivering.service;

import com.phat.food_delivering.model.Cart;
import com.phat.food_delivering.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
}
