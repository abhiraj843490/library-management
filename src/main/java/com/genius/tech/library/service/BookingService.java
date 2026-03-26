package com.genius.tech.library.service;

import com.genius.tech.library.dto.request.BookingCancelRequest;
import com.genius.tech.library.dto.request.BookingCreateRequest;
import com.genius.tech.library.dto.request.BookingFeedbackRequest;
import com.genius.tech.library.dto.response.BookingResponse;
import com.genius.tech.library.dto.response.StudySpaceAvailabilityResponse;
import com.genius.tech.library.dto.response.TimeSlotResponse;
import com.genius.tech.library.enums.BookingStatus;
import com.genius.tech.library.enums.Gender;
import com.genius.tech.library.enums.SeatStatus;
import com.genius.tech.library.exception.BusinessException;
import com.genius.tech.library.exception.ResourceNotFoundException;
import com.genius.tech.library.models.Booking;
import com.genius.tech.library.models.Seat;
import com.genius.tech.library.models.Student;
import com.genius.tech.library.repository.BookingRepository;
import com.genius.tech.library.repository.SeatRepository;
import com.genius.tech.library.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final StudentRepository studentRepository;
    private final SeatRepository seatRepository;

    public BookingService(BookingRepository bookingRepository, StudentRepository studentRepository, SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.studentRepository = studentRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional(readOnly = true)
    public List<TimeSlotResponse> getTimeSlots() {
        return List.of(
                new TimeSlotResponse(1, "Morning 1 (9-11 AM)", "09:00", "11:00", "DAY", 120),
                new TimeSlotResponse(2, "Morning 2 (11 AM-1 PM)", "11:00", "13:00", "DAY", 120),
                new TimeSlotResponse(3, "Afternoon 1 (1-3 PM)", "13:00", "15:00", "DAY", 120),
                new TimeSlotResponse(4, "Afternoon 2 (3-5 PM)", "15:00", "17:00", "DAY", 120),
                new TimeSlotResponse(5, "Evening 1 (5-7 PM)", "17:00", "19:00", "DAY", 120),
                new TimeSlotResponse(6, "Night 1 (7-9 PM)", "19:00", "21:00", "NIGHT", 120),
                new TimeSlotResponse(7, "Night 2 (9-11 PM)", "21:00", "23:00", "NIGHT", 120)
        );
    }

    @Transactional(readOnly = true)
    public List<StudySpaceAvailabilityResponse> getAvailableSpaces(Long memberId, LocalDate bookingDate, Integer timeSlotId, String roomType) {
        Student student = requireStudent(memberId);
        Gender studentGender = student.getGender();

        List<Seat> seats = seatRepository.findAll().stream()
                .filter(s -> s.getStatus() == SeatStatus.AVAILABLE)
                .filter(s -> s.getGender() == studentGender)
                .filter(s -> roomType == null || roomType.isBlank() || "ALL".equalsIgnoreCase(roomType) || s.getSection().name().equalsIgnoreCase(roomType))
                .toList();

        return seats.stream()
                .filter(seat -> !bookingRepository.existsBySeatIdAndBookingDateAndTimeSlotIdAndStatusNot(
                        seat.getId(),
                        bookingDate,
                        timeSlotId,
                        BookingStatus.CANCELLED
                ))
                .map(this::toAvailabilityResponse)
                .toList();
    }

    @Transactional
    public BookingResponse createBooking(BookingCreateRequest request) {
        Student student = requireStudent(request.getMemberId());

        if (!Boolean.TRUE.equals(student.getUser().getIsActive())) {
            throw BusinessException.inactiveStudent(student.getUser().getName());
        }
        if (request.getBookingDate().isBefore(LocalDate.now())) {
            throw new BusinessException("Booking date cannot be in the past", "BOOKING_INVALID_DATE");
        }
        if (request.getGroupSize() > 1) {
            throw new BusinessException("Only solo booking is supported for individual seats", "BOOKING_GROUP_NOT_SUPPORTED");
        }
        if (!student.hasActiveSubscription()) {
            throw new BusinessException("Subscription is not active or already expired", "SUBSCRIPTION_INACTIVE");
        }

        Seat seat = seatRepository.findById(request.getStudySpaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Study space not found with id: " + request.getStudySpaceId()));

        if (seat.getGender() != student.getGender()) {
            throw BusinessException.genderZoneMismatch();
        }

        if (student.getSeatNumber() != null && !student.getSeatNumber().isBlank()
                && !student.getSeatNumber().equalsIgnoreCase(seat.getSeatNumber())) {
            throw new BusinessException(
                    "Seat already allocated to student until subscription expiry: " + student.getSeatNumber(),
                    "SEAT_ALREADY_ALLOCATED_TO_STUDENT"
            );
        }

        if (seat.getStatus() == SeatStatus.ALLOCATED
                && (seat.getStudentId() == null || !seat.getStudentId().equals(student.getId()))) {
            throw new BusinessException("Seat is already allocated to another student", "SEAT_ALREADY_ALLOCATED");
        }

        boolean taken = bookingRepository.existsBySeatIdAndBookingDateAndTimeSlotIdAndStatusNot(
                seat.getId(),
                request.getBookingDate(),
                request.getTimeSlotId(),
                BookingStatus.CANCELLED
        );
        if (taken) {
            throw new BusinessException("Selected space is not available for the selected date/slot", "SPACE_NOT_AVAILABLE");
        }

        if (seat.getStatus() != SeatStatus.ALLOCATED || !student.getId().equals(seat.getStudentId())) {
            seat.setStatus(SeatStatus.ALLOCATED);
            seat.setStudentId(student.getId());
            seatRepository.save(seat);

            student.setSeatNumber(seat.getSeatNumber());
            student.setSeatSection(seat.getSection());
            studentRepository.save(student);
        }

        Booking booking = new Booking();
        booking.setStudent(student);
        booking.setSeatId(seat.getId());
        booking.setTimeSlotId(request.getTimeSlotId());
        booking.setSpaceName(seat.getSeatNumber());
        booking.setSlotName(slotNameById(request.getTimeSlotId()));
        booking.setBookingDate(request.getBookingDate());
        booking.setBookingType(request.getBookingType());
        booking.setGroupSize(request.getGroupSize());
        booking.setNotes(request.getNotes());
        booking.setStatus(BookingStatus.CONFIRMED);

        Booking saved = bookingRepository.save(booking);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getMemberBookings(Long memberId) {
        requireStudent(memberId);

        return bookingRepository.findByStudent_IdOrderByBookingDateDescCreatedAtDesc(memberId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public BookingResponse cancelBooking(Long bookingId, BookingCancelRequest request) {
        Booking booking = requireBooking(bookingId);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BusinessException("Booking is already cancelled", "BOOKING_ALREADY_CANCELLED");
        }
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new BusinessException("Only confirmed bookings can be cancelled", "BOOKING_NOT_CANCELLABLE");
        }
        if (!Boolean.TRUE.equals(booking.getCanBeCancelled())) {
            throw new BusinessException(
                    "This booking cannot be cancelled. Cancellation window has closed.",
                    "BOOKING_CANCELLATION_CLOSED"
            );
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setCanBeCancelled(false);
        booking.setCancelledReason(request.getReason().trim());
        booking.setCancelledAt(LocalDateTime.now());

        Booking saved = bookingRepository.save(booking);
        return toResponse(saved);
    }

    @Transactional
    public BookingResponse submitFeedback(Long bookingId, BookingFeedbackRequest request) {
        Booking booking = requireBooking(bookingId);

        if (booking.getStatus() != BookingStatus.COMPLETED) {
            throw new BusinessException("Feedback can only be submitted for completed bookings", "BOOKING_NOT_COMPLETED");
        }
        if (booking.getFeedbackRating() != null) {
            throw new BusinessException("Feedback has already been submitted for this booking", "BOOKING_FEEDBACK_EXISTS");
        }

        booking.setFeedbackRating(request.getRating());
        booking.setFeedbackCleanlinessRating(request.getCleanlinessRating());
        booking.setFeedbackNoiseLevel(request.getNoiseLevel());
        booking.setFeedbackComment(request.getComment());
        booking.setFeedbackAt(LocalDateTime.now());

        Booking saved = bookingRepository.save(booking);
        return toResponse(saved);
    }

    private Student requireStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.student(id));
    }

    private Booking requireBooking(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.booking(id));
    }

    private BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getStudent() != null ? booking.getStudent().getId() : null,
                booking.getSeatId(),
                booking.getTimeSlotId(),
                booking.getBookingCode(),
                booking.getSpaceName(),
                booking.getSlotName(),
                booking.getBookingDate(),
                booking.getStatus(),
                booking.getBookingType(),
                booking.getGroupSize(),
                booking.getCanBeCancelled(),
                booking.getFeedbackRating(),
                booking.getNotes(),
                booking.getCancelledReason(),
                booking.getCreatedAt(),
                booking.getUpdatedAt()
        );
    }

    private StudySpaceAvailabilityResponse toAvailabilityResponse(Seat seat) {
        String section = seat.getSection() != null ? seat.getSection().name() : "REGULAR";
        return new StudySpaceAvailabilityResponse(
                seat.getId(),
                seat.getSeatNumber(),
                1,
                section,
                section.equals("SILENT") ? "Silent Wing" : "Regular Wing",
                section.equals("SILENT") ? 2 : 1
        );
    }

    private String slotNameById(Integer slotId) {
        return getTimeSlots().stream()
                .filter(s -> s.getId().equals(slotId))
                .findFirst()
                .map(TimeSlotResponse::getSlotName)
                .orElse("Slot " + slotId);
    }
}
