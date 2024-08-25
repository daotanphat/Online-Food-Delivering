package com.phat.food_delivering.controller;

import com.phat.food_delivering.dto.CartDTO;
import com.phat.food_delivering.dto.CartItemDTO;
import com.phat.food_delivering.dto.Mapper.CartDTOMapper;
import com.phat.food_delivering.dto.Mapper.CartItemDTOMapper;
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
    @Autowired
    CartDTOMapper cartDTOMapper;
    @Autowired
    private CartItemDTOMapper cartItemDTOMapper;

    @PutMapping("/add")
    public ResponseEntity<CartItemDTO> addCartItemToCart(
            @RequestBody CartItemRequest request,
            @RequestHeader("Authorization") String token
    ) {
        CartItemDTO cartItemDTO = cartService.addCartItemIntoCart(request, token);
        return new ResponseEntity<>(cartItemDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemDTO> updateCartItemQuantity(
            @PathVariable Long id,
            @RequestParam int quantity
    ) {
        CartItem cartItem = cartService.updateCartItemQuantity(id, quantity);
        CartItemDTO cartItemDTO = cartItemDTOMapper.apply(cartItem);
        return new ResponseEntity<>(cartItemDTO, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<CartDTO> removeItemFromCart(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        CartDTO cartDTO = cartService.removeItemFromCart(id, token);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<CartDTO> clearCartItemFromCart(@RequestHeader("Authorization") String token) {
        CartDTO cartDTO = cartService.clearCartItem(token);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cart> findCartById(@PathVariable Long id) {
        Cart cart = cartService.findCartById(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<CartDTO> findCartByUserId(@RequestHeader("Authorization") String token) throws InterruptedException {
        CartDTO cartDTO = cartService.findCartByUserId(token);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
}
