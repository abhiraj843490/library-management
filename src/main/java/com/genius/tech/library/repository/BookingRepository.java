package com.genius.tech.library.repository;

import com.genius.tech.library.enums.BookingStatus;
import com.genius.tech.library.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStudent_IdOrderByBookingDateDescCreatedAtDesc(Long studentId);

    boolean existsBySeatIdAndBookingDateAndTimeSlotIdAndStatusNot(
            Long seatId,
            LocalDate bookingDate,
            Integer timeSlotId,
            BookingStatus status
    );
}
