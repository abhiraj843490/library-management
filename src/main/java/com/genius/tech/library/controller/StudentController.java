package com.genius.tech.library.controller;

import com.genius.tech.library.dto.request.PaymentRequest;
import com.genius.tech.library.dto.request.StudentCreateRequest;
import com.genius.tech.library.dto.request.StudentUpdateRequest;
import com.genius.tech.library.dto.response.*;
import com.genius.tech.library.enums.FeeStatus;
import com.genius.tech.library.enums.Gender;
import com.genius.tech.library.enums.SeatSection;
import com.genius.tech.library.enums.SubscriptionStatus;
import com.genius.tech.library.models.Seat;
import com.genius.tech.library.repository.SeatRepository;
import com.genius.tech.library.service.StudentService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * REST controller for student management.
 * <p>
 * Base path : /api/students
 * All write operations are ADMIN-only.
 * Read operations are ADMIN-only (students use /api/dashboard/stats for own profile).
 * <p>
 * Endpoints:
 * GET    /                        list (paginated + filtered)
 * POST   /                        enroll new student
 * GET    /{id}                    get full profile
 * PUT    /{id}                    patch update
 * DELETE /{id}                    hard delete
 * PATCH  /{id}/deactivate         soft disable
 * PATCH  /{id}/activate           re-enable
 * POST   /{id}/payments           record fee payment
 * GET    /{id}/payments           payment history
 * POST   /{id}/check-in           mark attendance in
 * POST   /{id}/check-out          mark attendance out
 */
