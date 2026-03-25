package com.genius.tech.library.controller;

import com.genius.tech.library.enums.Gender;
import com.genius.tech.library.enums.SeatSection;
import com.genius.tech.library.models.Seat;
import com.genius.tech.library.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    SeatRepository seatRepository;

    public SeatController(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }


    @GetMapping
    public List<Seat> getSeats(
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) SeatSection section) {

        if (gender != null && section != null) {
            return seatRepository.findByGenderAndSection(gender, section);
        } else {
            return seatRepository.findAll();
        }
    }
}
