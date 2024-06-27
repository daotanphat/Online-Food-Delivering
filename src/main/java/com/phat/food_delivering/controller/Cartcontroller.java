package com.phat.food_delivering.controller;

import com.phat.food_delivering.model.Cart;
import com.phat.food_delivering.model.CartItem;
import com.phat.food_delivering.repository.CartItemRepository;
import com.phat.food_delivering.request.CartItemRequest;
import com.phat.food_delivering.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class Cartcontroller {
    @Autowired
    CartService cartService;
    @Autowired
    private CartItemRepository cartItemRepository;

    @PutMapping
    public ResponseEntity<CartItem> addCartItemToCart(
            @RequestBody CartItemRequest request,
            @RequestHeader("Authorization") String token
    ) {
        CartItem cartItem = cartService.addCartItemIntoCart(request, token);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @PathVariable Long id,
            @RequestParam int quantity
    ) {
        CartItem cartItem = cartService.updateCartItemQuantity(id, quantity);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Cart> removeItemFromCart(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        Cart cart = cartService.removeItemFromCart(id, token);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCartItemFromCart(@RequestHeader("Authorization") String token) {
        Cart cart = cartService.clearCartItem(token);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cart> findCartById(@PathVariable Long id) {
        Cart cart = cartService.findCartById(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Cart> findCartByUserId(@RequestHeader("Authorization") String token) {
        Cart cart = cartService.findCartByUserId(token);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
