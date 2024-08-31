package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.UserDTO;
import com.phat.food_delivering.model.User;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);

    User saveUser(User user);

    Boolean existByEmail(String email);

    User findUserBasedOnToken(String token);

    List<UserDTO> findAllUsers();

    void updatePassword(String email, String password);

    UserDTO addFavoriteAddress(Long addressId, String token);
}
