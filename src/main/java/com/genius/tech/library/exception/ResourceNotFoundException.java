package com.genius.tech.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Thrown when a requested resource cannot be found — maps to HTTP 404. */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException student(Long id) {
        return new ResourceNotFoundException("Student not found with id: " + id);
    }

    public static ResourceNotFoundException user(Long id) {
        return new ResourceNotFoundException("User not found with id: " + id);
    }

    public static ResourceNotFoundException payment(Long id) {
        return new ResourceNotFoundException("Payment transaction not found with id: " + id);
    }

    public static ResourceNotFoundException booking(Long id) {
        return new ResourceNotFoundException("Booking not found with id: " + id);
    }
}
