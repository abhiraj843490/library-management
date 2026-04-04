package com.genius.tech.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Thrown when a business rule is violated — maps to HTTP 422. */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class BusinessException extends RuntimeException {

    private final String errorCode;

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    // ── named factories for common cases ─────────────────────────────────────

    public static BusinessException duplicateEmail(String email) {
        return new BusinessException(
                "Email already in use: " + email,
                "DUPLICATE_EMAIL");
    }

    public static BusinessException seatAlreadyOccupied(String seatNumber) {
        return new BusinessException(
                "Seat " + seatNumber + " is already assigned to another active student",
                "SEAT_OCCUPIED");
    }

    public static BusinessException genderZoneMismatch() {
        return new BusinessException(
                "Seat zone does not match student gender",
                "GENDER_ZONE_MISMATCH");
    }

    public static BusinessException alreadyCheckedIn(String name) {
        return new BusinessException(
                name + " is already checked in",
                "ALREADY_CHECKED_IN");
    }

    public static BusinessException alreadyCheckedOutForToday(String name) {
        return new BusinessException(
                name + " has already checked out for today",
                "ALREADY_CHECKED_OUT_TODAY");
    }

    public static BusinessException notCheckedIn(String name) {
        return new BusinessException(
                name + " is not currently checked in",
                "NOT_CHECKED_IN");
    }

    public static BusinessException inactiveStudent(String name) {
        return new BusinessException(
                name + "'s account is inactive",
                "STUDENT_INACTIVE");
    }
}
