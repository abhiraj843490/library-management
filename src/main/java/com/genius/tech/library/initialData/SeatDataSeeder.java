package com.genius.tech.library.initialData;

import com.genius.tech.library.enums.Gender;
import com.genius.tech.library.enums.SeatSection;
import com.genius.tech.library.enums.SeatStatus;
import com.genius.tech.library.models.Seat;
import com.genius.tech.library.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeatDataSeeder implements ApplicationRunner {
    @Autowired
    SeatRepository seatRepository;


    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<Seat> toInsert = new ArrayList<>();

        // BOY REGULAR: B-1..B-35
        for (int i = 1; i <= 35; i++) addIfMissing(toInsert, "B-" + i, Gender.BOY, SeatSection.REGULAR);

        // BOY SILENT: B-36..B-50
        for (int i = 36; i <= 50; i++) addIfMissing(toInsert, "B-" + i, Gender.BOY, SeatSection.SILENT);

        // GIRL REGULAR: G-1..G-35
        for (int i = 1; i <= 35; i++) addIfMissing(toInsert, "G-" + i, Gender.GIRL, SeatSection.REGULAR);

        // GIRL SILENT: G-36..G-50
        for (int i = 36; i <= 50; i++) addIfMissing(toInsert, "G-" + i, Gender.GIRL, SeatSection.SILENT);

        if (!toInsert.isEmpty()) {
            seatRepository.saveAll(toInsert);
            System.out.println("Seeded seats: " + toInsert.size());
        } else {
            System.out.println("Seats already seeded. No action needed.");
        }
    }

    private void addIfMissing(List<Seat> toInsert, String seatNumber, Gender gender, SeatSection section) {
        if (seatRepository.existsBySeatNumber(seatNumber)) return;

        Seat seat = new Seat();
        seat.setSeatNumber(seatNumber);
        seat.setGender(gender);
        seat.setSection(section);
        seat.setStatus(SeatStatus.AVAILABLE);
        seat.setStudentId(null);
        toInsert.add(seat);
    }
}

