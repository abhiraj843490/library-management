package com.genius.tech.library.service;


import com.genius.tech.library.dto.request.PaymentRequest;
import com.genius.tech.library.dto.request.StudentCreateRequest;
import com.genius.tech.library.dto.request.StudentUpdateRequest;
import com.genius.tech.library.dto.response.AttendanceResponse;
import com.genius.tech.library.dto.response.PaymentResponse;
import com.genius.tech.library.dto.response.StudentResponse;
import com.genius.tech.library.dto.response.StudentSummaryResponse;
import com.genius.tech.library.enums.*;
import com.genius.tech.library.exception.BusinessException;
import com.genius.tech.library.exception.ResourceNotFoundException;
import com.genius.tech.library.mapper.StudentMapper;
import com.genius.tech.library.models.PaymentTransaction;
import com.genius.tech.library.models.Seat;
import com.genius.tech.library.models.Student;
import com.genius.tech.library.models.User;
import com.genius.tech.library.repository.PaymentTransactionRepository;
import com.genius.tech.library.repository.SeatRepository;
import com.genius.tech.library.repository.StudentRepository;
import com.genius.tech.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Core service for student lifecycle management.
 * <p>
 * Responsibilities:
 * - Enrolment  : create User + Student in one transaction, auto-generate user code
 * - Update      : patch-style update of mutable fields
 * - Deactivation: soft-delete via user.isActive = false
 * - Deletion    : hard delete (cascades to students row via FK)
 * - Payments    : record fee payment, update fee/subscription status
 * - Attendance  : check-in / check-out state machine with guard rules
 * - Scheduler   : daily subscription expiry job
 */
