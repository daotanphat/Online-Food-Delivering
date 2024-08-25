package com.phat.food_delivering.controller;

import com.phat.food_delivering.model.Cart;
import com.phat.food_delivering.model.USER_ROLE;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.request.LoginRequest;
import com.phat.food_delivering.response.AuthResponse;
import com.phat.food_delivering.security.jwt.JwtUtils;
import com.phat.food_delivering.security.services.UserDetailsImpl;
import com.phat.food_delivering.service.CartService;
import com.phat.food_delivering.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

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

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login success");
        authResponse.setRole(USER_ROLE.valueOf(roles.get(0)));

        return ResponseEntity.ok(authResponse);
    }
}
