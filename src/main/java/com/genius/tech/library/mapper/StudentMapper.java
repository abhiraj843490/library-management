package com.genius.tech.library.mapper;

import com.genius.tech.library.dto.response.PaymentResponse;
import com.genius.tech.library.dto.response.StudentResponse;
import com.genius.tech.library.dto.response.StudentSummaryResponse;
import com.genius.tech.library.models.PaymentTransaction;
import com.genius.tech.library.models.Student;

/**
 * Pure static mapper — no Spring dependency so it is easy to unit-test.
 */
public final class StudentMapper {

    private StudentMapper() {}

    // ── Student → StudentResponse (full profile) ─────────────────────────────
    public static StudentResponse toResponse(Student s) {
        return new StudentResponse(
                s.getId(),
                s.getUser().getId(),
                s.getUser().getUserCode(),
                s.getUser().getName(),
                s.getUser().getEmail(),
                s.getPhone(),
                s.getGender(),
                s.getSeatSection(),
                s.getSeatNumber(),
                s.getEnrollmentDate(),
                s.getSubscriptionStatus(),
                s.getSubscriptionExpiry(),
                s.getMonthlyFee(),
                s.getFeeStatus(),
                s.isCheckedIn(),
                s.getCurrentCheckIn(),
                s.getCurrentCheckOut(),
                s.getLastSessionMinutes(),
                s.getTotalAttendanceMinutes(),
                s.getUser().getIsActive(),
                s.getCreatedAt(),
                s.getUpdatedAt()
        );
    }

    // ── Student → StudentSummaryResponse (list row) ──────────────────────────
    public static StudentSummaryResponse toSummary(Student s) {
        return new StudentSummaryResponse(
                s.getId(),
                s.getUser().getUserCode(),
                s.getUser().getName(),
                s.getUser().getEmail(),
                s.getPhone(),
                s.getGender(),
                s.getSeatSection(),
                s.getSeatNumber(),
                s.getSubscriptionStatus(),
                s.getSubscriptionExpiry(),
                s.getFeeStatus(),
                s.getMonthlyFee(),
                s.isCheckedIn(),
                s.getUser().getIsActive(),
                s.getCurrentCheckIn(),
                s.getCurrentCheckOut(),
                s.getLastSessionMinutes(),
                s.getTotalAttendanceMinutes()
        );
    }

    // ── PaymentTransaction → PaymentResponse ─────────────────────────────────
    public static PaymentResponse toPaymentResponse(PaymentTransaction p) {
        return new PaymentResponse(
                p.getId(),
                p.getTransactionCode(),
                p.getStudent().getId(),
                p.getStudent().getUser().getName(),
                p.getStudent().getUser().getUserCode(),
                p.getAmount(),
                p.getStatus(),
                p.getPaymentMethod(),
                p.getPaidAt(),
                p.getCreatedAt());
    }
}
