package com.phat.food_delivering.repository;

import com.phat.food_delivering.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    void deleteById(Long id);

    List<Restaurant> findAll();

    @Query(
            "SELECT r FROM Restaurant r" +
                    " WHERE lower(r.name) LIKE lower(CONCAT('%', :searchQuery, '%')) " +
                    " OR lower(r.cuisineType) LIKE lower(CONCAT('%', :searchQuery, '%')) "
    )
    List<Restaurant> findBySearchQuery(String searchQuery);

    Restaurant findByOwnerId(Long id);

    Restaurant save(Restaurant restaurant);
}
