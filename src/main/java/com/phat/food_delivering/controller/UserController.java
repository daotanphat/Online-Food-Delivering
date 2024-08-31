package com.phat.food_delivering.controller;

import com.phat.food_delivering.dto.Mapper.UserDTOMapper;
import com.phat.food_delivering.dto.UserDTO;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.security.SecurityConstants;
import com.phat.food_delivering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDTOMapper userDTOMapper;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String token) {
        token = token.replace(SecurityConstants.BEARER, "");
        User user = userService.findUserBasedOnToken(token);
        return new ResponseEntity<>(userDTOMapper.apply(user), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/add_favorite_address/{id}")
    public ResponseEntity<UserDTO> addFavoriteAddress(@RequestHeader("Authorization") String token,
                                                      @PathVariable Long id) {
        token = token.replace(SecurityConstants.BEARER, "");
        UserDTO user = userService.addFavoriteAddress(id, token);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
