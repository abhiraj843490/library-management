package com.genius.tech.library.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    // error fields
    private String errorCode;
    private Map<String, String> details;

    private LocalDateTime timestamp;

    // ✅ No-args constructor (important for Jackson)
    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    // ✅ All-args constructor
    public ApiResponse(boolean success, String message, T data,
                       String errorCode, Map<String, String> details,
                       LocalDateTime timestamp) {

        this.success = success;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
        this.details = details;
        this.timestamp = (timestamp != null) ? timestamp : LocalDateTime.now();
    }

    // ── Static Factory Methods ──────────────────────────────────────

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(
                true,
                message,
                data,
                null,
                null,
                LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> ok(T data) {
        return ok("Operation successful", data);
    }

    public static <T> ApiResponse<T> error(String message, String errorCode) {
        return new ApiResponse<>(
                false,
                message,
                null,
                errorCode,
                null,
                LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> validationError(String message,
                                                     Map<String, String> details) {
        return new ApiResponse<>(
                false,
                message,
                null,
                "VALIDATION_ERROR",
                details,
                LocalDateTime.now()
        );
    }

    // ── Getters & Setters ───────────────────────────────────────────

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}