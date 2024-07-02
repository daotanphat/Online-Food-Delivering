package com.phat.food_delivering.controller.Admin;

import com.phat.food_delivering.dto.CategoryDTO;
import com.phat.food_delivering.request.CreateCategoryRequest;
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
    public ResponseEntity<CategoryDTO> createCategory(
            @RequestBody CreateCategoryRequest request,
            @RequestHeader("Authorization") String token) {
        CategoryDTO category = categoryService.createCategory(request, token);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
}
