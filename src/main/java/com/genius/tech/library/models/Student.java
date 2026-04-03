package com.genius.tech.library.models;

import com.genius.tech.library.enums.FeeStatus;
import com.genius.tech.library.enums.Gender;
import com.genius.tech.library.enums.SeatSection;
import com.genius.tech.library.enums.SubscriptionStatus;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Extended profile for STUDENT role.
 * One-to-one with {@link User}.
 *
 * Table: students
 */
@Entity
@Table(name = "students",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_seat_number", columnNames = "seat_number")
        },
        indexes = {
                @Index(name = "idx_student_seat", columnList = "seat_number"),
                @Index(name = "idx_student_fee_status", columnList = "fee_status"),
                @Index(name = "idx_student_subscription", columnList = "subscription_status")
        })
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ── relationship ─────────────────────────────────────────────────────────
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "fk_students_user"))
    private User user;

    // ── personal info ────────────────────────────────────────────────────────
    @Column(nullable = false, length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Gender gender;

    // ── seat ─────────────────────────────────────────────────────────────────
    @Enumerated(EnumType.STRING)
    @Column(name = "seat_section", nullable = false, length = 10)
    private SeatSection seatSection;

    @Column(name = "seat_number", length = 20)
    private String seatNumber;            // nullable until allocated

    // ── subscription ─────────────────────────────────────────────────────────
    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_status", nullable = false, length = 10)
    private SubscriptionStatus subscriptionStatus = SubscriptionStatus.ACTIVE;

    @Column(name = "subscription_expiry", nullable = false)
    private LocalDateTime subscriptionExpiry;

    @Column(name = "monthly_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal monthlyFee = new BigDecimal("8500.00");

    @Enumerated(EnumType.STRING)
    @Column(name = "fee_status", nullable = false, length = 10)
    private FeeStatus feeStatus = FeeStatus.PENDING;

    // ── live attendance state ────────────────────────────────────────────────
    @Column(name = "current_check_in")
    private LocalDateTime currentCheckIn;     // null when not checked in

    @Column(name = "current_check_out")
    private LocalDateTime currentCheckOut;    // last check-out time

    @Column(name = "last_session_minutes")
    private Long lastSessionMinutes;

    @Column(name = "total_attendance_minutes")
    private Long totalAttendanceMinutes;

    // ── audit ────────────────────────────────────────────────────────────────
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // ── child collections ────────────────────────────────────────────────────
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentTransaction> payments = new ArrayList<>();

    // ── helpers ──────────────────────────────────────────────────────────────
    public boolean isCheckedIn() {
        return currentCheckIn != null;
    }

    public boolean hasActiveSubscription() {
        return SubscriptionStatus.ACTIVE == subscriptionStatus
                && (subscriptionExpiry == null || !subscriptionExpiry.isBefore(LocalDateTime.now()));
    }
    public Student(){}
    public Student(User user,
                   String phone,
                   Gender gender,
                   SeatSection seatSection,
                   String seatNumber,
                   LocalDate enrollmentDate,
                   SubscriptionStatus subscriptionStatus,
                   LocalDateTime subscriptionExpiry,
                   BigDecimal monthlyFee,
                   FeeStatus feeStatus
                   ) {

        this.user = user;
        this.phone = phone;
        this.gender = gender;
        this.seatSection = seatSection;
        this.seatNumber = seatNumber;
        this.enrollmentDate = enrollmentDate;
        this.subscriptionStatus = subscriptionStatus;
        this.subscriptionExpiry = subscriptionExpiry;
        this.monthlyFee = monthlyFee;
        this.feeStatus = feeStatus;
        this.lastSessionMinutes = 0L;
        this.totalAttendanceMinutes = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<PaymentTransaction> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentTransaction> payments) {
        this.payments = payments;
    }
}
