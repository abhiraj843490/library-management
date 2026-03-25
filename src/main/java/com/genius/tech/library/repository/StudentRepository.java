package com.genius.tech.library.repository;

import com.genius.tech.library.enums.FeeStatus;
import com.genius.tech.library.enums.Gender;
import com.genius.tech.library.enums.SeatSection;
import com.genius.tech.library.enums.SubscriptionStatus;
import com.genius.tech.library.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository
        extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    // ── lookups ───────────────────────────────────────────────────────────────

    Optional<Student> findByUserId(Long userId);

    boolean existsBySeatNumber(String seatNumber);

    // ── filtered list (used by StudentController GET /students) ───────────────

    @Query("""
    SELECT s FROM Student s
    JOIN s.user u
    WHERE (:search IS NULL
           OR LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%'))
           OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))
           OR LOWER(u.userCode) LIKE LOWER(CONCAT('%', :search, '%')))
      AND (:gender IS NULL OR s.gender = :gender)
      AND (:feeStatus IS NULL OR s.feeStatus = :feeStatus)
      AND (:subscriptionStatus IS NULL OR s.subscriptionStatus = :subscriptionStatus)
      AND (:activeOnly = FALSE OR u.isActive = TRUE)
    """)
    Page<Student> findAllWithFilters(
            @Param("search") String search,
            @Param("gender") Gender gender,
            @Param("feeStatus") FeeStatus feeStatus,
            @Param("subscriptionStatus") SubscriptionStatus subscriptionStatus,
            @Param("activeOnly") boolean activeOnly,
            Pageable pageable
    );

    // ── seat map ──────────────────────────────────────────────────────────────

    @Query("""
        SELECT s FROM Student s
        JOIN s.user u
        WHERE s.gender      = :gender
          AND s.seatSection = :seatSection
          AND u.isActive    = TRUE
          AND s.seatNumber IS NOT NULL
        """)
    List<Student> findOccupiedSeats(
            @Param("gender")      Gender gender,
            @Param("seatSection") SeatSection seatSection
    );

    // ── scheduler: expire subscriptions ──────────────────────────────────────

    @Modifying
    @Query("""
        UPDATE Student s SET s.subscriptionStatus = 'INACTIVE'
        WHERE s.subscriptionStatus = 'ACTIVE'
          AND s.subscriptionExpiry < :today
        """)
    int expireSubscriptions(@Param("today") LocalDate today);

    // ── counts for dashboard ──────────────────────────────────────────────────

    long countBySubscriptionStatus(SubscriptionStatus status);

    long countByFeeStatus(FeeStatus feeStatus);

    @Query("SELECT COUNT(s) FROM Student s JOIN s.user u WHERE u.isActive = TRUE")
    long countActiveStudents();
}

