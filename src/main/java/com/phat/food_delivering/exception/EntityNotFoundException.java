package com.phat.food_delivering.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> entity, Long id){
        super("The " + entity.getSimpleName().toLowerCase() + " with id " + id + " was not found.");
    }
}
