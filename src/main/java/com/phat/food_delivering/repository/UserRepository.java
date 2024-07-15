package com.phat.food_delivering.repository;

import com.phat.food_delivering.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String userName);

    List<User> findAll();

    @Query(
            "UPDATE User u " +
            " SET u.password = :password" +
            " WHERE u.email = :email"
    )
    void updatePassword(@RequestParam("password") String password, @RequestParam("email") String email);
}
