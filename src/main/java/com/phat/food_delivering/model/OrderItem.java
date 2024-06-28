package com.phat.food_delivering.model;

import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Size;
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
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Food food;

    //@Size(min = 0)
    @NonNull
    private int quantity;
    private Long totalPrice;
    private List<String> ingredients = new ArrayList<>();
}
