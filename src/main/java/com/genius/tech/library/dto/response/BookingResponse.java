package com.genius.tech.library.dto.response;

import com.genius.tech.library.enums.BookingStatus;
import com.genius.tech.library.enums.BookingType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingResponse {
    private Long id;
    private Long studentId;
    private Long seatId;
    private Integer timeSlotId;
    private String bookingCode;
    private String spaceName;
    private String slotName;
    private LocalDate bookingDate;
    private BookingStatus status;
    private BookingType bookingType;
    private Integer groupSize;
    private Boolean canBeCancelled;
    private Integer averageRating;
    private String notes;
    private String cancelledReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BookingResponse() {
    }

    public BookingResponse(Long id, Long studentId, Long seatId, Integer timeSlotId, String bookingCode, String spaceName, String slotName,
                           LocalDate bookingDate, BookingStatus status, BookingType bookingType, Integer groupSize,
                           Boolean canBeCancelled, Integer averageRating, String notes, String cancelledReason,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.studentId = studentId;
        this.seatId = seatId;
        this.timeSlotId = timeSlotId;
        this.bookingCode = bookingCode;
        this.spaceName = spaceName;
        this.slotName = slotName;
        this.bookingDate = bookingDate;
        this.status = status;
        this.bookingType = bookingType;
        this.groupSize = groupSize;
        this.canBeCancelled = canBeCancelled;
        this.averageRating = averageRating;
        this.notes = notes;
        this.cancelledReason = cancelledReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Integer getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Integer timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
    }

    public Boolean getCanBeCancelled() {
        return canBeCancelled;
    }

    public void setCanBeCancelled(Boolean canBeCancelled) {
        this.canBeCancelled = canBeCancelled;
    }

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCancelledReason() {
        return cancelledReason;
    }

    public void setCancelledReason(String cancelledReason) {
        this.cancelledReason = cancelledReason;
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
