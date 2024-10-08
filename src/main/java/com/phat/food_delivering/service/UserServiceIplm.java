package com.phat.food_delivering.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.phat.food_delivering.dto.UserDTO;
import com.phat.food_delivering.dto.Mapper.UserDTOMapper;
import com.phat.food_delivering.model.Address;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.repository.UserRepository;
import com.phat.food_delivering.security.SecurityConstants;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceIplm implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDTOMapper userDTOMapper;

    @Autowired
    AddressService addressService;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        if (findUserByEmail(user.getEmail()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public Boolean existByEmail(String email) {
        return findUserByEmail(email) != null;
    }

    public User findUserBasedOnToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(Decoders.BASE64.decode(SecurityConstants.SECRET_KEY));
        DecodedJWT subject = JWT.require(algorithm)
                .build()
                .verify(token);
        String email = subject.getClaim("email").asString();
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public void updatePassword(String email, String password) {
        userRepository.updatePassword(password, email);
    }

    @Override
    public UserDTO addFavoriteAddress(Long addressId, String token) {
        Address address = addressService.getAddressById(addressId);
        User user = findUserBasedOnToken(token);
        user.setAddress(new Address());
        user.setAddress(address);
        userRepository.save(user);
        return userDTOMapper.apply(user);
    }
}
