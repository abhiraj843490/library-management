package com.genius.tech.library.dto;

import com.genius.tech.library.enums.FeeStatus;
import com.genius.tech.library.enums.Gender;
import com.genius.tech.library.enums.SubscriptionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

public class StudentDto {
    String name;
    String email;
    String phone;
    String seatNumber;
    Gender seatingSide;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Gender getSeatingSide() {
        return seatingSide;
    }

    public void setSeatingSide(Gender seatingSide) {
        this.seatingSide = seatingSide;
    }

    public StudentDto(String name, String email, String phone, String seatNumber, Gender seatingSide) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.seatNumber = seatNumber;
        this.seatingSide = seatingSide;
    }

    public StudentDto() {
    }
}
