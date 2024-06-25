package com.phat.food_delivering.controller;

import com.phat.food_delivering.model.Category;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.request.CreateCategoryRequest;
import com.phat.food_delivering.security.SecurityConstants;
import com.phat.food_delivering.service.CategoryService;
import com.phat.food_delivering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/category")
public class AdminCategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestBody CreateCategoryRequest request,
            @RequestHeader("Authorization") String token) {
        token = token.replace(SecurityConstants.BEARER, "");
        User user = userService.findUserBasedOnToken(token);
        Category category = categoryService.createCategory(request, user.getId());
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
}
