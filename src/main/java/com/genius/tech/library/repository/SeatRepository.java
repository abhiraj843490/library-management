package com.genius.tech.library.repository;

import com.genius.tech.library.enums.Gender;
import com.genius.tech.library.enums.SeatSection;
import com.genius.tech.library.models.Seat;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.*;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    Optional<Seat> findBySeatNumber(String seatNumber);

    Optional<Seat> findByStudentId(Long studentId);

    boolean existsBySeatNumber(String seatNumber);

    List<Seat> findByGenderAndSection(Gender gender, SeatSection section);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        select s from Seat s
        where s.gender = :gender
          and s.section = :section
          and s.status = 'AVAILABLE'
        order by s.seatNumber
    """)
    List<Seat> findAvailableForUpdate(@Param("gender") Gender gender,
                                      @Param("section") SeatSection section);
}

