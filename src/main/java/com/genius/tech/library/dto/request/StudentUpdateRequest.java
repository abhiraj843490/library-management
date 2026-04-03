package com.genius.tech.library.dto.request;

import com.genius.tech.library.enums.FeeStatus;
import com.genius.tech.library.enums.SeatSection;
import com.genius.tech.library.enums.SubscriptionStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Request body for PUT /api/students/{id}.
 * All fields are optional — only non-null fields are applied (patch semantics).
 */

public class StudentUpdateRequest {

    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;

    private SeatSection seatSection;

    @Size(max = 20)
    private String seatNumber;

    private SubscriptionStatus subscriptionStatus;

    @FutureOrPresent(message = "Subscription expiry must be today or in the future")
    private LocalDateTime subscriptionExpiry;

    private FeeStatus feeStatus;

    @DecimalMin(value = "0.00")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal monthlyFee;

    /** Update display name */
    @Size(max = 120)
    private String name;

    private boolean checkedIn;
    private boolean checkedOut;

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public StudentUpdateRequest() {
    }

    public StudentUpdateRequest(String phone, SeatSection seatSection, String seatNumber, SubscriptionStatus subscriptionStatus, LocalDateTime subscriptionExpiry,
                                FeeStatus feeStatus, BigDecimal monthlyFee, String name,
                                boolean checkedIn, boolean checkedOut) {
        this.phone = phone;
        this.seatSection = seatSection;
        this.seatNumber = seatNumber;
        this.subscriptionStatus = subscriptionStatus;
        this.subscriptionExpiry = subscriptionExpiry;
        this.feeStatus = feeStatus;
        this.monthlyFee = monthlyFee;
        this.name = name;
        this.checkedIn=checkedIn;
        this.checkedOut=checkedOut;
    }
    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public SubscriptionStatus getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public LocalDateTime getSubscriptionExpiry() {
        return subscriptionExpiry;
    }

    public void setSubscriptionExpiry(LocalDateTime subscriptionExpiry) {
        this.subscriptionExpiry = subscriptionExpiry;
    }

    public FeeStatus getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(FeeStatus feeStatus) {
        this.feeStatus = feeStatus;
    }

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(BigDecimal monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
