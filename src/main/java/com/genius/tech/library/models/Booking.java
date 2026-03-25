//package com.genius.tech.library.models;
//
//import com.genius.tech.library.enums.BookingStatus;
//import com.genius.tech.library.enums.BookingType;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
////@Entity
////@Table(name = "bookings")
//public class Booking {
//
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String bookingCode;
//
//    @ManyToOne
//    @JoinColumn(name = "student_id")
//    private Student student;
//
//    @ManyToOne
//    @JoinColumn(name = "study_space_id")
//    private StudySpace studySpace;
//
//    @ManyToOne
//    @JoinColumn(name = "time_slot_id")
//    private TimeSlot timeSlot;
//
//    private LocalDate bookingDate;
//
//    @Enumerated(EnumType.STRING)
//    private BookingType bookingType;
//
//    private Integer groupSize = 1;
//
//    @Enumerated(EnumType.STRING)
//    private BookingStatus status = BookingStatus.CONFIRMED;
//
//    private Boolean canBeCancelled = true;
//
//    private String notes;
//    private String cancelledReason;
//
//    private LocalDateTime cancelledAt;
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    private LocalDateTime updatedAt;
//
//    @OneToOne(mappedBy = "booking")
//    private BookingFeedback feedback;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getBookingCode() {
//        return bookingCode;
//    }
//
//    public void setBookingCode(String bookingCode) {
//        this.bookingCode = bookingCode;
//    }
//
//    public Student getStudent() {
//        return student;
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//    }
//
//    public StudySpace getStudySpace() {
//        return studySpace;
//    }
//
//    public void setStudySpace(StudySpace studySpace) {
//        this.studySpace = studySpace;
//    }
//
//    public TimeSlot getTimeSlot() {
//        return timeSlot;
//    }
//
//    public void setTimeSlot(TimeSlot timeSlot) {
//        this.timeSlot = timeSlot;
//    }
//
//    public LocalDate getBookingDate() {
//        return bookingDate;
//    }
//
//    public void setBookingDate(LocalDate bookingDate) {
//        this.bookingDate = bookingDate;
//    }
//
//    public BookingType getBookingType() {
//        return bookingType;
//    }
//
//    public void setBookingType(BookingType bookingType) {
//        this.bookingType = bookingType;
//    }
//
//    public Integer getGroupSize() {
//        return groupSize;
//    }
//
//    public void setGroupSize(Integer groupSize) {
//        this.groupSize = groupSize;
//    }
//
//    public BookingStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(BookingStatus status) {
//        this.status = status;
//    }
//
//    public Boolean getCanBeCancelled() {
//        return canBeCancelled;
//    }
//
//    public void setCanBeCancelled(Boolean canBeCancelled) {
//        this.canBeCancelled = canBeCancelled;
//    }
//
//    public String getNotes() {
//        return notes;
//    }
//
//    public void setNotes(String notes) {
//        this.notes = notes;
//    }
//
//    public String getCancelledReason() {
//        return cancelledReason;
//    }
//
//    public void setCancelledReason(String cancelledReason) {
//        this.cancelledReason = cancelledReason;
//    }
//
//    public LocalDateTime getCancelledAt() {
//        return cancelledAt;
//    }
//
//    public void setCancelledAt(LocalDateTime cancelledAt) {
//        this.cancelledAt = cancelledAt;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public BookingFeedback getFeedback() {
//        return feedback;
//    }
//
//    public void setFeedback(BookingFeedback feedback) {
//        this.feedback = feedback;
//    }
//
//    public Booking(Long id, String bookingCode, Student student, StudySpace studySpace, TimeSlot timeSlot, LocalDate bookingDate, BookingType bookingType, Integer groupSize, BookingStatus status, Boolean canBeCancelled, String notes, String cancelledReason, LocalDateTime cancelledAt, LocalDateTime createdAt, LocalDateTime updatedAt, BookingFeedback feedback) {
//        this.id = id;
//        this.bookingCode = bookingCode;
//        this.student = student;
//        this.studySpace = studySpace;
//        this.timeSlot = timeSlot;
//        this.bookingDate = bookingDate;
//        this.bookingType = bookingType;
//        this.groupSize = groupSize;
//        this.status = status;
//        this.canBeCancelled = canBeCancelled;
//        this.notes = notes;
//        this.cancelledReason = cancelledReason;
//        this.cancelledAt = cancelledAt;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.feedback = feedback;
//    }
//
//     public Booking() {
//
//     }
//}