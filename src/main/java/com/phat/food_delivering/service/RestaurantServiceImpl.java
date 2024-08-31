package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.Mapper.AddressDTOMapper;
import com.phat.food_delivering.dto.RestaurantDTO;
import com.phat.food_delivering.dto.Mapper.RestaurantDTOMapper;
import com.phat.food_delivering.dto.RestaurantDTOO;
import com.phat.food_delivering.exception.EntityNotFoundException;
import com.phat.food_delivering.model.*;
import com.phat.food_delivering.repository.OrderItemRepository;
import com.phat.food_delivering.repository.OrderRepository;
import com.phat.food_delivering.repository.RestaurantRepository;
import com.phat.food_delivering.request.RestaurantRequest;
import com.phat.food_delivering.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Autowired
    RestaurantDTOMapper restaurantDTOMapper;

    @Autowired
    AddressDTOMapper addressDTOMapper;

    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Restaurant createRestaurant(RestaurantRequest restaurantRequest, String token) {
        token = token.replace(SecurityConstants.BEARER, "");
        User user = userService.findUserBasedOnToken(token);

        List<Address> addresses = addressService.getAddresses();
        Address address = restaurantRequest.getAddress();
        if (!addressService.checkDuplicateAddress(addressDTOMapper.apply(address), addresses)) {
            address = addressService.save(restaurantRequest.getAddress());
        } else {
            address = addressService.getAddressById(address.getId());
        }
        Restaurant restaurant = restaurantDTOMapper.toRestaurant(restaurantRequest, user, address);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(RestaurantRequest req, Long id) {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setCuisineType(req.getCuisineType());
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {
        Restaurant restaurant = findRestaurantById(id);
        List<Food> foods = restaurant.getFoods();
        if (!foods.isEmpty()) {
            for (Food food : foods) {
                for (OrderItem orderItem : food.getOrderItems()) {
                    orderRepository.deleteById(orderItem.getId());
                }
            }
        }
        restaurantRepository.deleteById(id);
    }

    @Override
    public List<RestaurantDTOO> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDTOO> searchRestaurant(String key) {
        return restaurantRepository.findBySearchQuery(key)
                .stream()
                .map(restaurantDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        return unwrapRestaurant(restaurantOptional, id);
    }

    @Override
    public RestaurantDTOO findRestaurantByUserId(Long userId) {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant == null) {
            return RestaurantDTOO.noArgConstructor();
        }
        return restaurantDTOMapper.apply(restaurant);
    }

    @Override
    public RestaurantDTO addToFavorite(Long restaurantId, User user) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setDescription(restaurant.getDescription());
        restaurantDTO.setImages(restaurant.getImages());

        if (user.getFavorites().stream().anyMatch(r -> r.getId().equals(restaurantDTO.getId()))) {
            user.getFavorites().removeIf(r -> r.getId().equals(restaurantDTO.getId()));
        } else {
            user.getFavorites().add(restaurantDTO);
        }

        userService.saveUser(user);
        return restaurantDTO;
    }

    @Override
    public RestaurantDTOO updateRestaurantStatus(Long restaurantId) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return restaurantDTOMapper.apply(savedRestaurant);
    }

    @Override
    public Restaurant findRestaurantByToken(String token) {
        token = token.replace(SecurityConstants.BEARER, "");
        User user = userService.findUserBasedOnToken(token);
        RestaurantDTOO restaurantDTOO = findRestaurantByUserId(user.getId());
        Restaurant restaurant = findRestaurantById(restaurantDTOO.id());
        return restaurant;
    }

    static Restaurant unwrapRestaurant(Optional<Restaurant> entity, Long id) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(Restaurant.class, id);
        }
    }
}
