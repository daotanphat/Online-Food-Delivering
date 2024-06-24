package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.RestaurantDTO;
import com.phat.food_delivering.exception.EntityNotFoundException;
import com.phat.food_delivering.model.Address;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.repository.RestaurantRepository;
import com.phat.food_delivering.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressService.save(req.getAddress());
        Restaurant restaurant = new Restaurant();

        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setImages(req.getImages());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(CreateRestaurantRequest req, Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setCuisineType(req.getCuisineType());
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) throws Exception {
        restaurantRepository.deleteById(id);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String key) {
        return restaurantRepository.findBySearchQuery(key);
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        return unwrapRestaurant(restaurant, id);
    }

    @Override
    public Restaurant findRestaurantByUserId(Long userId) throws Exception {
        return restaurantRepository.findByOwnerId(userId);
    }

    @Override
    public RestaurantDTO addToFavorite(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setTitle(restaurant.getName());
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
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }

    static Restaurant unwrapRestaurant(Optional<Restaurant> entity, Long id) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(Restaurant.class, id);
        }
    }
}
