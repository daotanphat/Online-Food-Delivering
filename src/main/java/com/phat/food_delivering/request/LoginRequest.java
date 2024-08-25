package com.phat.food_delivering.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
