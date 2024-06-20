package com.phat.food_delivering.response;

import com.phat.food_delivering.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private USER_ROLE role;
}
