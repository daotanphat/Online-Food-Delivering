package com.phat.food_delivering;

import com.phat.food_delivering.exception.EntityNotFoundException;
import com.phat.food_delivering.exception.ListEntityNotFoundException;
import com.phat.food_delivering.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationHandleException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotfoundException(RuntimeException ex) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ListEntityNotFoundException.class})
    public ResponseEntity<Object> handleListEntityNotFoundException(RuntimeException ex) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
    }
}
