package com.phat.food_delivering.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name of food can not be blank")
    @NonNull
    private String name;
    private String description;

    //@Size(min = 0)
    private Long price;

    @ManyToOne
    @JoinColumn(name = "food_category_id")
    private Category foodCategory;

    @Column(length = 1000)
    @ElementCollection
    private List<String> images;
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    private boolean isVegetarian;
    private boolean isSeasonal;

    @ManyToMany(mappedBy = "foods", cascade = CascadeType.PERSIST)
    private List<IngredientsItem> ingredients = new ArrayList<>();

    @Past(message = "Date can not be in the past")
    private Date creationDate;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();
}
