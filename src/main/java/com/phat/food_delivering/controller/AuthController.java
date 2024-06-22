package com.phat.food_delivering.controller;

import com.phat.food_delivering.model.Cart;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.service.CartService;
import com.phat.food_delivering.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@Valid @RequestBody User user) throws Exception {
        User isEmailExist = userService.findUserByEmail(user.getEmail());
        if (isEmailExist != null) {
            throw new Exception("Email is already used in with another account");
        }

        User savedUser = userService.saveUser(user);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartService.saveCart(cart);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
