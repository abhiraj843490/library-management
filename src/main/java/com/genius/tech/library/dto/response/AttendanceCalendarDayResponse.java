package com.genius.tech.library.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceCalendarDayResponse {

    private LocalDate date;
    private String status;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Long sessionMinutes;

    public AttendanceCalendarDayResponse() {
    }

    public AttendanceCalendarDayResponse(
            LocalDate date,
            String status,
            LocalDateTime checkIn,
            LocalDateTime checkOut,
            Long sessionMinutes
    ) {
        this.date = date;
        this.status = status;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.sessionMinutes = sessionMinutes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public Long getSessionMinutes() {
        return sessionMinutes;
    }

    public void setSessionMinutes(Long sessionMinutes) {
        this.sessionMinutes = sessionMinutes;
    }
}
