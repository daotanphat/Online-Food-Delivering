package com.phat.food_delivering.service;

import com.phat.food_delivering.model.User;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);

    User saveUser(User user);
    User findUserBasedOnToken(String token);
    List<User> findAllUsers();
}