@Service
@Transactional(readOnly = true)
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PaymentTransactionRepository paymentRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SeatRepository seatRepository;

    /**
     * Enrol a new student.
     * <p>
     * Flow:
     * 1. Validate uniqueness of email
     * 2. Auto-generate user code (STU-001, STU-002 …)
     * 3. Persist User with BCrypt-hashed password
     * 4. Persist Student with default / provided monthly fee
     * 5. Return full StudentResponse
     */
    @Transactional
    public StudentResponse enroll(StudentCreateRequest req) {

        // ── guard: duplicate email ────────────────────────────────────────────
        if (userRepository.existsByEmail(req.getEmail())) {
            throw BusinessException.duplicateEmail(req.getEmail());
        }

        // ── guard: seat taken (if seat supplied at enrolment) ─────────────────
        if (req.getSeatNumber() != null
                && !req.getSeatNumber().isBlank()
                && studentRepository.existsBySeatNumber(req.getSeatNumber())) {
            throw BusinessException.seatAlreadyOccupied(req.getSeatNumber());
        }

        // ── create User ───────────────────────────────────────────────────────
        User user = new User(
                generateUserCode(Role.STUDENT),
                req.getName().trim(),
                req.getEmail().toLowerCase().trim(),
                passwordEncoder.encode(req.getPassword()!=null ? req.getPassword() : "student"),
                Role.STUDENT,
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        user = userRepository.save(user);

        // ── create Student ────────────────────────────────────────────────────
        BigDecimal fee = req.getMonthlyFee() != null
                ? req.getMonthlyFee()
                : new BigDecimal("400.00");
// ai suggestion seat changes remove
        Seat seat = allocateSeat(req.getGender(), req.getSeatSection());
        Student student = new Student(
                user,
                req.getPhone().trim(),
                req.getGender(),
                req.getSeatSection(),
                seat.getSeatNumber(),
                LocalDate.now(),
                SubscriptionStatus.ACTIVE,
                (req.getSubscriptionExpiry()==null ? LocalDateTime.now().plusMonths(3) : req.getSubscriptionExpiry()),
                fee,
                FeeStatus.PENDING
        );

        student = studentRepository.save(student);

        seat.setStatus(SeatStatus.ALLOCATED);
        seat.setStudentId(student.getId());
        seatRepository.save(seat);

        System.out.println(user.getUserCode() + ", " + user.getEmail());
        return StudentMapper.toResponse(student);
    }

    private Seat allocateSeat(Gender gender, SeatSection section) {
        return seatRepository.findAvailableForUpdate(gender, section).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No seat available"));
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 2. READ
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Paginated, filtered student list.
     *
     * @param search             partial match on name / email / userCode
     * @param gender             optional gender filter
     * @param feeStatus          optional fee status filter
     * @param subscriptionStatus optional subscription status filter
     * @param activeOnly         when true, excludes deactivated accounts
     */
    public Page<StudentSummaryResponse> listStudents(
            String search,
            Gender gender,
            FeeStatus feeStatus,
            SubscriptionStatus subscriptionStatus,
            boolean activeOnly,
            Pageable pageable) {

        return studentRepository
                .findAllWithFilters(search, gender, feeStatus, subscriptionStatus, activeOnly, pageable)
                .map(StudentMapper::toSummary);
    }

    /**
     * Fetch full student profile by primary key.
     */
    public StudentResponse getById(Long id) {
        return StudentMapper.toResponse(requireStudent(id));
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 3. UPDATE (patch semantics — only non-null fields are applied)
    // ═════════════════════════════════════════════════════════════════════════

    @Transactional
    public StudentResponse update(Long id, StudentUpdateRequest req) {

        Student student = requireStudent(id);
        User user = student.getUser();

        // ── user-level fields ─────────────────────────────────────────────────
        if (req.getName() != null && !req.getName().isBlank()) {
            user.setName(req.getName().trim());
        }

        // ── contact ───────────────────────────────────────────────────────────
        if (req.getPhone() != null && !req.getPhone().isBlank()) {
            student.setPhone(req.getPhone().trim());
        }

        // ── seat ──────────────────────────────────────────────────────────────
        if (req.getSeatSection() != null) {
            student.setSeatSection(req.getSeatSection());
        }

        if (req.getSeatNumber() != null) {
            String newSeat = req.getSeatNumber().isBlank() ? null : req.getSeatNumber();
            // validate new seat is free (unless it's the same seat)
            if (newSeat != null
                    && !newSeat.equals(student.getSeatNumber())
                    && studentRepository.existsBySeatNumber(newSeat)) {
                throw BusinessException.seatAlreadyOccupied(newSeat);
            }
            student.setSeatNumber(newSeat);
        }

        // ── subscription ──────────────────────────────────────────────────────
        if (req.getSubscriptionStatus() != null && req.getSubscriptionStatus() == SubscriptionStatus.INACTIVE) {
            student.setSubscriptionStatus(req.getSubscriptionStatus());
            if (student.getSeatNumber() != null) {
                Student finalStudent = student;
                Seat seat = seatRepository.findBySeatNumber(student.getSeatNumber())
                        .orElseThrow(() -> new IllegalArgumentException("Seat not found: " + finalStudent.getSeatNumber()));

                seat.setStatus(SeatStatus.AVAILABLE);
                seat.setStudentId(null);
                seatRepository.save(seat);

                student.setSeatNumber(null);
            }
        }

        if (req.getSubscriptionExpiry() != null) {
            student.setSubscriptionExpiry(req.getSubscriptionExpiry());
        }

        // ── fee ───────────────────────────────────────────────────────────────
        if (req.getFeeStatus() != null) {
            student.setFeeStatus(req.getFeeStatus());
        }

        if (req.getMonthlyFee() != null) {
            student.setMonthlyFee(req.getMonthlyFee());
        }
        userRepository.save(user);
        student = studentRepository.save(student);

        System.out.println("Updated student id={} ({})" + id + "," + user.getUserCode());
        return StudentMapper.toResponse(student);
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 4. DEACTIVATE  (soft delete — preserves all history)
    // ═════════════════════════════════════════════════════════════════════════

    @Transactional
    public void deactivate(Long id) {
        Student student = requireStudent(id);
        student.getUser().setIsActive(false);
        releaseSeatIfAssigned(student);
        // Force check-out if currently checked in
        if (student.isCheckedIn()) {
            student.setCurrentCheckIn(null);
        }
        userRepository.save(student.getUser());
        System.out.println("Deactivated student id={}" + "," + id);
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 5. DELETE  (hard delete — also removes User via CASCADE)
    // ═════════════════════════════════════════════════════════════════════════

    @Transactional
    public void delete(Long id) {
        Student student = requireStudent(id);
        userRepository.delete(student.getUser());   // cascades → students row
        System.out.println("Deleted student id={}" + "," + id);
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 6. PAYMENT  — record monthly fee collection
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Record a fee payment for a student.
     * <p>
     * Flow:
     * 1. Validate student exists and is active
     * 2. Generate unique transaction code
     * 3. Persist PaymentTransaction with status=PAID
     * 4. Update student.feeStatus = Paid
     * <p>
     * Note: subscription extension (if any) is done via the update endpoint.
     */
    @Transactional
    public PaymentResponse recordPayment(Long studentId, PaymentRequest req) {

        Student student = requireStudent(studentId);

        if (!student.getUser().getIsActive()) {
            throw BusinessException.inactiveStudent(student.getUser().getName());
        }

        String txnCode = generateTransactionCode();

        PaymentTransaction txn = new PaymentTransaction(
                txnCode,
                student,
                req.getAmount(),
                PaymentStatus.PAID,
                req.getPaymentMethod(),
                LocalDateTime.now()
        );

        txn = paymentRepository.save(txn);

        // update fee status on student profile
        student.setFeeStatus(FeeStatus.PAID);
        studentRepository.save(student);

        System.out.println("Payment recorded: {} for student id={} amount={}" + "," +
                txnCode + "," + studentId + "," + req.getAmount());
        return StudentMapper.toPaymentResponse(txn);
    }

    /**
     * Paginated payment history for a student.
     */
    public Page<PaymentResponse> getPaymentHistory(Long studentId, Pageable pageable) {
        requireStudent(studentId);  // validate student exists
        return paymentRepository
                .findByStudentIdOrderByCreatedAtDesc(studentId, pageable)
                .map(StudentMapper::toPaymentResponse);
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 7. ATTENDANCE — check-in / check-out state machine
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * CHECK-IN
     * <p>
     * Guard rules:
     * - Student must be active
     * - student.currentCheckIn must be null (not already checked in)
     */
    @Transactional
    public AttendanceResponse checkIn(Long studentId) {

        Student student = requireStudent(studentId);
        String name = student.getUser().getName();

        if (!student.getUser().getIsActive()) {
            throw BusinessException.inactiveStudent(name);
        }

        if (student.isCheckedIn()) {
            throw BusinessException.alreadyCheckedIn(name);
        }

        LocalDateTime now = LocalDateTime.now();
        student.setCurrentCheckIn(now);
        studentRepository.save(student);

        System.out.println("Check-in: student id={} at {}" + "," + studentId + "," + now);

        return new AttendanceResponse(
                student.getId(),
                student.getUser().getUserCode(),
                name,
                "CHECKED_IN",
                now
        );
    }

    /**
     * CHECK-OUT
     * <p>
     * Guard rules:
     * - student.currentCheckIn must NOT be null (must be checked in)
     * - Records session duration in minutes
     * - Clears currentCheckIn after recording checkOut
     */
    @Transactional
    public AttendanceResponse checkOut(Long studentId) {

        Student student = requireStudent(studentId);
        String name = student.getUser().getName();

        if (!student.isCheckedIn()) {
            throw BusinessException.notCheckedIn(name);
        }

        LocalDateTime checkInTime = student.getCurrentCheckIn();
        LocalDateTime checkOutTime = LocalDateTime.now();

        long sessionMinutes = Math.max(Duration.between(checkInTime, checkOutTime).toMinutes(), 0L);
        long previousTotal = student.getTotalAttendanceMinutes() == null ? 0L : student.getTotalAttendanceMinutes();
        long totalAttendanceMinutes = previousTotal + sessionMinutes;

        student.setCurrentCheckOut(checkOutTime);
        student.setLastSessionMinutes(sessionMinutes);
        student.setTotalAttendanceMinutes(totalAttendanceMinutes);
//        student.setCurrentCheckIn(null);          // clear live check-in flag
        studentRepository.save(student);

        System.out.println("Check-out: student id={} session={}min" + "," + studentId + "," + sessionMinutes);

        return new AttendanceResponse(
                student.getId(),
                student.getUser().getUserCode(),
                name,
                "CHECKED_OUT",
                checkInTime,
                checkOutTime,
                sessionMinutes,
                totalAttendanceMinutes
        );


    }

    // ═════════════════════════════════════════════════════════════════════════
    // 8. ACTIVATE  (re-enable a deactivated account)
    // ═════════════════════════════════════════════════════════════════════════

    @Transactional
    public StudentResponse activate(Long id) {
        Student student = requireStudent(id);
        student.getUser().setIsActive(true);
        userRepository.save(student.getUser());
        System.out.println("Activated student id={}" + ", " + id);
        return StudentMapper.toResponse(student);
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 9. SCHEDULED JOBS
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Runs daily at 00:01 — marks students whose subscriptionExpiry < today
     * as INACTIVE.
     */
    @Scheduled(cron = "0 1 0 * * *")
    @Transactional
    public void expireSubscriptions() {
        int updated = studentRepository.expireSubscriptions(LocalDate.now());
        studentRepository.findBySeatNumberIsNotNull().forEach(student -> {
            if (!student.hasActiveSubscription()) {
                releaseSeatIfAssigned(student);
            }
        });
        if (updated > 0) {
            System.out.println("Subscription expiry job: marked {} student(s) as INACTIVE " + updated);
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    // PRIVATE HELPERS
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Fetch student or throw 404.
     */
    private Student requireStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.student(id));
    }

    /**
     * Generate next sequential user code for the given role.
     * <p>
     * Pattern: STU-001, STU-002 … STU-999, STU-1000 (no upper bound)
     * Uses the MAX existing code for the role as the seed.
     */
    private String generateUserCode(Role role) {
        String prefix = role == Role.ADMIN ? "ADM" : "STU";
        return userRepository.findMaxUserCodeByRole(role)
                .map(max -> {
                    // extract numeric suffix: "STU-042" → 42
                    String[] parts = max.split("-");
                    int next = Integer.parseInt(parts[parts.length - 1]) + 1;
                    return String.format("%s-%03d", prefix, next);
                })
                .orElse(prefix + "-001");
    }

    /**
     * Generate transaction code: TXN-YYYYMMDD-001
     * Resets daily; no collision risk within the same day because of the
     * DB unique constraint on transaction_code.
     */
    private String generateTransactionCode() {
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // 20260322
        String prefix = "TXN-" + today + "-";

        return paymentRepository.findMaxTransactionCodeWithPrefix(prefix)
                .map(max -> {
                    String[] parts = max.split("-");
                    int next = Integer.parseInt(parts[parts.length - 1]) + 1;
                    return String.format("%s%03d", prefix, next);
                })
                .orElse(prefix + "001");
    }

    private void releaseSeatIfAssigned(Student student) {
        if (student.getSeatNumber() == null || student.getSeatNumber().isBlank()) {
            return;
        }

        seatRepository.findBySeatNumber(student.getSeatNumber()).ifPresent(seat -> {
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setStudentId(null);
            seatRepository.save(seat);
        });

        student.setSeatNumber(null);
        studentRepository.save(student);
    }
}
