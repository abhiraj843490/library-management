package com.genius.tech.library.repository;

import com.genius.tech.library.models.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, Long> {

    Optional<AttendanceSession> findByStudentIdAndAttendanceDate(Long studentId, LocalDate attendanceDate);

    List<AttendanceSession> findByStudentIdAndAttendanceDateBetweenOrderByAttendanceDateAsc(
            Long studentId,
            LocalDate fromDate,
            LocalDate toDate
    );
}
