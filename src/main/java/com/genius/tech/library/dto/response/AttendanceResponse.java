package com.genius.tech.library.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Returned after check-in or check-out actions.
 */

public class AttendanceResponse {

    private Long studentId;
    private String userCode;
    private String studentName;

    /** "CHECKED_IN" | "CHECKED_OUT" */
    private String action;

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    /** Duration in minutes — populated only on check-out */
    private Long sessionMinutes;
    private Long totalAttendanceMinutes;

    public AttendanceResponse() {
    }

    public AttendanceResponse(Long studentId,
                              String userCode,
                              String studentName,
                              String action,
                              LocalDateTime checkIn) {

        this.studentId = studentId;
        this.userCode = userCode;
        this.studentName = studentName;
        this.action = action;
        this.checkIn = checkIn;
    }

    public AttendanceResponse(Long studentId,
                              String userCode,
                              String studentName,
                              String action,
                              LocalDateTime checkIn,
                              LocalDateTime checkOut,
                              Long sessionMinutes,
                              Long totalAttendanceMinutes) {

        this.studentId = studentId;
        this.userCode = userCode;
        this.studentName = studentName;
        this.action = action;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.sessionMinutes = sessionMinutes;
        this.totalAttendanceMinutes = totalAttendanceMinutes;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public Long getTotalAttendanceMinutes() {
        return totalAttendanceMinutes;
    }

    public void setTotalAttendanceMinutes(Long totalAttendanceMinutes) {
        this.totalAttendanceMinutes = totalAttendanceMinutes;
    }
}
