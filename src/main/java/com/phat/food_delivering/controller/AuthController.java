package com.phat.food_delivering.controller;

import com.phat.food_delivering.dto.Mapper.UserDTOMapper;
import com.phat.food_delivering.dto.UserDTO;
import com.phat.food_delivering.model.Cart;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.request.UserRequest;
import com.phat.food_delivering.service.CartService;
import com.phat.food_delivering.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    UserDTOMapper userDTOMapper;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@Valid @RequestBody UserRequest request) throws Exception {
        User isEmailExist = userService.findUserByEmail(request.getEmail());
        if (isEmailExist != null) {
            throw new Exception("Email is already used in with another account");
        }

        User user = userDTOMapper.toUser(request);
        User savedUser = userService.saveUser(user);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartService.saveCart(cart);

        return new ResponseEntity<>(userDTOMapper.apply(savedUser), HttpStatus.CREATED);
    }

    @PutMapping("/update-password/{password}")
    public ResponseEntity<HttpStatus> updatePassword(
            @RequestHeader("Authorization") String token,
            @PathVariable String password){
        User user = userService.findUserBasedOnToken(token);
        userService.updatePassword(user.getEmail(), password);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
