//package com.genius.tech.library.models;
//import com.genius.tech.library.enums.SeatSection;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDateTime;
//import java.util.List;
//@Entity
//@Table(name = "study_spaces")
//@Getter @Setter
//public class StudySpace {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String spaceName;
//
//    @Enumerated(EnumType.STRING)
//    private SeatSection roomType;
//
//    private Integer capacity;
//
//    private String location;
//
//    private Integer floorNo;
//
//    private Boolean isActive = true;
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    private LocalDateTime updatedAt;
//
//    @OneToMany(mappedBy = "studySpace")
//    private List<StudySpaceFacility> facilities;
//
//    @OneToMany(mappedBy = "studySpace")
//    private List<Booking> bookings;
//}
