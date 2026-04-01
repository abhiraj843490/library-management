package com.genius.tech.library.dto.response;


import com.genius.tech.library.enums.FeeStatus;
import com.genius.tech.library.enums.Gender;
import com.genius.tech.library.enums.SeatSection;
import com.genius.tech.library.enums.SubscriptionStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Full student profile — returned on GET /students/{id}, POST /students, PUT /students/{id}.
 */

public class StudentResponse {

    // ── identity ─────────────────────────────────────────────────────────────
    private Long studentId;
    private Long userId;
    private String userCode;          // STU-001
    private String name;
    private String email;
    private String phone;

    // ── seat ─────────────────────────────────────────────────────────────────
    private Gender gender;
    private SeatSection seatSection;
    private String seatNumber;

    // ── subscription ─────────────────────────────────────────────────────────
    private LocalDate enrollmentDate;
    private SubscriptionStatus subscriptionStatus;
    private LocalDateTime subscriptionExpiry;
    private BigDecimal monthlyFee;
    private FeeStatus feeStatus;

    // ── live attendance ───────────────────────────────────────────────────────
    private boolean checkedIn;
    private LocalDateTime currentCheckIn;
    private LocalDateTime currentCheckOut;
    private Long lastSessionMinutes;
    private Long totalAttendanceMinutes;

    // ── account state ─────────────────────────────────────────────────────────
    private boolean active;           // user.isActive

    // ── audit ─────────────────────────────────────────────────────────────────
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StudentResponse() {
    }
    public StudentResponse(Long studentId,
                           Long userId,
                           String userCode,
                           String name,
                           String email,
                           String phone,
                           Gender gender,
                           SeatSection seatSection,
                           String seatNumber,
                           LocalDate enrollmentDate,
                           SubscriptionStatus subscriptionStatus,
                           LocalDateTime subscriptionExpiry,
                           BigDecimal monthlyFee,
                           FeeStatus feeStatus,
                           boolean checkedIn,
                           LocalDateTime currentCheckIn,
                           LocalDateTime currentCheckOut,
                           Long lastSessionMinutes,
                           Long totalAttendanceMinutes,
                           Boolean active,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt) {

        this.studentId = studentId;
        this.userId = userId;
        this.userCode = userCode;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.seatSection = seatSection;
        this.seatNumber = seatNumber;
        this.enrollmentDate = enrollmentDate;
        this.subscriptionStatus = subscriptionStatus;
        this.subscriptionExpiry = subscriptionExpiry;
        this.monthlyFee = monthlyFee;
        this.feeStatus = feeStatus;
        this.checkedIn = checkedIn;
        this.currentCheckIn = currentCheckIn;
        this.currentCheckOut = currentCheckOut;
        this.lastSessionMinutes = lastSessionMinutes;
        this.totalAttendanceMinutes = totalAttendanceMinutes;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }



    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
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

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(BigDecimal monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public FeeStatus getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(FeeStatus feeStatus) {
        this.feeStatus = feeStatus;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public LocalDateTime getCurrentCheckIn() {
        return currentCheckIn;
    }

    public void setCurrentCheckIn(LocalDateTime currentCheckIn) {
        this.currentCheckIn = currentCheckIn;
    }

    public LocalDateTime getCurrentCheckOut() {
        return currentCheckOut;
    }

    public void setCurrentCheckOut(LocalDateTime currentCheckOut) {
        this.currentCheckOut = currentCheckOut;
    }

    public Long getLastSessionMinutes() {
        return lastSessionMinutes;
    }

    public void setLastSessionMinutes(Long lastSessionMinutes) {
        this.lastSessionMinutes = lastSessionMinutes;
    }

    public Long getTotalAttendanceMinutes() {
        return totalAttendanceMinutes;
    }

    public void setTotalAttendanceMinutes(Long totalAttendanceMinutes) {
        this.totalAttendanceMinutes = totalAttendanceMinutes;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
