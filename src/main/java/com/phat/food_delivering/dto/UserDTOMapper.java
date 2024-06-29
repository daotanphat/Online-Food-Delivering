package com.phat.food_delivering.dto;

import com.phat.food_delivering.model.User;
import com.phat.food_delivering.request.UserRequest;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getAddresses()
        );
    }

    public User toUser(UserRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        return user;
    }
}
