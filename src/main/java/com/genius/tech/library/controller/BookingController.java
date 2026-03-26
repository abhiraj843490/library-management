package com.genius.tech.library.controller;

import com.genius.tech.library.dto.request.BookingCancelRequest;
import com.genius.tech.library.dto.request.BookingCreateRequest;
import com.genius.tech.library.dto.request.BookingFeedbackRequest;
import com.genius.tech.library.dto.response.ApiResponse;
import com.genius.tech.library.dto.response.BookingResponse;
import com.genius.tech.library.dto.response.StudySpaceAvailabilityResponse;
import com.genius.tech.library.dto.response.TimeSlotResponse;
import com.genius.tech.library.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/study-spaces")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/time-slots")
    public ResponseEntity<ApiResponse<List<TimeSlotResponse>>> getTimeSlots() {
        return ResponseEntity.ok(ApiResponse.ok("Time slots retrieved", bookingService.getTimeSlots()));
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<StudySpaceAvailabilityResponse>>> getAvailableSpaces(
            @RequestParam Long memberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookingDate,
            @RequestParam Integer timeSlotId,
            @RequestParam(required = false) String roomType) {

        List<StudySpaceAvailabilityResponse> spaces =
                bookingService.getAvailableSpaces(memberId, bookingDate, timeSlotId, roomType);
        return ResponseEntity.ok(ApiResponse.ok("Available spaces retrieved", spaces));
    }

    @PostMapping("/bookings")
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(@Valid @RequestBody BookingCreateRequest request) {
        BookingResponse created = bookingService.createBooking(request);
        return ResponseEntity.ok(ApiResponse.ok("Booking created successfully", created));
    }

    @GetMapping("/members/{memberId}/bookings")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getMemberBookings(
            @PathVariable Long memberId) {

        List<BookingResponse> bookings = bookingService.getMemberBookings(memberId);
        return ResponseEntity.ok(
                ApiResponse.ok("Member bookings retrieved", bookings));
    }

    @PutMapping("/bookings/{bookingId}/cancel")
    public ResponseEntity<ApiResponse<BookingResponse>> cancelBooking(
            @PathVariable Long bookingId,
            @Valid @RequestBody BookingCancelRequest request) {

        BookingResponse cancelled = bookingService.cancelBooking(bookingId, request);
        return ResponseEntity.ok(
                ApiResponse.ok("Booking cancelled successfully", cancelled));
    }

    @PostMapping("/bookings/{bookingId}/feedback")
    public ResponseEntity<ApiResponse<BookingResponse>> submitFeedback(
            @PathVariable Long bookingId,
            @Valid @RequestBody BookingFeedbackRequest request) {

        BookingResponse updated = bookingService.submitFeedback(bookingId, request);
        return ResponseEntity.ok(
                ApiResponse.ok("Feedback submitted successfully", updated));
    }
}
