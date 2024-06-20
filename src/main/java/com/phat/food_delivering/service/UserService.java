package com.phat.food_delivering.service;

import com.phat.food_delivering.model.User;

public interface UserService {
    User findUserByEmail(String email);

    User saveUser(User user);
}
