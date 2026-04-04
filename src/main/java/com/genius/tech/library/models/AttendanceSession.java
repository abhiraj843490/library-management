package com.genius.tech.library.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "attendance_sessions",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_attendance_student_day", columnNames = {"student_id", "attendance_date"})
        },
        indexes = {
                @Index(name = "idx_attendance_student", columnList = "student_id"),
                @Index(name = "idx_attendance_date", columnList = "attendance_date")
        }
)
public class AttendanceSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false, foreignKey = @ForeignKey(name = "fk_attendance_student"))
    private Student student;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Column(name = "session_minutes")
    private Long sessionMinutes;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    public AttendanceSession() {
    }

    public AttendanceSession(Student student, LocalDate attendanceDate, LocalDateTime checkIn, String status) {
        this.student = student;
        this.attendanceDate = attendanceDate;
        this.checkIn = checkIn;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
