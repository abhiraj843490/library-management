package com.genius.tech.library.models;

import com.genius.tech.library.enums.Gender;
import com.genius.tech.library.enums.SeatSection;
import com.genius.tech.library.enums.SeatStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seats", indexes = {
        @Index(name = "idx_seat_gender_section_status", columnList = "gender,section,status")
})
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number", nullable = false, unique = true, length = 20)
    private String seatNumber; // B-1, B-2, G-1...

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private SeatSection section;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SeatStatus status = SeatStatus.AVAILABLE;

    @Column(name = "student_id")
    private Long studentId; // nullable when AVAILABLE/BLOCKED/etc.

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() { this.updatedAt = LocalDateTime.now(); }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public SeatSection getSection() {
        return section;
    }

    public void setSection(SeatSection section) {
        this.section = section;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
