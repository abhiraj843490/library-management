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
////@Table(name = "booking_feedback")
//public class BookingFeedback {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne
//    @JoinColumn(name = "booking_id", nullable = false)
//    private Booking booking;
//
//    private Integer rating;
//    private Integer cleanlinessRating;
//    private String comment;
//
//    public BookingFeedback() {
//    }
//
//    public BookingFeedback(Long id, Booking booking, Integer rating, Integer cleanlinessRating, String comment, LocalDateTime createdAt) {
//        this.id = id;
//        this.booking = booking;
//        this.rating = rating;
//        this.cleanlinessRating = cleanlinessRating;
//        this.comment = comment;
//        this.createdAt = createdAt;
//    }
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Booking getBooking() {
//        return booking;
//    }
//
//    public void setBooking(Booking booking) {
//        this.booking = booking;
//    }
//
//    public Integer getRating() {
//        return rating;
//    }
//
//    public void setRating(Integer rating) {
//        this.rating = rating;
//    }
//
//    public Integer getCleanlinessRating() {
//        return cleanlinessRating;
//    }
//
//    public void setCleanlinessRating(Integer cleanlinessRating) {
//        this.cleanlinessRating = cleanlinessRating;
//    }
//
//    public String getComment() {
//        return comment;
//    }
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//}
