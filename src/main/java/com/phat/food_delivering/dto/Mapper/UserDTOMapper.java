package com.phat.food_delivering.dto.Mapper;

import com.phat.food_delivering.dto.AddressDTO;
import com.phat.food_delivering.dto.UserDTO;
import com.phat.food_delivering.model.Address;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.request.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class UserDTOMapper implements Function<User, UserDTO> {
    private AddressDTOMapper addressDTOMapper;

    @Override
    public UserDTO apply(User user) {
        AddressDTO addressDTO = new AddressDTO();
        if (user.getAddress() != null) {
            addressDTO = addressDTOMapper.apply(user.getAddress());
        }
        List<AddressDTO> addressDTOS = new ArrayList<>();
        for (Address address : user.getAddresses()) {
            addressDTOS.add(addressDTOMapper.apply(address));
        }
        return new UserDTO(
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                addressDTO,
                addressDTOS,
                user.getFavorites()
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
