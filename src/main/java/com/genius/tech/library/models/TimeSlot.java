//package com.genius.tech.library.models;
//import com.genius.tech.library.enums.SlotType;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.List;
//@Entity
//@Table(name = "time_slots")
//@Getter @Setter
//public class TimeSlot {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String slotName;
//
//    private LocalTime startTime;
//    private LocalTime endTime;
//
//    @Enumerated(EnumType.STRING)
//    private SlotType slotType;
//
//    private Integer durationMinutes;
//
//    private Boolean isActive = true;
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//    @OneToMany(mappedBy = "timeSlot")
//    private List<Booking> bookings;
//}
