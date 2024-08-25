package com.phat.food_delivering.request;

import com.phat.food_delivering.model.USER_ROLE;
import lombok.Data;

@Data
public class UserRequest {
    private String fullName;
    private String email;
    private String password;
    private USER_ROLE role;
}
