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
 * Lightweight student row — returned on GET /students (paginated list).
 */

public class StudentSummaryResponse {

    private Long studentId;
    private String userCode;
    private String name;
    private String email;
    private String phone;
    private Gender gender;
    private SeatSection seatSection;
    private String seatNumber;
    private SubscriptionStatus subscriptionStatus;
    private LocalDateTime subscriptionExpiry;
    private FeeStatus feeStatus;
    private BigDecimal monthlyFee;
    private boolean checkedIn;
    private boolean active;
    private LocalDateTime currentCheckIn;
    private LocalDateTime currentCheckOut;
    private Long lastSessionMinutes;
    private Long totalAttendanceMinutes;

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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public StudentSummaryResponse(Long studentId,
                                  String userCode,
                                  String name,
                                  String email,
                                  String phone,
                                  Gender gender,
                                  SeatSection seatSection,
                                  String seatNumber,
                                  SubscriptionStatus subscriptionStatus,
                                  LocalDateTime subscriptionExpiry,
                                  FeeStatus feeStatus,
                                  BigDecimal monthlyFee,
                                  boolean checkedIn,
                                  Boolean active,
                                  LocalDateTime currentCheckIn,
                                  LocalDateTime currentCheckOut,
                                  Long lastSessionMinutes,
                                  Long totalAttendanceMinutes) {

        this.studentId = studentId;
        this.userCode = userCode;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.seatSection = seatSection;
        this.seatNumber = seatNumber;
        this.subscriptionStatus = subscriptionStatus;
        this.subscriptionExpiry = subscriptionExpiry;
        this.feeStatus = feeStatus;
        this.monthlyFee = monthlyFee;
        this.checkedIn = checkedIn;
        this.active = active;
        this.currentCheckIn = currentCheckIn;
        this.currentCheckOut = currentCheckOut;
        this.lastSessionMinutes = lastSessionMinutes;
        this.totalAttendanceMinutes = totalAttendanceMinutes;
    }
}
