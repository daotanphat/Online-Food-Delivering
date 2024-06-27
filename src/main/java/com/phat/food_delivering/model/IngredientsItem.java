package com.phat.food_delivering.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name of ingredient item can not be blank")
    @NonNull
    private String name;

    @ManyToOne
    private IngredientCategory category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;


    @ManyToMany
    @JoinTable(
            name = "ingredient_food",
            joinColumns = @JoinColumn(name = "ingredientId"),
            inverseJoinColumns = @JoinColumn(name = "foodId")
    )
    private List<Food> foods = new ArrayList<>();

    private boolean inStock = true;
}