@RestController
@RequestMapping("/api/students")
//@PreAuthorize("hasRole('ADMIN')")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    SeatRepository seatRepository;

    /**
     * Paginated student list with optional filters.
     * <p>
     * Query params:
     * search             — partial match on name / email / userCode
     * gender             — BOYS | GIRLS
     * feeStatus          — Paid | Pending
     * subscriptionStatus — ACTIVE | INACTIVE
     * activeOnly         — true (default) | false
     * page, size, sort   — Spring Pageable
     * <p>
     * Example: GET /api/students?search=ravi&feeStatus=Pending&size=10&sort=createdAt,desc
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<StudentSummaryResponse>>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) FeeStatus feeStatus,
            @RequestParam(required = false) SubscriptionStatus subscriptionStatus,
            @RequestParam(defaultValue = "true") boolean activeOnly,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<StudentSummaryResponse> page = studentService.listStudents(search, gender, feeStatus, subscriptionStatus, activeOnly, pageable);

        return ResponseEntity.ok(ApiResponse.ok("Students retrieved successfully", page));
    }

    /**
     * Enrol a new student.
     * Creates both the users row and the students row in a single transaction.
     * Returns 201 Created with the full student profile.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> enroll(
            @Valid @RequestBody StudentCreateRequest request) {

        StudentResponse created = studentService.enroll(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Student enrolled successfully", created));
    }

    // =========================================================================
    // GET /api/students/{id}
    // =========================================================================

    /**
     * Retrieve full student profile by primary key.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.ok(studentService.getById(id)));
    }

    // =========================================================================
    // PUT /api/students/{id}  — patch update
    // =========================================================================

    /**
     * Update mutable student fields.
     * Only non-null fields in the request body are applied (patch semantics).
     * <p>
     * Updatable: name, phone, seatSection, seatNumber,
     * subscriptionStatus, subscriptionExpiry, feeStatus, monthlyFee
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody StudentUpdateRequest request) {

        StudentResponse updated = studentService.update(id, request);
        return ResponseEntity.ok(
                ApiResponse.ok("Student updated successfully", updated));
    }

    // =========================================================================
    // DELETE /api/students/{id}  — hard delete
    // =========================================================================

    /**
     * Permanently removes the student and linked user account.
     * Cascades to students row via FK constraint.
     * Use PATCH /deactivate for non-destructive disabling.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        studentService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.ok("Student deleted successfully", null));
    }

    // =========================================================================
    // PATCH /api/students/{id}/deactivate  — soft disable
    // =========================================================================

    /**
     * Soft-deactivate: sets user.isActive = false without removing any records.
     * Student cannot log in after deactivation.
     * History (bookings, payments) is preserved for reporting.
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivate(
            @PathVariable Long id) {

        studentService.deactivate(id);
        return ResponseEntity.ok(
                ApiResponse.ok("Student deactivated", null));
    }

    // =========================================================================
    // PATCH /api/students/{id}/activate  — re-enable
    // =========================================================================

    /**
     * Re-enables a previously deactivated student account.
     * Sets user.isActive = true.
     * Subscription status is NOT automatically restored — admin must update separately.
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<StudentResponse>> activate(
            @PathVariable Long id) {

        // Activate is just an update setting isActive = true
//        StudentUpdateRequest req = new StudentUpdateRequest();
        // Re-use the update path for consistency; service handles isActive via deactivate/activate logic
        // Delegate to a dedicated activate method in the service
        StudentResponse res = studentService.activate(id);
        return ResponseEntity.ok(
                ApiResponse.ok("Student activated", res));
    }

    // =========================================================================
    // POST /api/students/{id}/payments  — record fee payment
    // =========================================================================

    /**
     * Record a monthly fee payment.
     * <p>
     * Creates an immutable payment_transactions row.
     * Updates student.feeStatus = Paid.
     * Returns 201 Created with the transaction record.
     * <p>
     * Body: { amount, paymentMethod, referenceNote? }
     */
    @PostMapping("/{id}/payments")
    public ResponseEntity<ApiResponse<PaymentResponse>> recordPayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse txn = studentService.recordPayment(id, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Payment recorded successfully", txn));
    }

    // =========================================================================
    // GET /api/students/{id}/payments  — payment history
    // =========================================================================

    /**
     * Paginated payment transaction history for a student.
     * Ordered by createdAt descending (latest first).
     */
    @GetMapping("/{id}/payments")
    public ResponseEntity<ApiResponse<Page<PaymentResponse>>> getPaymentHistory(
            @PathVariable Long id,
            @PageableDefault(size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable) {

        Page<PaymentResponse> history = studentService.getPaymentHistory(id, pageable);
        return ResponseEntity.ok(
                ApiResponse.ok("Payment history retrieved", history));
    }

    // =========================================================================
    // POST /api/students/{id}/check-in
    // =========================================================================

    /**
     * Mark student as checked in.
     * <p>
     * Guard rules enforced in service:
     * - Student account must be active
     * - Student must not already be checked in (currentCheckIn is null)
     * <p>
     * Sets currentCheckIn = now().
     */
    @PostMapping("/{id}/check-in")
    public ResponseEntity<ApiResponse<AttendanceResponse>> checkIn(
            @PathVariable Long id) {

        AttendanceResponse result = studentService.checkIn(id);
        return ResponseEntity.ok(
                ApiResponse.ok("Check-in recorded", result));
    }

    // =========================================================================
    // POST /api/students/{id}/check-out
    // =========================================================================

    /**
     * Mark student as checked out.
     * <p>
     * Guard rules enforced in service:
     * - Student must currently be checked in (currentCheckIn is NOT null)
     * <p>
     * Clears currentCheckIn, sets currentCheckOut = now().
     * Response includes session duration in minutes.
     */
    @PostMapping("/{id}/check-out")
    public ResponseEntity<ApiResponse<AttendanceResponse>> checkOut(
            @PathVariable Long id) {

        AttendanceResponse result = studentService.checkOut(id);
        return ResponseEntity.ok(
                ApiResponse.ok("Check-out recorded", result));
    }

    @GetMapping("/{id}/attendance")
    public ResponseEntity<ApiResponse<List<AttendanceCalendarDayResponse>>> getAttendanceCalendar(
            @PathVariable Long id,
            @RequestParam(required = false) String month) {

        YearMonth yearMonth = (month == null || month.isBlank())
                ? YearMonth.now()
                : parseYearMonth(month);
        List<AttendanceCalendarDayResponse> data = studentService.getAttendanceCalendar(id, yearMonth);
        return ResponseEntity.ok(ApiResponse.ok("Attendance calendar retrieved", data));
    }

    private YearMonth parseYearMonth(String value) {
        try {
            return YearMonth.parse(value);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid month format. Use YYYY-MM");
        }
    }

}
