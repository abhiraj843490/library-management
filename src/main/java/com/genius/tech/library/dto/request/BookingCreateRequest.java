package com.genius.tech.library.dto.request;

import com.genius.tech.library.enums.BookingType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class BookingCreateRequest {

    @NotNull(message = "memberId is required")
    private Long memberId;

    @NotNull(message = "studySpaceId is required")
    private Long studySpaceId;

    @NotNull(message = "timeSlotId is required")
    @Min(value = 1, message = "timeSlotId must be greater than 0")
    private Integer timeSlotId;

    @NotNull(message = "bookingDate is required")
    private LocalDate bookingDate;

    @NotNull(message = "bookingType is required")
    private BookingType bookingType;

    @NotNull(message = "groupSize is required")
    @Min(value = 1, message = "groupSize must be at least 1")
    private Integer groupSize;

    @Size(max = 500, message = "notes can be at most 500 characters")
    private String notes;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getStudySpaceId() {
        return studySpaceId;
    }

    public void setStudySpaceId(Long studySpaceId) {
        this.studySpaceId = studySpaceId;
    }

    public Integer getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Integer timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
