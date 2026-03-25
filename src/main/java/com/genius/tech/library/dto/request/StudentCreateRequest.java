package com.genius.tech.library.dto.request;

import com.genius.tech.library.enums.Gender;
import com.genius.tech.library.enums.SeatSection;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentCreateRequest {

    // ── user fields ──────────────────────────────────────────────────────────
    @NotBlank(message = "Name is required")
    @Size(max = 120, message = "Name must not exceed 120 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address")
    @Size(max = 150)
    private String email;

    /**
     * Plain-text password — will be BCrypt hashed in the service layer.
     * Minimum 8 characters; frontend should enforce stronger rules.
     */
//    @NotBlank(message = "Password is required")
//    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    // ── student fields ───────────────────────────────────────────────────────
    @NotBlank(message = "Phone number is required")
    @Size(max = 20)
    private String phone;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Seat section is required")
    private SeatSection seatSection;

    /** Optional at enrolment; can be assigned later via /seats/assign */
    private String seatNumber;

    private LocalDateTime subscriptionExpiry;

    /**
     * Override default monthly fee (8500.00).
     * If null the service applies the default.
     */
    @DecimalMin(value = "0.00", message = "Monthly fee must be non-negative")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal monthlyFee;

    public StudentCreateRequest() {
    }

    public StudentCreateRequest(String name, String email, String password, String phone, Gender gender, SeatSection seatSection, String seatNumber, LocalDateTime subscriptionExpiry, BigDecimal monthlyFee) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.seatSection = seatSection;
        this.seatNumber = seatNumber;
        this.subscriptionExpiry = subscriptionExpiry;
        this.monthlyFee = monthlyFee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public SeatSection getSeatSection() {
        return seatSection;
    }

    public void setSeatSection(SeatSection seatSection) {
        this.seatSection = seatSection;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public LocalDateTime getSubscriptionExpiry() {
        return subscriptionExpiry;
    }

    public void setSubscriptionExpiry(LocalDateTime subscriptionExpiry) {
        this.subscriptionExpiry = subscriptionExpiry;
    }

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(BigDecimal monthlyFee) {
        this.monthlyFee = monthlyFee;
    }
}
